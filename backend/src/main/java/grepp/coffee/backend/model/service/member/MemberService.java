package grepp.coffee.backend.model.service.member;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.order.OrderException;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    // 멤버 조회 예외처리
    public Member findByIdOrThrowMemberException(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", memberId, ExceptionMessage.ORDER_NOT_FOUND);
                    return new OrderException(ExceptionMessage.ORDER_NOT_FOUND);
                });
    }
}
