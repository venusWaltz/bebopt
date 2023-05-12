package com.bebopt.app.data.controller;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bebopt.app.data.entity.Client;
import com.bebopt.app.data.entity.SpotifyUser;
import com.bebopt.app.views.MainLayout;

import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@RestController
@RequestMapping("")
public class AuthController {
    private static String clientID = Client.getClientID();
    private static String clientSecret = Client.getClientSecret();
    private static String scopes = "user-read-private,playlist-read-private,user-top-read,user-read-currently-playing";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");
    private String code = "";

    // create SpotifyApi object to use when sending requests to Spotify API
    public static SpotifyApi spotifyApi = new SpotifyApi.Builder()
        .setClientId(clientID)
        .setClientSecret(clientSecret)
        .setRedirectUri(redirectUri)
        .build();

    // create spotify login URI for user authorization
    @GetMapping("spotify-url")
    @ResponseBody
    public static String spotifyLogin() {
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .scope(scopes)
            .show_dialog(true)
            .build();
        final URI uri = authorizationCodeUriRequest.execute();
        return uri.toString();
    }

    // following authentication, the app is redirected to our callback URI (http://localhost:8080/callback);
    // from here, we get the code Spotify has added to our URI and use it to get a temporary access token
    @GetMapping("callback")
    public String getUserCode(@RequestParam("code") String userCode, HttpServletResponse response) throws IOException{
        code = userCode;    // set program var code to userCode from URI
        
        // use retrieved code to send request to Spotify API for access token
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code).build();
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error" + e.getMessage());
        }

        response.sendRedirect("http://localhost:8080/home");    // redirect to home page after retrieving access token

        SpotifyUser thisSpotifyUser = new SpotifyUser(SpotifyService.getCurrentUser());
        MainLayout.spotifyUser = thisSpotifyUser;
        
        return spotifyApi.getAccessToken();
    }   // handle errors

    // handle refresh here -->

    public static String getAccessToken() {
        return spotifyApi.getAccessToken();
    }

        // get current user's profile
    @GetMapping("user-profile")
    public static User getProfile() {
        
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
        .build();

        try {
            final User user = getCurrentUsersProfileRequest.execute();
            return user;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    // get user's top tracks
    @GetMapping("user-top-tracks/{timeRange}")
    public static Track[] getTopTracks(String timeRange) {
        
        final GetUsersTopTracksRequest getUsersTopTracksRequest = spotifyApi.getUsersTopTracks()
            .time_range(timeRange)
            // .limit(10)
            .build();

        try {
            final Paging<Track> trackPaging = getUsersTopTracksRequest.execute();

            // top tracks returned as JSON
            return trackPaging.getItems();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new Track[0];
    }

    // get user's top artists
    @GetMapping("user-top-artists/{timeRange}")
    public static Artist[] getTopArtists(@PathVariable String timeRange) {

        final GetUsersTopArtistsRequest getUsersTopArtistsRequest = spotifyApi.getUsersTopArtists()
            .time_range(timeRange)
            // .limit(10)
            .build();

        try {
            final Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

            // top artists returned as JSON 
            return artistPaging.getItems();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new Artist[0];
    }

    // get user's playlist information
    @GetMapping("user-playlists")
    public static PlaylistSimplified[] getPlaylists() {

        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest 
            = spotifyApi
                .getListOfCurrentUsersPlaylists()
                // .limit(10)
                .build();

        try {
            final Paging<PlaylistSimplified> playlistPaging = getListOfCurrentUsersPlaylistsRequest.execute();

            return playlistPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    
        return null;    
    }

    @GetMapping("currently-playing")
    public static CurrentlyPlaying getCurrentlyPlaying() {
        final GetUsersCurrentlyPlayingTrackRequest getUsersCurrentlyPlayingTrackRequest
            = spotifyApi
                .getUsersCurrentlyPlayingTrack()
                .build();

        try {
            final CurrentlyPlaying currentlyPlaying = getUsersCurrentlyPlayingTrackRequest.execute();
            return currentlyPlaying;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}