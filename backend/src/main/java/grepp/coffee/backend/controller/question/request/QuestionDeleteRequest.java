package grepp.coffee.backend.controller.question.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDeleteRequest {
    @NotNull
    Long memberId;
}
