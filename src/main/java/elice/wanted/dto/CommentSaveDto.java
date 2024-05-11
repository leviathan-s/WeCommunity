package elice.wanted.dto;

import elice.wanted.entity.Member;
import elice.wanted.entity.Post;
import elice.wanted.session.MemberSessionInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveDto {
    private String content;
    private boolean secret;
    private MemberSessionInfo memberSessionInfo;

    public boolean getSecret() {
        return secret;
    }
}
