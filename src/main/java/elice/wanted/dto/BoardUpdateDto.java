package elice.wanted.dto;

import elice.wanted.entity.Board;
import elice.wanted.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardUpdateDto {

    private Long id;
    @NotBlank(message = "게시판 이름은 필수 입력사항입니다")
    private String subject;


    public BoardUpdateDto(String subject) {
        this.subject = subject;
    }

    public BoardUpdateDto(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public BoardUpdateDto() {
    }

    public Board toEntity() {
        return new Board(id, subject);
    }
}
