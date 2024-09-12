package grepp.coffee.backend.controller.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.member.MemberException;
import grepp.coffee.backend.common.exception.order.OrderException;
import grepp.coffee.backend.controller.member.request.MemberLoginRequest;
import grepp.coffee.backend.controller.member.request.MemberRegisterRequest;
import grepp.coffee.backend.controller.member.request.MemberUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "회원 API")
public class MemberController {

    private final MemberService memberService;

    //회원 가입
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입 API")
    public ResponseEntity<Void> registerMember(@Valid @RequestBody MemberRegisterRequest request) {
        memberService.registerMember(request);
        return ResponseEntity.ok().build();
    }

    //로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 API")
    public ResponseEntity<Member> login(HttpServletRequest httpServletRequest,
                        @Valid @RequestBody MemberLoginRequest request) {

        Member member = memberService.login(request);

        if(member == null) {
            throw new MemberException(ExceptionMessage.MEMBER_LOGIN_FAIL);
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("loginMember", member);
        session.setAttribute("userRole", member.getRole());
        session.setMaxInactiveInterval(60 * 10);
        return ResponseEntity.ok().body(member);
    }


    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 API")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    //마이페이지
    @GetMapping("/mypage")
    @Operation(summary = "회원정보 조회", description = "회원정보를 조회하는 API")
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

    @PutMapping("/mypage")
    @Operation(summary = "회원정보 수정", description = "회원정보를 수정하는 API")
    public ResponseEntity<Member> updateMemberInfo(HttpSession session,
                                         @Valid @RequestBody MemberUpdateRequest request) {

        // 세션에서 로그인된 회원 정보 가져오기
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            throw new MemberException(ExceptionMessage.MEMBER_NOT_LOGIN);
        }

        // 회원 정보 업데이트
        Member member = memberService.updateMember(loginMember.getMemberId(), request);

        return ResponseEntity.ok().body(member);
    }
}
