package grepp.coffee.backend.controller.member.request;

import grepp.coffee.backend.model.entity.member.constant.ROLE;
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
    private String password;

    @NotNull
    private ROLE role;

    @NotNull
    private String address;
}
