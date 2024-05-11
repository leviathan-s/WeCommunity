package elice.wanted.dto;

import elice.wanted.entity.Board;
import elice.wanted.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
public class BoardResponseDto {
    private Long id;
    private String subject;
    private MemberResponseDto memberResponseDto;
    private int postsCount;

    public BoardResponseDto(Long id, String subject, MemberResponseDto memberResponseDto, int postsCount) {
        this.id = id;
        this.subject = subject;
        this.memberResponseDto = memberResponseDto;
        this.postsCount = postsCount;
    }
}