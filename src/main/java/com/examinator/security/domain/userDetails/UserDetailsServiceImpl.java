package com.examinator.security.domain.userDetails;

import com.examinator.security.domain.user.User;
import com.examinator.security.domain.user.UserService;
import com.examinator.security.domain.userCredentials.UserCredentials;
import com.examinator.security.domain.userCredentials.UserCredentialsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserService userService;
    private final UserCredentialsService userCredentialsService;

    public UserDetailsServiceImpl(UserService userService, UserCredentialsService userCredentialsService) {
        this.userService = userService;
        this.userCredentialsService = userCredentialsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        UserCredentials userCredentials = userCredentialsService.findUserCredentialsByUserId(user.getId());
        return new UserDetailsImpl(
                user.getUsername(),
                userCredentials.getPassword(),
                userCredentials.isEnabled(),
                true,
                true,
                true,
                Set.of(new SimpleGrantedAuthority(user.getAuthority().name())));
    }
}
