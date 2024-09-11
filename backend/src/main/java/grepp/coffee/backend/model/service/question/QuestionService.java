package grepp.coffee.backend.model.service.question;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.question.QuestionException;
import grepp.coffee.backend.controller.question.request.QuestionRegisterRequest;
import grepp.coffee.backend.controller.question.request.QuestionUpdateRequest;
import grepp.coffee.backend.model.entity.question.Question;
import grepp.coffee.backend.model.repository.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    //FAQ 전체 조회
    public List<Question> readQuestionList() {return questionRepository.findAll();}

    //질문 등록
    @Transactional
    public void createQuestion(QuestionRegisterRequest request) {
        Question question = Question.builder()
                .memberId((long)1)  //임시로 admin의 memberId는 1이라고 가정
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();
        questionRepository.save(question);
    }

    //질문 수정
    @Transactional
    public void updateQuestion(Long questionId, QuestionUpdateRequest request) {
        // 질문 조회
        Question question = findByIdOrThrowQuestionException(questionId);
        // 수정된 정보를 반영
        question.updateQuestion(
                request.getQuestion(),
                request.getAnswer()
        );
    }


    //질문 삭제
    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = findByIdOrThrowQuestionException(questionId);
        questionRepository.delete(question);
    }


    // 질문 조회 예외처리
    public Question findByIdOrThrowQuestionException(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> {
                    log.warn(">>>> {} : {} <<<<", questionId, ExceptionMessage.QUESTION_NOT_FOUND);
                    return new QuestionException(ExceptionMessage.QUESTION_NOT_FOUND);
                });
    }
}
