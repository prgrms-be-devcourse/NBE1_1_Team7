package grepp.coffee.backend.controller.question.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRegisterRequest {
    @NotNull
    @Size(max = 200, message = "질문 내용 200자 이내")
    private String question;

    @NotNull
    @Size(max = 200, message = "답변 내용 200자 이내")
    private String answer;
}
