package com.bebopt.app.security;

import com.bebopt.app.data.controller.RedirectController;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUser {

    private static Boolean isLoggedIn = false;

    public void login() {
        isLoggedIn = true;
    }

    public void logout() {
        RedirectController.redirect("logout");  // log out of spotify account
        isLoggedIn = false;
    }

    public static void setIsLoggedIn(Boolean bool) {
        isLoggedIn = bool;
    }

    public static Boolean isLoggedIn() {
        return isLoggedIn;
    }

}
