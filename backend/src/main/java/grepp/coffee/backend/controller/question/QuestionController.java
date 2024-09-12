package grepp.coffee.backend.controller.question;

import grepp.coffee.backend.controller.question.request.QuestionDeleteRequest;
import grepp.coffee.backend.controller.question.request.QuestionRegisterRequest;
import grepp.coffee.backend.controller.question.request.QuestionUpdateRequest;
import grepp.coffee.backend.model.entity.question.Question;
import grepp.coffee.backend.model.service.question.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "FAQ API")
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "FAQ 목록 조회", description = "모든 FAQ를 조회하는 API")
    @GetMapping("")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok().body(questionService.readQuestionList());
    }

    @Operation(summary = "[관리자] FAQ 등록", description = "관리자가 새로운 질문과 답변(QNA)을 등록하는 API")
    @PostMapping("/")
    public ResponseEntity<Void> createQuestion(@Valid @RequestBody QuestionRegisterRequest question) {
        questionService.createQuestion(question);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "[관리자] FAQ 수정", description = "관리자가 특정 질문과 답변(FAQ)을 수정하는 API")
    @PutMapping("/{questionId}")
    public ResponseEntity<Void> updateQuestion(@PathVariable(name = "questionId") Long questionId,
                                               @Valid @RequestBody QuestionUpdateRequest question){
        questionService.updateQuestion(questionId, question);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "[관리자] FAQ 삭제", description = "관리자가 특정 질문과 답변(FAQ)을 삭제하는 API")
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable(name = "questionId") Long questionId,
                                               @Valid @RequestBody QuestionDeleteRequest question) {
        questionService.deleteQuestion(questionId, question);
        return ResponseEntity.ok().build();
    }
}
