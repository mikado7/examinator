package com.examinator.security.domain.user;

import com.examinator.common.DataValidator;
import com.examinator.security.Authority;
import com.examinator.security.domain.userCredentials.UserCredentials;
import com.examinator.security.domain.userCredentials.UserCredentialsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserCredentialsRepository userCredentialsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(String username, String password) {
        if (!DataValidator.isValidEmail(username)) {
            throw new IllegalArgumentException("Email is not valid");
        }

//        if (!DataValidator.isValidPassword(password)) {
//            throw new IllegalArgumentException("Password is not valid");
//        }

        if (userRepository.findByUsername(username.toLowerCase()).orElse(null) != null) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setAuthority(Authority.USER);
        userRepository.save(user);

        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(passwordEncoder.encode(password));
        userCredentials.setEnabled(false);
        userCredentials.setCreatedDate(new Date());
        userCredentials.setActivateToken(UUID.randomUUID().toString());
        userCredentials.setActivateTokenExpiryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        userCredentials.setUserId(user.getId());
        userCredentialsRepository.save(userCredentials);

        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(username);
//            mailMessage.setSubject("Activate your account");
//            mailMessage.setText("To activate your account click here: http://localhost:8080/api/auth/activate?token=" + userCredentials.getActivateToken());
//            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            logger.error("Failed to send email");
            userRepository.delete(user);
            userCredentialsRepository.delete(userCredentials);
            throw e;
        }

        return user;
    }

    @Override
    public void activate(String token) {
        UserCredentials userCredentials = userCredentialsRepository.findByActivateToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        if (userCredentials.getActivateTokenExpiryDate().before(new Date())) {
            throw new IllegalArgumentException("Token expired");
        }
        userCredentials.setEnabled(true);
        userCredentials.setActivateToken(null);
        userCredentials.setActivateTokenExpiryDate(null);
        userCredentialsRepository.save(userCredentials);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserCredentials findUserCredentialsByUserId(Long id) {
        return userCredentialsRepository.findById(id).orElseThrow();
    }

}
