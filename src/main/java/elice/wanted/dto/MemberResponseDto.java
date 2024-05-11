package elice.wanted.dto;

import elice.wanted.entity.Address;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private Long id;
    private String nickname;
    private String email;
    private String contact;

    @Embedded
    private Address address;

    private String introduction;
    private int point;
    private boolean admin;

    public MemberResponseDto(Long id, String nickname, String email, String contact, Address address, String introduction, int point, boolean admin) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.introduction = introduction;
        this.point = point;
        this.admin = admin;
    }
}
