package com.examinator.security.domain.userCredentials;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    @Override
    Optional<UserCredentials> findById(Long id);

    Optional<UserCredentials> findByUserId(Long userId);

    Optional<UserCredentials> findByActivateToken(String activateToken);
}
