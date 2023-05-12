package com.bebopt.app.data.controller;

import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;


@Service
public class SpotifyService {

    public static User getCurrentUser() {
        User user = AuthController.getProfile();
        return user;
    }

    public static Track[] getTracks(String timeRange) {
        Track[] tracks = AuthController.getTopTracks(timeRange);
        return tracks;
    }

    public static Artist[] getArtists(String timeRange) {
        Artist[] artists = AuthController.getTopArtists(timeRange);
        return artists;
    }

    public static PlaylistSimplified[] getPlaylists() {
        PlaylistSimplified[] playlists = AuthController.getPlaylists();
        return playlists;
    }

    public static CurrentlyPlaying getCurrentlyPlayingItem() {
        CurrentlyPlaying currentlyPlaying = AuthController.getCurrentlyPlaying();
        return currentlyPlaying;
    }

           // public static void setCurrentUser() {
    //     new CurrentUser(SpotifyService.getCurrentUser());        // get current user info
    // }

}

