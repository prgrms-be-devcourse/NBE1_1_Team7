package grepp.coffee.backend.controller.member;

import grepp.coffee.backend.controller.member.request.MemberRegisterRequest;
import grepp.coffee.backend.controller.order.request.OrderRegisterRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
