package grepp.coffee.backend.controller.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.common.exception.order.OrderException;
import grepp.coffee.backend.controller.member.request.MemberLoginRequest;
import grepp.coffee.backend.controller.member.request.MemberRegisterRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.service.member.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/account")
@RestController
public class MemberController {

    private final MemberService memberService;

    //회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Void> registerMember(@Valid @RequestBody MemberRegisterRequest request) {
        memberService.registertMember(request);
        return ResponseEntity.ok().build();
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Member> login(HttpServletRequest httpServletRequest,
                        @Valid @RequestBody MemberLoginRequest request) {

        Member member = memberService.login(request);

        if(member == null) {
            throw new OrderException(ExceptionMessage.MEMBER_LOGIN_FAIL);
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("loginMember", member);
        session.setMaxInactiveInterval(60 * 10);
        return ResponseEntity.ok().body(member);

    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    //마이페이지
    @GetMapping("/mypage")
    public ResponseEntity<Member> getMemberInfo(HttpSession session) {

        // 세션에서 로그인된 회원 정보 가져오기
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            throw new MemberException(ExceptionMessage.MEMBER_NOT_LOGIN);
        }

        // 로그인된 회원의 ID로 회원 정보 조회
        Member member = memberService.findByIdOrThrowMemberException(loginMember.getMemberId());

        return ResponseEntity.ok().body(member);

    }
}
