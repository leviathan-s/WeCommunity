package elice.wanted.session;


import lombok.Getter;

@Getter
public class MemberSessionInfo {
    private Long id;
    private String nickname;

    public MemberSessionInfo(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
