package dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserDto {
    private String username;
    private String password;
    private String email;

}
