package com.examinator.security.domain.userCredentials;

import org.springframework.stereotype.Service;

@Service
public class UserCredentialsServiceImpl implements UserCredentialsService{

    private UserCredentialsRepository userCredentialsRepository;

    public UserCredentialsServiceImpl(UserCredentialsRepository userCredentialsRepository) {
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @Override
    public UserCredentials findUserCredentialsByUserId(Long id) {
        return userCredentialsRepository.findById(id).orElseThrow();
    }
}
