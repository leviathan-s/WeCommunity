package elice.wanted.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    private String name;

    public Category() {
    }

    public Category(String category_name) {
        this.name = category_name;
    }
}
