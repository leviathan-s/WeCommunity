package elice.wanted.entity;

import elice.wanted.dto.PostResponseDto;
import elice.wanted.dto.PostUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Post extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCategory> postCategories = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    private Long postPriority = 0L;
    private boolean is_notice;
    private Long views = 0L;

    public void viewPost() {
        views++;
    }

    public boolean getIsNotice() {
        return is_notice;
    }

    public Post() {
        views = 0L;
    }

    public PostUpdateDto toPostUpdateDto() {
        return new PostUpdateDto(title, content, is_notice, postCategories.stream().map((pc) -> pc.getCategory().getName()).collect(Collectors.toList()));
    }

    public PostResponseDto toPostResponseDto() {
        List<String> categories = postCategories.stream().map((pc) -> pc.getCategory().getName()).collect(Collectors.toList());

        return new PostResponseDto(id,
                title,
                member.toMemberResponseDto(),
                content,
                categories,
                comments.stream().map((c) -> c.toResponseDto()).collect(Collectors.toList()),
                board.toResponseDto(),
                postPriority,
                is_notice,
                views,
                getCreatedDate(),
                getLastModifiedDate());
    }

    public Post(String title, String content, Board board, boolean is_notice) {
        this.title = title;
        this.content = content;
        this.board = board;
        this.is_notice = is_notice;
    }
}
