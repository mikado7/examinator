//package com.examinator.security;
//
//import com.examinator.security.model.*;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//public class RestAuthenticationProvider implements AuthenticationProvider {
//
//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    public RestAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) {
//        Object principal = authentication.getPrincipal();
//        if (!(principal instanceof UserPrincipal)) {
//            throw new RuntimeException("Principal not found");
//        }
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        UserCredentials credentials = (UserCredentials) authentication.getCredentials();
//        SecurityUser securityUser = authenticateUser(userPrincipal.getValue(), credentials.getPassword());
//        assert securityUser != null;
//        return new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//
//    private SecurityUser authenticateUser(String username, String password) {
//        User user = userService.getUserByEmail(username);
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        UserCredentials userCredentials = userService.findUserCredentialsByUserId(user.getId());
//        if (userCredentials == null) {
//            throw new RuntimeException("User credentials not found");
//        }
//
//        if (passwordEncoder.matches(password, userCredentials.getPassword())) {
//            return new SecurityUser(user.getId());
//        }
//
//        return null;
//    }
//}
