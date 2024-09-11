package grepp.coffee.backend.controller.question;

import grepp.coffee.backend.controller.question.request.QuestionRegisterRequest;
import grepp.coffee.backend.model.entity.question.Question;
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
@RequestMapping("/question")
@RestController
public class QuestionController {

    @PostMapping("/")
    public ResponseEntity<Void> createQuestion(@Valid @RequestBody QuestionRegisterRequest question) {
        return ResponseEntity.ok().build();
    }
}
