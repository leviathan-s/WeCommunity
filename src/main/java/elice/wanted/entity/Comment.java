package elice.wanted.entity;

import elice.wanted.dto.CommentResponseDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Comment extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;


    @Column(nullable = false)
    private boolean secret;

    private Long comment_priority;

    public CommentResponseDto toResponseDto() {
        return new CommentResponseDto(id, content, member.toMemberResponseDto(), secret, comment_priority, getCreatedDate(), getLastModifiedDate());
    }
}
