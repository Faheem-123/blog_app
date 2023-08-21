package blog_App.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "USER_OTP")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;


    @Column(name = "EMAIL")
    private String email;

    @Column(name = "OTP")
    private String otp;

    @Column(name = "CREATE_TIME")
    private String otpTime;

    @Column(name = "status")
    private Integer status;





}
