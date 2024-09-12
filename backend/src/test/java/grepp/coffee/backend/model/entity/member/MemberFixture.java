package grepp.coffee.backend.model.entity.member;

public class MemberFixture {


    // 테스트용 멤버 생성
    public static Member registerMember() {
        return Member.builder()
                .email("email")
                .password("password".getBytes())
                .point(1)
                .address("address")
                .postcode("postcode")
                .build();
    }

    public static Member registerAdminMember() {
        return Member.builder()
                .email("admin@example.com")
                .password("password".getBytes())
                .point(1)
                .address("address")
                .postcode("postcode")
                .build();
    }

}
