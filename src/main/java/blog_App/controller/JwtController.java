package blog_App.controller;


import blog_App.entity.UserOtp;
import blog_App.payloads.*;
import blog_App.service.EmailService;
import blog_App.service.PasswordService;
import blog_App.service.UserOtpService;
import blog_App.utils.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import blog_App.security.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import blog_App.security.JwtResponse;
import blog_App.security.JwtUtil;
import blog_App.security.UserDetailServiceImpl;
import blog_App.service.UserService;

import java.text.SimpleDateFormat;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Tag(name = "JwtController",description = "APIs for JwtController !!")
public class JwtController {
    @Autowired
    private EmailService emailService;
@Autowired
private UserOtpService userOtpService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

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

    @PostMapping("/update-password/{email}")
    public ResponseEntity<ApiResponse> updatePassword(@RequestBody UpdatePasswordRequest request, @PathVariable String email) {
        ApiResponse response = passwordService.updatePassword(request,email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam("email") String email, HttpSession session) {
        String otp = CommonUtil.getRandomOTP(6);
        System.out.println(otp);
        //code for otp send to email
        String to=email;
        String subject="OTP generate";
        String content="Here is your OTP !!"+otp+" ";
        boolean f=  emailService.sendEmail(subject,content,to);
        if(f){
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            UserOtp userOtp=new UserOtp();
            userOtp.setOtp(otp);
            userOtp.setEmail(email);
            userOtp.setOtpTime(timeStamp);
            userOtp.setStatus(1);
            this.userOtpService.saveOtp(userOtp);
//          session.setAttribute("sessionEmail",email);
//          session.setAttribute("sessionOTP",otp);
            return "OTP sent to your email!!";
        }else {
            return "Your email id is not right!!";
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOTP(@RequestBody ValidateOtpRequest request) {
        ApiResponse response = passwordService.validateOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, @PathVariable String email) {
        ApiResponse response = passwordService.changePassword(changePasswordRequest,email);
        return ResponseEntity.ok(response);
    }
}
