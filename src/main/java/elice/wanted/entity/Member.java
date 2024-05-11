package elice.wanted.entity;

import elice.wanted.dto.MemberResponseDto;
import elice.wanted.session.MemberSessionInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String nickname;
    private String encryptedPassword;
    private String email;
    private String contact;

    @Embedded
    private Address address;

    private String introduction;
    private int point;
    private boolean admin;

    public MemberSessionInfo toMemberSessionInfo() {
        return new MemberSessionInfo(id, nickname);
    }

    public MemberResponseDto toMemberResponseDto() {
        return new MemberResponseDto(id, nickname, email, contact, address, introduction, point, admin);
    }
}
