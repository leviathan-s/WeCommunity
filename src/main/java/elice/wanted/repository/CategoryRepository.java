package elice.wanted.repository;

import elice.wanted.entity.Category;
import elice.wanted.service.CategoryService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public Category findByName(String name);
}
