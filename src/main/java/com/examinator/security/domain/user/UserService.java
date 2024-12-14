package com.examinator.security.domain.user;

import com.examinator.security.domain.userCredentials.UserCredentials;

public interface UserService {

    User save(String email, String password);

    void activate(String token);

    User getUserByUsername(String email);

    UserCredentials findUserCredentialsByUserId(Long id);
}
