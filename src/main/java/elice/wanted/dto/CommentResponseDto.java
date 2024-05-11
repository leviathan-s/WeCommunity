package elice.wanted.dto;

import elice.wanted.entity.Member;
import elice.wanted.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String content;
    private MemberResponseDto memberResponseDto;
    private PostResponseDto postResponseDto;
    private boolean secret;
    private Long comment_priority;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public CommentResponseDto(Long id, String content, MemberResponseDto memberResponseDto, boolean secret, Long comment_priority, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.content = content;
        this.memberResponseDto = memberResponseDto;
        this.secret = secret;
        this.comment_priority = comment_priority;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
