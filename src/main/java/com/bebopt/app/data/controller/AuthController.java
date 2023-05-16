package com.bebopt.app.data.controller;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.core5.http.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bebopt.app.data.entity.Client;
import com.bebopt.app.security.AuthenticatedUser;

import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.albums.GetAlbumRequest;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import se.michaelthelin.spotify.requests.data.player.GetUsersCurrentlyPlayingTrackRequest;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.ReorderPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForSeveralTracksRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetTrackRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

@RestController
@RequestMapping("")
public class AuthController {
    private static String clientID = Client.getClientID();
    private static String clientSecret = Client.getClientSecret();
    private static String scopes = "user-read-private,playlist-read-private,user-top-read,user-read-currently-playing,user-read-recently-played,playlist-modify-private,playlist-modify-public";
    private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");
    private String code = "";
    private static User user;

    public static User getUser() {
        return user;
    }

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

        user = getProfile();
        AuthenticatedUser.setIsLoggedIn(true); 

        response.sendRedirect("http://localhost:8080/home");    // redirect to home page after retrieving access token

        return spotifyApi.getAccessToken();
    }   // handle errors

    // refresh access token when it expires (fix later)
    public static void refeshAccessToken() {
        try {
            AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .refresh_token(spotifyApi.getRefreshToken())
                .build();
                AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();
                spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
                spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // logout
    @GetMapping("logout")
    public static void logout(HttpServletResponse response) throws IOException {
        try {
            spotifyApi.authorizationCodeRefresh(clientID, clientSecret, spotifyApi.getRefreshToken()).build().execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        spotifyApi.setAccessToken(null);
        spotifyApi.setRefreshToken(null);
        AuthenticatedUser.setIsLoggedIn(false);

        response.sendRedirect("/home");
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

    // get user's playlists
    @GetMapping("user-playlists")
    public static PlaylistSimplified[] getPlaylists() {

        final GetListOfCurrentUsersPlaylistsRequest getListOfCurrentUsersPlaylistsRequest 
            = spotifyApi
                .getListOfCurrentUsersPlaylists()
                .limit(10)
                .build();

        try {
            final Paging<PlaylistSimplified> playlistPaging = getListOfCurrentUsersPlaylistsRequest.execute();

            return playlistPaging.getItems();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    
        return null;    
    }

    // get a playlist by its id
    @GetMapping("get-playlist-by-id/{id}")
    public static Playlist getPlaylistById(String id) {
        final GetPlaylistRequest getPlaylistRequest = spotifyApi
            .getPlaylist(id)
            .build();

        try {
            final Playlist playlist = getPlaylistRequest.execute();
            return playlist;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    // get track by id
    @GetMapping("get-track-by-id/{id}")
    public static Track getTrackById(String id) {
        final GetTrackRequest getTrackRequest = spotifyApi
            .getTrack(id)
            .build();

        try {
            final Track track = getTrackRequest.execute();
            return track;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return null;
    }

    // get user's currently playing track
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

    // get recommendations from a track
    @GetMapping("recommendations")
    public static Recommendations getRecommendations(String seed) {
        final GetRecommendationsRequest getRecommendationsRequest = spotifyApi
            .getRecommendations()
            .limit(10)
            .seed_artists(seed)
            .build();
        
        try {
            final Recommendations recommendations = getRecommendationsRequest.execute();
            return recommendations;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    // get user's recently played items
    @GetMapping("recently-played")
    public static PagingCursorbased<PlayHistory> getRecentlyPlayedTracks() {
        final GetCurrentUsersRecentlyPlayedTracksRequest getCurrentUsersRecentlyPlayedTracksRequest
            = spotifyApi
                .getCurrentUsersRecentlyPlayedTracks()
                .limit(20)
                .build();

        try {
            final PagingCursorbased<PlayHistory> recentlyPlayedTracks = getCurrentUsersRecentlyPlayedTracksRequest.execute();
            return recentlyPlayedTracks;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @GetMapping("audio-features")
    public static AudioFeatures[] getAudioFeatures(String tracks) {
        final GetAudioFeaturesForSeveralTracksRequest getAudioFeaturesForSeveralTracksRequest
            = spotifyApi
                .getAudioFeaturesForSeveralTracks(tracks)
                .build();

        try {
            final AudioFeatures[] audioFeatures = getAudioFeaturesForSeveralTracksRequest.execute();
            return audioFeatures;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @GetMapping("get-album")
    public static Album getAlbumById(String id) {
        final GetAlbumRequest getAlbumRequest = spotifyApi.getAlbum(id).build();

        try {
            final Album album = getAlbumRequest.execute();
            return album;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @PostMapping("create-playlist") 
    public static Playlist createPlaylist() {
        final CreatePlaylistRequest createPlaylistRequest = spotifyApi
            .createPlaylist(user.getId(), "name")
            .build();

        try {
            final Playlist newPlaylist = createPlaylistRequest.execute();
            return newPlaylist;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @PostMapping("add-items-to-playlist")
    public static SnapshotResult addToPlaylist(String id, String[] uris) {
        final AddItemsToPlaylistRequest addItemsToPlaylistRequest = spotifyApi
            .addItemsToPlaylist(id, uris) // max length 50 for String[] uris, use JsonArray to add >50 tracks
            .build();

        try {
            final SnapshotResult snapshotResult = addItemsToPlaylistRequest.execute();
            return snapshotResult;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    @PutMapping("update-playlist-items")
    public static SnapshotResult modifyPlaylist(String id) {
        final ReorderPlaylistsItemsRequest reorderPlaylistsItemsRequest 
            = spotifyApi
                .reorderPlaylistsItems(id, 0, 0)
                .build();
        
        try {
            final SnapshotResult snapshotResult = reorderPlaylistsItemsRequest.execute();
            return snapshotResult;
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return null;
    }
}