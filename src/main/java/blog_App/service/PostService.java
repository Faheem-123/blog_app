package blog_App.service;

import java.util.List;

import blog_App.payloads.PostDto;
//import blog_App.payloads.PostResponse;
import blog_App.payloads.PostResponse;
import blog_App.payloads.UserDto;

public interface PostService {
	PostDto createPost(PostDto post,Integer userId, Integer categoryId);
	PostDto updatePost(PostDto post, Integer postId);
	PostDto getPostById(Integer postId);
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	List<PostDto> getPostsByCategory(Integer categoryId);
	List<PostDto> getPostsByUser(Integer userId);
	List<PostDto> getPostsByTitle(String title);
	void deletePost(Integer postId);


}
