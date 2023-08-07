package blog_App.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import blog_App.payloads.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import blog_App.entity.Category;
import blog_App.entity.Post;
import blog_App.entity.User;
import blog_App.exception.ResourceNotFoundException;
import blog_App.payloads.CategoryDto;
import blog_App.payloads.PostDto;
//import blog_App.payloads.PostResponse;
import blog_App.repository.CategoryRepository;
import blog_App.repository.PostRepository;
import blog_App.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		Post post = this.postDtoToPost(postDto);
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = this.postRepository.save(post);
		return this.postToPostDto(savedPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepository.save(post);
		return this.postToPostDto(updatedPost);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "Id", postId));
		return this.postToPostDto(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p= PageRequest.of(pageNumber, pageSize, sort);
		 Page<Post> postWithPage = this.postRepository.findAll(p);
		 List<Post> posts = postWithPage.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());
		PostResponse pr=new PostResponse();
		pr.setContent(postDtos);
		pr.setPageNumber(postWithPage.getNumber());
		pr.setPageSize(postWithPage.getSize());
		pr.setTotalElements(postWithPage.getTotalElements());
		pr.setTotalPages(postWithPage.getTotalPages());
		pr.setLastPage(postWithPage.isLast());
		return pr;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts = this.postRepository.findByCategory(category);
//		List<Post> posts = this.postRepository.findByCategoryId(categoryId);
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToPostDto(post))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		List<Post> posts = this.postRepository.findByUser(user);
//		List<Post> posts = this.postRepository.findByUserId(userId);
		List<PostDto> postDtos = posts.stream().map((post) -> this.postToPostDto(post))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "Id", postId));
		this.postRepository.delete(post);
	}

	public Post postDtoToPost(PostDto postDto) {
		Post post = this.modelMapper.map(postDto, Post.class);
		return post;
	}

	public PostDto postToPostDto(Post post) {
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto>  getPostsByTitle(String title) {

//		 List<Post> posts = this.postRepository.findByTitleContaining(title);
		List<Post> posts = this.postRepository.searchPostByTitle("%"+title+"%");
		if(posts.isEmpty()){
			throw new ResourceNotFoundException();
		}
		 List<PostDto> postDtos = posts.stream().map((post) -> this.postToPostDto(post))
					.collect(Collectors.toList());
			return postDtos;
	}
}
