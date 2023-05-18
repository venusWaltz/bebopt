package com.bebopt.app.data.spotify;

import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.model_objects.miscellaneous.CurrentlyPlaying;
import se.michaelthelin.spotify.model_objects.special.SnapshotResult;
import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.PagingCursorbased;
import se.michaelthelin.spotify.model_objects.specification.PlayHistory;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.User;


@Service
public class SpotifyService {
    
// ---------------------------------------- User ----------------------------------------

    public static User getCurrentUser() {
        return AuthController.getUserProfile();
    }

// ---------------------------------------- Tracks ----------------------------------------

    public static Track[] getTopTracks(String timeRange) {
        return AuthController.getTopTracks(timeRange);
    }
    public static Track getTrackById(String id) {
        return AuthController.getTrackById(id);
    }
    public static PagingCursorbased<PlayHistory> getRecentlyPlayedTracks() {
        return AuthController.getRecentlyPlayedTracks();
    }
    public static CurrentlyPlaying getCurrentlyPlaying() {
        return AuthController.getCurrentlyPlaying();
    }

    public static Track[] getSeveralTracks(String ids) {//used for recommendations but makes more sense to me up here
        Track[] rtracks = AuthController.getSeveralTracksRequest(ids);
        return rtracks;
    }

    public static String getTop5TrackIds() {//gets top 5 tracks user has listened to used for recommendations but makes more sense to me up here
        Track[] top5 = AuthController.getTopTracks("short_term");
        String id[] = new String[5];
        for(int i = 0; i < id.length; i++){
            id[i] = top5[i].getId();
        }
        String ids = String.join(",", id);
        return ids;
    }

// ---------------------------------------- Artists ----------------------------------------

    public static Artist[] getTopArtists(String timeRange) {
        return AuthController.getTopArtists(timeRange);
    }

    public static String getTopArtistId() {
        String id = AuthController.getTopArtists("short_term")[0].getId();
        return id;
    }

// ---------------------------------------- Albums ----------------------------------------

    public static Album getAlbumById(String id) {
        return AuthController.getAlbumById(id);
    }

// ---------------------------------------- Playlists ----------------------------------------

    public static PlaylistSimplified[] getPlaylists() {
        return AuthController.getPlaylists();
    }
    public static Playlist getPlaylistById(String id) {
        return AuthController.getPlaylistById(id);
    }
    public static AudioFeatures[] getAudioFeatures(String tracks) {
        return AuthController.getAudioFeatures(tracks);
    }

// ---------------------------------------- Playlist management ----------------------------------------

    public static Playlist createPlaylist() {
        return AuthController.createPlaylist();
    }
    public static SnapshotResult addToPlaylist(String id, String[] uris) {
        return AuthController.addToPlaylist(id, uris);
    }
    public static SnapshotResult modifyPlaylist(String id) {
        return AuthController.modifyPlaylist(id);
    }

// ---------------------------------------- Recommendations ----------------------------------------

    public static Recommendations getRecommendations(String seed) {
        return AuthController.getRecommendations(seed);
    }

    public static Artist[] getRelatedArtists(String seed) {
        Artist[] rartists = AuthController.getRelatedArtists(seed);
        return rartists;
    }
}
