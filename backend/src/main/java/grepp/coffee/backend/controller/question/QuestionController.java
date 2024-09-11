package grepp.coffee.backend.controller.question;

import grepp.coffee.backend.controller.question.request.QuestionRegisterRequest;
import grepp.coffee.backend.model.entity.question.Question;
import grepp.coffee.backend.model.service.product.ProductService;
import grepp.coffee.backend.model.service.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;

    // QNA 목록
    @GetMapping("")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok().body(questionService.readQuestionList());
    }

    // QNA 등록
    @PostMapping("/")
    public ResponseEntity<Void> createQuestion(@Valid @RequestBody QuestionRegisterRequest question) {
        questionService.createQuestion(question);
        return ResponseEntity.ok().build();
    }
}
