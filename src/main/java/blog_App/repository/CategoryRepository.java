package blog_App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog_App.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
