package com.bebopt.app.data.controller;

import java.net.URI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bebopt.app.data.entity.Client;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

@RestController
@RequestMapping("")
public class AuthController {
    private static String clientID = Client.getClientID();
    private static String clientSecret = Client.getClientSecret();
    private static String scopes = "user-read-email,user-read-private,playlist-read-private";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/home");
    private String code = "";

    private static SpotifyApi spotifyApi = new SpotifyApi.Builder()
        .setClientId(clientID)
        .setClientSecret(clientSecret)
        .setRedirectUri(redirectUri)
        .build();

    @GetMapping("spotifyUrl")
    @ResponseBody
    public static String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .scope(scopes)
            .show_dialog(true)
            .build();
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

}
