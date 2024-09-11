package grepp.coffee.backend.controller.member.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequest {

    @NotNull
    private String email;

    @NotNull
    private byte[] password;

    @NotNull
    private String address;
}
