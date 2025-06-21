package models.services;

import config.JwtService;
import models.entities.User;
import models.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import requestBodies.SignupRequest;

@Service
@RequiredArgsConstructor          // <-- Spring injects all final fields
public class UserService {

    private final UserRepository      userRepository;
    private final PasswordEncoder     passwordEncoder;      // inject the bean
    private final JwtService          jwtService;
    private final AuthenticationManager authenticationManager;

    /* --------------------------------------------------------------------- */
    /*  Public helpers                                                       */
    /* --------------------------------------------------------------------- */

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    public String createUser(SignupRequest body) {


        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new IllegalStateException("E-mail already in use");
        }

        User user = new User();
        user.setEmail(body.getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setUsername(body.getUsername());
        user.setRole(User.Role.valueOf(body.getRole().toUpperCase()));
        userRepository.save(user);


        return jwtService.generateToken(user);
    }

    /**
     * Authenticates the credentials and returns a JWT.
     * Throws AuthenticationException when the credentials are wrong.
     */
    public String loginAndGenerateToken(String email, String rawPassword) {

        /* 1. Let Spring Security validate the credentials */
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, rawPassword));

        /* 2. Load the user (will exist because authentication succeeded) */
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        /* 3. Return JWT */
        return jwtService.generateToken(user);
    }
}