package grepp.coffee.backend.model.service.question;

import grepp.coffee.backend.common.exception.ExceptionMessage;
import grepp.coffee.backend.common.exception.question.QuestionException;
import grepp.coffee.backend.controller.question.request.QuestionDeleteRequest;
import grepp.coffee.backend.controller.question.request.QuestionRegisterRequest;
import grepp.coffee.backend.controller.question.request.QuestionUpdateRequest;
import grepp.coffee.backend.model.entity.member.Member;
import grepp.coffee.backend.model.entity.question.Question;
import grepp.coffee.backend.model.repository.question.QuestionRepository;
import grepp.coffee.backend.model.service.member.MemberService;
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
    private final MemberService memberService;

    //FAQ 전체 조회
    public List<Question> readQuestionList() {return questionRepository.findAll();}

    //질문 등록
    @Transactional
    public void createQuestion(QuestionRegisterRequest request) {
        // 사용자 조회
        Member member = memberService.findByIdOrThrowMemberException(request.getMemberId());

        Question question = Question.builder()
                .memberId(member.getMemberId())
                .question(request.getQuestion())
                .answer(request.getAnswer())
                .build();
        questionRepository.save(question);
    }

    //질문 수정
    @Transactional
    public void updateQuestion(Long questionId, QuestionUpdateRequest request) {
        // 사용자 조회
        Member member = memberService.findByIdOrThrowMemberException(request.getMemberId());
        // 질문 조회
        Question question = findByIdOrThrowQuestionException(questionId);

        if(!member.getMemberId().equals(question.getMemberId())) {
            log.warn(">>>> {} : {} <<<<", questionId, ExceptionMessage.QUESTION_NOT_WRITTEN_BY_MEMBER);
            throw new QuestionException(ExceptionMessage.QUESTION_NOT_WRITTEN_BY_MEMBER);
        }

        // 수정된 정보를 반영
        question.updateQuestion(
                request.getQuestion(),
                request.getAnswer()
        );
    }


    //질문 삭제
    @Transactional
    public void deleteQuestion(Long questionId, QuestionDeleteRequest request) {
        // 사용자 조회
        Member member = memberService.findByIdOrThrowMemberException(request.getMemberId());
        // 질문 조회
        Question question = findByIdOrThrowQuestionException(questionId);

        if(!member.getMemberId().equals(question.getMemberId())) {
            log.warn(">>>> {} : {} <<<<", questionId, ExceptionMessage.QUESTION_NOT_WRITTEN_BY_MEMBER);
            throw new QuestionException(ExceptionMessage.QUESTION_NOT_WRITTEN_BY_MEMBER);
        }
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
