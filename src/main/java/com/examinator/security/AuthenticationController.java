package com.examinator.security;

import com.examinator.security.domain.user.User;
import com.examinator.security.domain.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest signupRequest) {
        logger.info("signup");
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();
        return userService.save(username, password);
    }

    @GetMapping("/activate")
    public void activate(@RequestParam String token) {
        userService.activate(token);
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page!";
    }

    @GetMapping("/login")
    public String login() {
        return "Welcome to the login page!";
    }


}
