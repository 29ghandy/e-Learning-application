package contorllers;

import jakarta.validation.Valid;
import models.entities.User;
import models.services.UserService;
import org.antlr.v4.runtime.misc.Pair;
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



            String token = userService.createUser(body);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(token + " User created!");
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("server error occurred!");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequest body, BindingResult result) {
       // check then create token and send it in the response
       try {
           if(result.hasErrors()) {
               return ResponseEntity.badRequest().body("Data validation failed");
           }
           String token = userService.loginAndGenerateToken(body.getEmail(), body.getPassword());
           if(token != null) {
               Pair<String,String> pair = new Pair<>("User logged in", token);
               return ResponseEntity.ok().body(pair);
           }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user name or password incorrect");
       }
       catch (Exception e) {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("server error occurred!");
       }

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