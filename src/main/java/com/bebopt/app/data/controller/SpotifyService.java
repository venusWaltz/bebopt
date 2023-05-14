package com.bebopt.app.data.controller;

import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
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

    public static Playlist getPlaylistById(String id) {
        Playlist playlist = AuthController.getPlaylistById(id);
        return playlist;
    }

    public static CurrentlyPlaying getCurrentlyPlayingItem() {
        CurrentlyPlaying currentlyPlaying = AuthController.getCurrentlyPlaying();
        return currentlyPlaying;
    }

    public static Recommendations getRecommendations() {
        Recommendations recommendations = AuthController.getRecommendations();
        return recommendations;
    }

    public static PagingCursorbased<PlayHistory> getRecentlyPlayedTracks() {
        PagingCursorbased<PlayHistory> playHistory = AuthController.getRecentlyPlayedTracks();
        return playHistory;
    }

}

