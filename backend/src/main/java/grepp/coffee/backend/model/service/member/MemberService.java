package grepp.coffee.backend.model.service.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.common.exception.order.OrderException;
import grepp.coffee.backend.controller.member.request.MemberLoginRequest;
import grepp.coffee.backend.controller.member.request.MemberRegisterRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public void registertMember(MemberRegisterRequest request) {

        // 이메일 중복 확인
        Member existingMember = getMemberByEmail(request.getEmail());

        if (existingMember != null) {
            throw new MemberException(ExceptionMessage.MEMBER_IS_PRESENT);
        }

        // 새로운 member 생성 및 저장
        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .build();
        memberRepository.save(member);
    }

    //이메일로 회원 정보 조회
    @Transactional
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    //로그인
    @Transactional
    public Member login(MemberLoginRequest request) {

        //회원 정보 조회
        Member member = getMemberByEmail(request.getEmail());
        String password = (member == null) ? "" : Arrays.toString(member.getPassword());

        //비밀번호 일치 시 회원 정보 리턴
        if (member != null || Arrays.toString(request.getPassword()).equals(password))
            return member;

        return null;
    }


    // 멤버 조회 예외처리
    public Member findByIdOrThrowMemberException(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", memberId, ExceptionMessage.ORDER_NOT_FOUND);
                    return new OrderException(ExceptionMessage.ORDER_NOT_FOUND);
                });
    }

}
