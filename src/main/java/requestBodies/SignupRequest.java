package requestBodies;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class SignupRequest {
    @NotBlank
    private String username;

    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    @NotBlank
    private String password;
    private  String role;
}