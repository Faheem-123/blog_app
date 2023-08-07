package blog_App.controller;


import javax.validation.Valid;

import blog_App.security.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog_App.payloads.UserDto;
//import blog_App.security.JwtRequest;
import blog_App.security.JwtResponse;
import blog_App.security.JwtUtil;
import blog_App.security.UserDetailServiceImpl;
import blog_App.service.UserService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    //Generate token using username and password
    @PostMapping("/token")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        }catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }
        UserDetails userDetails = this.userDetailServiceImpl.loadUserByUsername(jwtRequest.getUsername());
       String token= this.jwtUtil.generateToken(userDetails);
       JwtResponse response =new JwtResponse();
       response.setToken(token);
       return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
    }

    //Register new user with user details
    @PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto regUserDto = this.userService.registerNewUser(userDto);
		return new ResponseEntity<>(regUserDto,HttpStatus.CREATED);
	}
}
