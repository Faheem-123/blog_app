package blog_App.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import blog_App.payloads.UserPostCategoryResponse;
import blog_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog_App.payloads.ApiResponse;
import blog_App.payloads.UserDto;
import blog_App.service.UserService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@Tag(name = "UserController",description = "APIs for User !!")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	//Create user with details
	@PostMapping("/register")
	@Operation(summary = "Create new user !!",description = "This is user created API !!")
	@ApiResponses(value = {
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "SUCCESS | OK"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "New user created")
	})
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}

	//Update user with given id
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId){
		UserDto updatedUserDto = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUserDto);
	}

	//Delete user only by admin login
	// Admin access only
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId){
	this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully","200",true),HttpStatus.OK);
	}

	//Get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	//Get user with given id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

	@GetMapping("/post/category/details")
	public ResponseEntity<List<UserPostCategoryResponse>> getUserPostCategoryDetails(){
		return ResponseEntity.ok(this.userRepository.getUserPostCategoryDetail());
	}

}
