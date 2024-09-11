package grepp.coffee.backend.model.entity.member;

import grepp.coffee.backend.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "MEMBERS")
@Table(name = "MEMBERS")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", nullable = false)
    private Long memberId;

    @Column(name = "EMAIL", length = 50, nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private byte[] password;

    @Column(name = "POINT", nullable = true)
    private int point;

    @Column(name = "ADDRESS", length = 200, nullable = false)
    private String address;

    @Column(name = "POSTCODE", length = 200)
    private String postcode;

    @Builder
    public Member(Long memberId, String email, byte[] password, int point, String address, String postcode) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.point = point;
        this.address = address;
        this.postcode = postcode;
    }
}