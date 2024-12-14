package com.examinator;

import com.examinator.security.domain.user.User;
import com.examinator.security.domain.user.UserService;
import com.examinator.security.domain.userCredentials.UserCredentialsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserService userService;
    private final UserCredentialsService userCredentialsService;

    public DatabaseLoader(UserService userService, UserCredentialsService userCredentialsService) {
        this.userService = userService;
        this.userCredentialsService = userCredentialsService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = userService.save("user@gmail.com", "password");
        userService.activate(userCredentialsService.findUserCredentialsByUserId(user.getId()).getActivateToken());
    }
}
