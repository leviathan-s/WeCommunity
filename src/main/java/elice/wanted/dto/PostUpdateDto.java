package elice.wanted.dto;

import elice.wanted.entity.PostCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostUpdateDto {

    private Long id;

    @NotBlank(message = "글 제목은 필수 입력사항입니다")
    private String title;

    @NotBlank(message = "글 내용은 필수 입력사항입니다")
    private String content;
    private boolean notice;
    private List<String> postCategories;

    public PostUpdateDto(String title, String content, boolean notice, List<String> postCategories) {
        this.title = title;
        this.content = content;
        this.notice = notice;
        this.postCategories = postCategories;
    }

    public boolean getNotice() {
        return notice;
    }

    public void setNotice(boolean b) {
        notice = b;
    }
}
