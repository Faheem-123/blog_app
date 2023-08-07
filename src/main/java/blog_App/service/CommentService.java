package blog_App.service;

import blog_App.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer userId,Integer postId);
	void deleteComment(Integer commentId);

}
