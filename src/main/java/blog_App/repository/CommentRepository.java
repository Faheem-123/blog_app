package blog_App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import blog_App.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
