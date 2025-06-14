package contorllers;

import jakarta.validation.Valid;
import models.entities.User;
import models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import requestBodies.SignInRequest;
import requestBodies.SignupRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserService userService;
   @Autowired
    public AuthController( UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest body, BindingResult result) {
        try {
            if(result.hasErrors()) {
                return ResponseEntity.badRequest().body("Data validation failed");
            }
            User newUser = userService.findByEmail(body.getEmail());
            if (newUser != null) {
                return ResponseEntity
                        .status(HttpStatus.IM_USED)
                        .body("Email already exists");
            }


            User user = new User();
            user.setEmail(body.getEmail());
            user.setPassword(body.getPassword());
            user.setUsername(body.getUsername());
            user.setRole(User.Role.valueOf(body.getRole()));
            User createdUser = userService.createUser(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User created!");
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("server error occurred!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequest body, BindingResult result) {
        return ResponseEntity.status(HttpStatus.CREATED).body("User logged in");
    }

    @PostMapping("/forgetpassword")
    public ResponseEntity<?> forgetPassword(@RequestBody SignInRequest body) {
        return ResponseEntity.status(HttpStatus.CREATED).body("User logged in");
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<?> resetPassword(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}