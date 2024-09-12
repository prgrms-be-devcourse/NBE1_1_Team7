package grepp.coffee.backend.model.entity.member.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ROLE {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER")
    ;

    private final String text;
}