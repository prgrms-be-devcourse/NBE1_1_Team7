package grepp.coffee.backend.model.service.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.common.exception.order.OrderException;
import grepp.coffee.backend.controller.member.request.MemberLoginRequest;
import grepp.coffee.backend.controller.member.request.MemberRegisterRequest;
import grepp.coffee.backend.controller.member.request.MemberUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.member.constant.ROLE;
import grepp.coffee.backend.model.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring Security의 UserDetailsService 구현
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new MemberException(ExceptionMessage.MEMBER_NOT_FOUND);
        }

        return new org.springframework.security.core.userdetails.User(
                member.getEmail(),
                passwordEncoder.encode(member.getPassword()),
                getAuthorities(member.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(ROLE role) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }


    //회원가입
    @Transactional
    public void registerMember(MemberRegisterRequest request) {

        // 이메일 중복 확인
        Member existingMember = getMemberByEmail(request.getEmail());

        if (existingMember != null) {
            throw new MemberException(ExceptionMessage.MEMBER_IS_PRESENT);
        }

        // 새로운 member 생성 및 저장
        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
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
        String password = (member == null) ? "" : member.getPassword();

        //비밀번호 일치 시 회원 정보 리턴
        if (member != null && passwordEncoder.matches(request.getPassword(), password))
            return member;

        return null;
    }

    //회원 정보 수정
    @Transactional
    public Member updateMember(Long id, MemberUpdateRequest request) {

        // 회원 정보 조회
        Member member = findByIdOrThrowMemberException(id);

        System.out.println(member);

        // member 정보 업데이트
        member = Member.builder()
                .memberId(id)
                .email(member.getEmail())
                .password(request.getPassword())
                .address(request.getAddress())
                .postcode(request.getPostcode())
                .build();

        // 변경된 회원 정보 저장
        return memberRepository.save(member);
    }


    // 멤버 조회 예외처리
    public Member findByIdOrThrowMemberException(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", memberId, ExceptionMessage.MEMBER_NOT_FOUND);
                    return new MemberException(ExceptionMessage.MEMBER_NOT_FOUND);
                });
    }
}
