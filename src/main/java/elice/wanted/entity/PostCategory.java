package elice.wanted.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCategoryId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public PostCategory() {
    }

    public static PostCategory createPostCategory(Category category) {
        PostCategory postCategory = new PostCategory();
        postCategory.setCategory(category);
        return postCategory;
    }
}
