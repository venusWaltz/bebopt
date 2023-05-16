package com.bebopt.app.security;

import com.bebopt.app.data.entity.User;
import com.bebopt.app.data.service.UserRepository;
import com.vaadin.flow.spring.security.AuthenticationContext;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;
    private static Boolean isLoggedIn = false;

    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }

    public Optional<User> get() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class)
                .map(userDetails -> userRepository.findByUsername(userDetails.getUsername()));
    }

    public void logout() {
        authenticationContext.logout();
    }

    public static void setIsLoggedIn(Boolean bool) {
        isLoggedIn = bool;
    }

    public static Boolean getIsLoggedIn() {
        return isLoggedIn;
    }

}
