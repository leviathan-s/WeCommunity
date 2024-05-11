package elice.wanted.dto;

import elice.wanted.entity.Board;
import elice.wanted.entity.Member;
import elice.wanted.session.MemberSessionInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Getter
@Setter
public class BoardSaveDto {

    @NotBlank(message = "게시판 이름은 필수 입력사항입니다")
    private String subject;
    private Member member;
    private MemberSessionInfo memberSessionInfo;

    public BoardSaveDto(String subject) {
        this.subject = subject;
    }

    public BoardSaveDto() {
    }

    public Board toEntity() {
        return new Board(subject, member);
    }
}
