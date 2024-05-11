package elice.wanted.entity;

import elice.wanted.dto.BoardResponseDto;
import elice.wanted.dto.BoardSaveDto;
import elice.wanted.dto.BoardUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Slf4j
public class Board extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public Board() {
    }

    public Board(String subject) {
        this.subject = subject;
    }

    public Board(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public Board(String subject, Member member) {
        this.subject = subject;
        this.member = member;
    }

    public void editBoardId(Long id) {
        this.id = id;
    }

    public BoardResponseDto toResponseDto() {
        return new BoardResponseDto(id, subject, member.toMemberResponseDto(), posts.size());
    }

    public BoardSaveDto toSaveDto() {
        return new BoardSaveDto(subject);
    }

    public BoardUpdateDto toUpdateDto() {
        return new BoardUpdateDto(id, subject);
    }
}
