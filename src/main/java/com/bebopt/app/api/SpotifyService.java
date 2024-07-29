package com.bebopt.app.api;

import org.springframework.stereotype.Service;

import com.bebopt.app.objects.TimeRange;

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

/**
 * The {@code SpotifyService} class acts as a bridge between the application and the Spotify API endpoints.
 * This class provides methods to interact with various Spotify resources.
 */
@Service
public class SpotifyService {
    
// ---------------------------------------- User ----------------------------------------

    /**
     * Retrieves the URI for the Spotify login.
     * 
     * @return The Spotify login URI.
     */
    public static String getUri() {
        return SpotifyApiClient.getSpotifyLoginUri();
    }

    /**
     * Retrieves the profile information of the current authenticated user.
     * 
     * @return The user profile.
     */
    public static User getCurrentUser() {
        return SpotifyApiClient.getUserProfile();
    }

// -------------------------------------------- Tracks --------------------------------------------

    /**
     * Retrieves a track by its ID.
     * 
     * @param id The Spotify ID number of the track.
     * @return The {@code Track} object corresponding to the given ID.
     */
    public static Track getTrackById(String id) {
        return SpotifyApiClient.getTrackById(id);
    }

    /**
     * Retrieves the top tracks of the authenticated user for a given time range.
     * 
     * @param timeRange The time range for which to fetch top tracks for (short, medium, long).
     * @return The array of top tracks.
     */
    public static Track[] getTopTracks(TimeRange timeRange) {
        return SpotifyApiClient.getTopTracks(timeRange);
    }

    /**
     * Retrieves several tracks based on their IDs.
     * 
     * @param ids A comma-separated list of IDs.
     * @return The array of tracks corresponding to the given IDs.
     */
    public static Track[] getSeveralTracks(String ids) {
        return SpotifyApiClient.getSeveralTracksRequest(ids);
    }
    
    /**
     * Retrieves audio features of multiple tracks based on their IDs.
     * 
     * @param tracks Comma-separated list of IDs.
     * @return The array of audio features objects corresponding to the given track IDs.
     */
    public static AudioFeatures[] getAudioFeatures(String tracks) {
        return SpotifyApiClient.getAudioFeatures(tracks);
    }

    /**
     * Retrieves track recommendations based on seed parameters.
     * 
     * @param seed Comma-separated list of track IDs, artist IDs, or genre seeds.
     * @return Recommendations object containing track recommendations.
     */
    public static Recommendations getRecommendations(String seed) {
        return SpotifyApiClient.getRecommendations(seed);
    }

    /**
     * Retrieves the recently played tracks of the authenticated user.
     * 
     * @return The paging object containing the recently played tracks.
     */
    public static PagingCursorbased<PlayHistory> getRecentlyPlayedTracks() {
        return SpotifyApiClient.getRecentlyPlayedTracks();
    }

    /**
     * Retrieves the currently playing track of the authenticated user.
     * 
     * @return Information about the currently playing track.
     */
    public static CurrentlyPlaying getCurrentlyPlaying() {
        return SpotifyApiClient.getCurrentlyPlaying();
    }

    /**
     * Retrieves the top 5 track IDs that the user has listened to.
     * 
     * @return Comma-separated string of top 5 track IDs.
     */
    public static String getTopNTrackIds(int n) {
        Track[] top = SpotifyApiClient.getTopTracks(TimeRange.SHORT_TERM);
        String id[] = new String[n];
        for(int i = 0; i < id.length; i++) { id[i] = top[i].getId(); }
        return String.join(",", id);
    }

// -------------------------------------------- Albums --------------------------------------------
    
        /**
         * Retrieves an album by its ID.
         * 
         * @param id The ID of the album.
         * @return The album object corresponding to the given ID.
         */
        public static Album getAlbumById(String id) {
            return SpotifyApiClient.getAlbumById(id);
        }

// ------------------------------------------- Artists --------------------------------------------

    /**
     * Retrieves an artist by its ID.
     * 
     * @param id The Spotify ID number of the artist.
     * @return The {@code Artist} object corresponding to the given ID.
     */
    public static Artist getArtistById(String id) {
        return SpotifyApiClient.getArtistById(id);
    }

    /**
     * Retrieves the ID of the top artist of the authenticated user for the short term.
     * 
     * @return The ID of the top artist.
     */
    public static String getTopArtistId() {
        return SpotifyApiClient.getTopArtists(TimeRange.SHORT_TERM)[0].getId();
    }

    /**
     * Retrieves the top artists of the authenticated user for a given time range.
     * 
     * @param timeRange The time range for which to fetch top artists (short, medium, long).
     * @return The array of top artists.
     */
    public static Artist[] getTopArtists(TimeRange timeRange) {
        return SpotifyApiClient.getTopArtists(timeRange);
    }

    /**
     * Retrieves related artists based on a seed artist.
     * 
     * @param seed The ID of the seed artist.
     * @return An array of related artists.
     */
    public static Artist[] getRelatedArtists(String seed) {
        return SpotifyApiClient.getRelatedArtists(seed);
    }

// ------------------------------------------ Playlists -------------------------------------------

    /**
     * Retrieves a full playlist object by its ID.
     * 
     * @param id The ID of the playlist.
     * @return The full playlist object corresponding to the given ID.
     */
    public static Playlist getPlaylistById(String id) {
        return SpotifyApiClient.getPlaylistById(id);
    }

    /**
     * Retrieves simplified information about the authenticated user's playlists.
     * 
     * @return The array of simplified playlist objects.
     */
    public static PlaylistSimplified[] getPlaylists() {
        return SpotifyApiClient.getPlaylists();
    }

    /**
     * Creates a new playlist for the authenticated user.
     * 
     * @return The created playlist object.
     */
    public static Playlist createPlaylist(String name) {
        return SpotifyApiClient.createPlaylist(name);
    }

    /**
     * Adds tracks to a playlist identified by its ID.
     * 
     * @param id The ID of the playlist.
     * @param uris Array of URIs of tracks to add.
     * @return Snapshot result indicating the state of the playlist after modification.
     */
    public static SnapshotResult addToPlaylist(String id, String[] uris) {
        return SpotifyApiClient.addToPlaylist(id, uris);
    }

    /**
     * Modifies a playlist identified by its ID.
     * 
     * @param id The ID of the playlist.
     * @return Snapshot result indicating the state of the playlist after modification.
     */
    public static SnapshotResult modifyPlaylist(String id) {
        return SpotifyApiClient.modifyPlaylist(id);
    }
}
