package com.examinator.security.domain.userCredentials;

public interface UserCredentialsService {

    UserCredentials findUserCredentialsByUserId(Long id);
}
