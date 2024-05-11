package elice.wanted.dto;

import elice.wanted.entity.Address;
import elice.wanted.entity.Member;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSaveDto {

    @NotBlank(message = "닉네임은 필수 입력사항입니다")
    private String nickname;

    @NotBlank(message = "패스워드는 필수 입력사항입니다")
    private String encryptedPassword;

    @Email(message = "올바른 이메일 형식을 입력해 주세요")
    private String email;

    @NotBlank(message = "연락처는 필수 입력사항입니다")
    private String contact;
    private String introduction;

    public MemberSaveDto() {
    }

    public Member toEntity() {
        Member member = new Member();
        member.setNickname(nickname);
        member.setEncryptedPassword(encryptedPassword);
        member.setEmail(email);
        member.setContact(contact);
        member.setIntroduction(introduction);
        return member;
    }
}
