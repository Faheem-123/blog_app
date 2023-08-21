package blog_App.payloads;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String newPassword;
    private String confirmPassword;
}
