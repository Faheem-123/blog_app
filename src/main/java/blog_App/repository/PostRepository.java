package blog_App.repository;

import java.util.List;

import blog_App.payloads.PostUserCategoryResponse;
import blog_App.payloads.UserPostCategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import blog_App.entity.Category;
import blog_App.entity.Post;
import blog_App.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);

//	List<Post> findByTitleContaining(String title);
	@Query("select p from Post p where p.title like :key")
	List<Post> searchPostByTitle(@Param("key") String title);
	@Query("select new blog_App.payloads.PostUserCategoryResponse(p.title, u.username, c.categoryTitle) " +
			"from Post p JOIN p.user u JOIN p.category c")
	List<PostUserCategoryResponse> getPostUserCategoryDetails();
}
