package blog_App.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import blog_App.payloads.ApiResponse;
import blog_App.payloads.CommentDto;
import blog_App.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentService commentService;

	//Create comment on the basis of user with specific post
	@PostMapping("/user/{userId}/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
			@PathVariable("userId") Integer userId, @PathVariable("postId") Integer postId) {
		CommentDto saveCommentDto = this.commentService.createComment(commentDto,userId,postId);
		return new ResponseEntity<>(saveCommentDto, HttpStatus.CREATED);
	}

	//Delete comment using id
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("commentId") Integer commentId) {
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", "200", true),
				HttpStatus.OK);
	}

}
