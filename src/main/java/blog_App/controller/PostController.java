package blog_App.controller;
import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import blog_App.payloads.*;
import blog_App.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import blog_App.service.PostService;
import blog_App.utils.AppConstants;
@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/api")
@Tag(name = "PostController",description = "APIs for Post !!")
@SecurityRequirement(name="securityScheme")
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private PostRepository postRepository;

	//Create post on the basic of user id with specific category id
	@PostMapping("posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
												  @RequestParam Integer userId, @RequestParam Integer categoryId){
		PostDto createPostDto = this.postService.createPost(postDto,userId,categoryId);
		return new ResponseEntity<>(createPostDto,HttpStatus.CREATED);
	}
	// Get all posts of specific user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable("userId") Integer userId){
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
	}

	//Get all posts of specific category id
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable("categoryId") Integer categoryId){
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);
	}

	//Get all posts with pagination
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY, required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR, required=false) String sortDir){
		return ResponseEntity.ok(this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir));
	}

	//Get single post by id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable("postId") Integer postId){
		return ResponseEntity.ok(this.postService.getPostById(postId));
	}

	//Delete specific post
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
	this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully","200",true),HttpStatus.OK);
	}

	//Update post by id
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("postId") Integer postId){
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPostDto);
	}

	//Search post by title field
	@GetMapping("/post/search/{title}")
	public ResponseEntity<List<PostDto>> getPost(@PathVariable("title") String title){
		return ResponseEntity.ok(this.postService.getPostsByTitle(title));
	}

	//Get post_title, username, category_name details from user, post, and category tables using join query
	@GetMapping("/post/user/category/details")
	public ResponseEntity<List<PostUserCategoryResponse>> getUserPostCategoryDetails(){
		return ResponseEntity.ok(this.postRepository.getPostUserCategoryDetails());
	}


}
