package elice.wanted.dto;

import elice.wanted.entity.Board;
import elice.wanted.entity.Member;
import elice.wanted.entity.PostCategory;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private MemberResponseDto memberResponseDto;
    private String content;
    private List<String> postCategories = new ArrayList<>();
    private List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
    private BoardResponseDto boardResponseDto;
    private Long postPriority;
    private boolean isNotice;
    private Long views;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public PostResponseDto(Long id,
                           String title,
                           MemberResponseDto memberResponseDto,
                           String content, List<String> postCategories,
                           List<CommentResponseDto> commentResponseDtos,
                           BoardResponseDto boardResponseDto,
                           Long postPriority,
                           boolean isNotice,
                           Long views,
                           LocalDateTime createdDate,
                           LocalDateTime lastModifiedDate) {
        this.id = id;
        this.title = title;
        this.memberResponseDto = memberResponseDto;
        this.content = content;
        this.postCategories = postCategories;
        this.commentResponseDtos = commentResponseDtos;
        this.boardResponseDto = boardResponseDto;
        this.postPriority = postPriority;
        this.isNotice = isNotice;
        this.views = views;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean getIsNotice() {
        return isNotice;
    }
}
