package grepp.coffee.backend.model.repository.question;

import grepp.coffee.backend.model.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByQuestionIdAndMemberId(Long questionId, Long memberId);
}
