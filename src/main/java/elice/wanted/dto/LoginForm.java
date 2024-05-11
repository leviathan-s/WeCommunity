package elice.wanted.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
    @NotBlank(message = "닉네임을 입력해 주세요")
    private String nickname;

//    @NotBlank(message = "패스워드를 입력해 주세요")
    @NotBlank
    private String encryptedPassword;

    public LoginForm() {
    }
}
