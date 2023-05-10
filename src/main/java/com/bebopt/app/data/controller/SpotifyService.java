package com.bebopt.app.data.controller;

import org.springframework.stereotype.Service;

// import com.bebopt.app.data.entity.CurrentUser;

import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;


@Service
public class SpotifyService {

    private final static AuthController authController = new AuthController();

    // create spotify authorization URL + update redirect url in RedirectController class
    public static void handleUserLogin() {
        String str = authController.spotifyLogin(); // generate URL
        RedirectController.setUrl(str);             // set value of URL in RedirctController
        RedirectController.redirect("page-redirect");
    }

}

