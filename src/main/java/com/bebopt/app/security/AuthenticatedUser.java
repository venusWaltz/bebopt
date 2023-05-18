package com.bebopt.app.security;

import org.springframework.stereotype.Component;

import com.bebopt.app.data.spotify.Redirect;

@Component
public class AuthenticatedUser {

    private static Boolean isLoggedIn = false;

    public static void login() {
        isLoggedIn = true;
    }

    public void logout() {
        Redirect.redirect("logout");  // log out of spotify account
        Redirect.redirect("home");
        isLoggedIn = false;
    }

    public static void setIsLoggedIn(Boolean bool) {
        isLoggedIn = bool;
    }

    public static Boolean isLoggedIn() {
        return isLoggedIn;
    }

}
