package contorllers;

import models.entities.User;
import models.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User created = userService.createUser(user);
        // return 201 Created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
}