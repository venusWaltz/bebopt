package com.bebopt.app.data.controller;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    // create spotify authorization URL + update redirect url in RedirectController class
    public static void Auth() {
        String str = AuthController.spotifyLogin();
        RedirectController.setUrl(str);
    }

}
