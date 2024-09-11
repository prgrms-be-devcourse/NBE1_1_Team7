package grepp.coffee.backend.model.service.question;

import grepp.coffee.backend.controller.question.request.QuestionRegisterRequest;
import grepp.coffee.backend.model.entity.product.Product;
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
}
