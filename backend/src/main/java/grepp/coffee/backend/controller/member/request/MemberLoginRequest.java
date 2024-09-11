package grepp.coffee.backend.controller.member.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginRequest {

    @NotNull
    private String email;

    @NotNull
    private byte[] password;
}
