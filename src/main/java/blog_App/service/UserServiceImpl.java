package blog_App.service;

import java.util.List;
import java.util.stream.Collectors;
import blog_App.entity.Role;
import blog_App.exception.UserAlreadyExistException;
import blog_App.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import blog_App.entity.User;
import blog_App.exception.ResourceNotFoundException;
import blog_App.payloads.UserDto;
import blog_App.repository.UserRepository;
import blog_App.utils.AppConstants;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.userDtoToUser(userDto);
		if(userRepository.existsByEmail(userDto.getEmail())){
			throw new UserAlreadyExistException("Email already exist !! ");
		}
		User savedUser=this.userRepository.save(user);
		return this.userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User updatedUser=this.userRepository.save(user);
		return this.userToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepository.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		this.userRepository.delete(user);
	}

	public User userDtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	public UserDto userToUserDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		Role role=new Role();
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		if(user.isUser_type()==true) {
			 role=this.roleRepository.findById(AppConstants.ADMIN_ROLE).get();
		}else {
		 role=this.roleRepository.findById(AppConstants.NORMAL_ROLE).get();
		}
		user.getRoles().add(role);
		User saveUser = this.userRepository.save(user);
		return this.modelMapper.map(saveUser, UserDto.class);
	}

}
