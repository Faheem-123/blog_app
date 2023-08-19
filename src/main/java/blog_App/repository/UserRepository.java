package blog_App.repository;

import java.util.List;
import java.util.Optional;

import blog_App.payloads.UserPostCategoryResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import blog_App.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
	@Query("select new blog_App.payloads.UserPostCategoryResponse(u.username, p.title, c.categoryTitle) " +
			"from User u JOIN u.posts p JOIN p.category c")
	public List<UserPostCategoryResponse> getUserPostCategoryDetail();

	Boolean existsByEmail(String email);
}
