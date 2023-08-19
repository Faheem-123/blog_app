package blog_App.payloads;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min=4,message="Username must be min of 4 character !!")
    private String username;
    @Email(message="Email is not valid !!")
    private String email;
    @NotEmpty
    @Size(min=3,max=10,message="password must be min of 3 and max of 10 !!")
    private String password;
    @NotEmpty
    private String about;
    private boolean user_type;
    List<RoleDto> roles=new ArrayList<RoleDto>();
}
