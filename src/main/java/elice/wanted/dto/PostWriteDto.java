package elice.wanted.dto;

import elice.wanted.entity.Board;
import elice.wanted.entity.Member;
import elice.wanted.entity.Post;
import elice.wanted.entity.PostCategory;
import elice.wanted.session.MemberSessionInfo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostWriteDto {

    @NotBlank(message = "글 제목은 필수 입력사항입니다")
    private String title;

    @NotBlank(message = "글 내용은 필수 입력사항입니다")
    private String content;

    private MemberSessionInfo memberSessionInfo;
    private Long boardId;

    private List<String> postCategories = new ArrayList<>();
    private boolean isNotice;

    public PostWriteDto() {
    }

    public PostWriteDto(String title, String content, List<String> postCategories, boolean isNotice) {
        this.title = title;
        this.content = content;
        this.postCategories = postCategories;
        this.isNotice = isNotice;
    }

    public boolean getIsNotice() {
        return isNotice;
    }
    public void setIsNotice(boolean isNotice) {
        this.isNotice = isNotice;
    }
//    public Post toEntity() {
//        return new Post()
//    }
}
