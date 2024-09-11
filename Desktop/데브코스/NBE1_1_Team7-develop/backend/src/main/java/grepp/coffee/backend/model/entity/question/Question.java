package grepp.coffee.backend.model.entity.question;

import grepp.coffee.backend.model.entity.BaseEntity;
import grepp.coffee.backend.model.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "QUESTION")
@Table(name = "QUESTION")
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QUESTION_ID")
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(name = "QUESTION", length = 200, nullable = false)
    private String question;

    @Column(name = "ANSWER", length = 200, nullable = false)
    private String answer;

    @Builder
    public Question(Member member, String question, String answer) {
        this.member = member;
        this.question = question;
        this.answer = answer;
    }
}