package blog_App.service;

import blog_App.entity.User;
import blog_App.entity.UserOtp;
import blog_App.payloads.*;
import blog_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserOtpService userOtpService;
    public ApiResponse updatePassword(UpdatePasswordRequest request, String email) {
        String newPassword = request.getNewPassword();
        String confirmPassword = request.getConfirmPassword();
        String oldPassword = request.getOldPassword();
        User dbUSer = userRepository.findByEmail(email);
        if (dbUSer == null) {
            return new ApiResponse("User is null !!","404",false);
        } else if (oldPassword.equals(newPassword)) {
            return new ApiResponse("Old password should not be same with New password !!","500",false);
        } else if (!newPassword.equals(confirmPassword)) {
            return new ApiResponse("Confrim password is not same !!","500",false);
        } else {
            boolean isPasswordMatched = bcryptEncoder.matches(request.getOldPassword(), dbUSer.getPassword());
            if (!isPasswordMatched) {
                return new ApiResponse("Password is not matched !!","500",false);
            } else {
                String encodePassword = passwordEncoder.encode(request.getConfirmPassword());
                dbUSer.setPassword(encodePassword);
                userRepository.save(dbUSer);
            }
        }
        return new ApiResponse("Password changed successfully","200",true);
    }

    public ApiResponse validateOtp(ValidateOtpRequest request) {
        String otp = request.getOtp();
        String email = request.getEmail();
        User dbUser = userRepository.findByEmail(email);
        if (dbUser == null) {
            return new ApiResponse("User is null !!","404",false);
        } else {
            UserOtp userOtp = userOtpService.findByOTP(otp);
            if (userOtp == null) {
                return new ApiResponse("OTP is null !!","404",false);
            }
//            else if (userOtp.getOtp()!=otp) {
//                return new ApiResponse("OTP not matched !!","404",false);
//            }
           else {
                Integer status = userOtp.getStatus();
            }
        }
        return new ApiResponse("OTP verified successfully","200",true);
    }

    public ApiResponse changePassword(ChangePasswordRequest changePasswordRequest, String email) {
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmPassword = changePasswordRequest.getConfirmPassword();
        User dbUser = userRepository.findByEmail(email);
        if (dbUser == null) {
            return new ApiResponse("User is null !!","404",false);
        }
        else if (!newPassword.equals(confirmPassword)) {
            return new ApiResponse("Confrim password is not same !!","500",false);
        }
        else {
            User user = userRepository.findByEmail(email);
                String encodePassword = passwordEncoder.encode(changePasswordRequest.getConfirmPassword());
            user.setPassword(encodePassword);
            userRepository.save(user);
        }
        return new ApiResponse("Password changed successfully","200",true);
    }
}
