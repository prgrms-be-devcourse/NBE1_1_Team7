package grepp.coffee.backend.model.service.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.order.OrderException;
import grepp.coffee.backend.controller.member.request.MemberRegisterRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.order.Order;
import grepp.coffee.backend.model.repository.member.MemberRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static grepp.coffee.backend.model.entity.order.constant.OrderStatus.PENDING;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public void registertMember(MemberRegisterRequest request) {

        // 새로운 member 생성 및 저장
        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .build();
        memberRepository.save(member);
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
