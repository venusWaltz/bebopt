package com.bebopt.app.security;

import org.springframework.stereotype.Component;

import com.bebopt.app.routing.Router;

/**
 * The {@code AuthenticatedUser} class manages the authentication state of the user.
 */
@Component
public class AuthenticatedUser {

    private static Boolean isLoggedIn = false;

    /**
     * Set the {@code isLoggedIn} flag to true.
     */
    public static void login() {
        isLoggedIn = true;
    }

    /**
     * Log the user out by redirecting first to logout endpoint, then to the home page, setting
     * the {@code isLoggedIn} flag to false.
     */
    public void logout() {
        Router.redirect("logout");
        Router.redirect("home");
        isLoggedIn = false;
    }

    /**
     * Sets the {@code isLoggedIn} flag to the specified boolean value.
     * 
     * @param bool The boolean value to set {@code isLoggedIn} to.
     */
    public static void setIsLoggedIn(Boolean bool) {
        isLoggedIn = bool;
    }

    /**
     * Returns the current authentication status of the user.
     * 
     * @return True if the user is logged in, false otherwise.
     */
    public static Boolean isLoggedIn() {
        return isLoggedIn;
    }
}