package grepp.coffee.backend.controller.member.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequest {

    @NotNull
    private String password;

    @NotNull
    private String address;

    @NotNull
    private String postcode;
}