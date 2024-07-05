package com.bebopt.app.views.playlists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bebopt.app.data.spotify.SpotifyService;

import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

/**
 * The {@code PlaylistActions} class provides methods to perform various actions on playlists.
 */
public class PlaylistActions {

    static Map<Integer, List<Track>> decadeMap;

    // ------------------------------------------- Sort -------------------------------------------

    /**
     * Sorts the tracks in a playlist based on the specified option.
     * 
     * @param playlistId The ID of the playlist to be sorted.
     * @param option     The sorting criterion (e.g., "Release Date" or "Popularity").
     */
    public static void sortPlaylist(String playlistId, String option) {
        List<Track> sortedTracks = new ArrayList<>();

        if (option.equals("Release date") || option.equals("Popularity")) {
            sortedTracks = getPlaylistTracks(playlistId);
            Comparator<Track> comparator = "Release date".equals(option) ?
                Comparator.comparingInt(track -> getReleaseDate(track)) :
                Comparator.comparingInt(Track::getPopularity).reversed();
            sortedTracks.sort(comparator);
        } else { sortedTracks = sortAudioFeatures(getAudioFeatures(playlistId), option); }

        addToNewPlaylist(tracksToUriStr(sortedTracks));
        System.out.println("\nSorted by " + option);
        for (Track track : sortedTracks) { System.out.println(track.getName()); }
    }

    /**
     * Sorts and array of audio features based on the selected option.
     * 
     * @param audioFeatures The array of {@code AudioFeatures} to be sorted.
     * @param selectedSort  The sorting criterion.
     * @return A list of sorted {@code Track} objects.
     */
    public static List<Track> sortAudioFeatures(AudioFeatures[] audioFeatures, String selectedSort) {
        SortBy sortByFunction = getFunction(selectedSort);
        Arrays.sort(audioFeatures, Comparator.comparing(sortByFunction::getValue));
        return getTracksFromAudioFeatures(audioFeatures);
    }

    /**
     * Converts an array of sorted audio features into a list of tracks.
     * 
     * @param sortedFeatures The array of sorted {@code AudioFeatures}.
     * @return A list of {@code Track} objects.
     */
    private static List<Track> getTracksFromAudioFeatures(AudioFeatures[] sortedFeatures) {
        List<Track> tracks = new ArrayList<>();
        for (AudioFeatures feature : sortedFeatures) {
            tracks.add(SpotifyService.getTrackById(feature.getId()));
        }
        return tracks;
    }

    /**
     * Functional interface for getting a value from an {@code AudioFeatures} object.
     */
    interface SortBy { Float getValue(AudioFeatures audioFeatures); }

    /**
     * Returns a {@code SortBy} function based on the selected option.
     * 
     * @param option The sorting criterion.
     * @return A {@code SortBy} function.
     */
    public static SortBy getFunction(String option) {
        switch (option) {
            case "Duration": return feature -> (float) feature.getDurationMs();
            case "Acousticness": return AudioFeatures::getAcousticness;
            case "Danceability": return AudioFeatures::getDanceability;
            case "Energy": return AudioFeatures::getEnergy;
            case "Instrumentalness": return AudioFeatures::getInstrumentalness;
            case "Loudness": return AudioFeatures::getLoudness;
            case "Speechiness": return AudioFeatures::getSpeechiness;
            case "Tempo": return AudioFeatures::getTempo;
            case "Valence": return AudioFeatures::getValence;
            default: throw new IllegalArgumentException("Invalid parameter: " + option);
        }
    }

    // ------------------------------------------ Filter ------------------------------------------

    /**
     * Filters the tracks in a playlist based on the specified criterion.
     * 
     * @param playlistId The ID of the playlist to be filtered.
     * @param filterBy   The filtering criterion (e.g., "Release Decade").
     * @param option     The specific option for the filter criterion.
     */
    public static void filterPlaylist(String playlistId, String filterBy, Integer option) {
        List<Track> filteredTracks = new ArrayList<>();
        System.out.println(filterBy);

        if ("Release decade".equals(filterBy)) {
            filteredTracks = filterByReleaseDecade(playlistId, option);
        }

        if (filteredTracks != null) {
            addToNewPlaylist(tracksToUriStr(filteredTracks)); 
            System.out.println("\nFiltered by " + filterBy.toLowerCase() +
                    (filterBy.equals("Release decade") ? " (" + option + "s) - " : " - ")
                    + filteredTracks.size() + " song(s) found:");
            for (Track track : filteredTracks) { System.out.println(track.getName()); }
        } else { System.out.println("No songs found"); }
    }

    /**
     * Filters the tracks in a playlist by their release decade.
     * 
     * @param playlistId The ID of the playlist to be filtered.
     * @param option     The specific decade to filter by.
     * @return A list of filtered {@code Track} objects.
     */
    public static List<Track> filterByReleaseDecade(String playlistId, Integer option) {
        if (decadeMap != null && option != null) return decadeMap.get(option);
        else if (decadeMap == null) System.out.println("Decade map not found");
        else if (option == null)    System.out.println("No decade selected");
        return null;
    }

    /**
     * Creates a map of tracks grouped by their release decade.
     * 
     * @param playlistId The ID of the playlist to be filtered.
     * @return A set of integers representing the available decades.
     */
    public static Set<Integer> filterGetKeys(String playlistId) {
        List<Track> tracks = getPlaylistTracks(playlistId);
        decadeMap = createDecadeMap(tracks);
        return decadeMap.keySet();
    }

    /**
     * Creates a map of tracks from each decade.
     * 
     * @param tracks The list of tracks to be grouped by decade.
     * @return A map with decades as keys and lists of tracks as values.
     */
    public static Map<Integer, List<Track>> createDecadeMap(List<Track> tracks) {
        Integer earliest = Integer.MAX_VALUE;
        Integer latest = Integer.MIN_VALUE;
        Map<Integer, List<Track>> decadeMap = new HashMap<>();

        for (Track track : tracks) {
            Integer releaseYear = getReleaseDate(track);

            if (releaseYear < earliest) { earliest = releaseYear; }
            if (releaseYear > latest)   { latest = releaseYear; }

            Integer decade = releaseYear / 10 * 10;
            decadeMap.computeIfAbsent(decade, k -> new ArrayList<>()).add(track);
        }

        return decadeMap;
    }

    // ------------------------------------------ Merge -------------------------------------------

    /**
     * Merges two playlists into a new playlist.
     * 
     * @param firstPlaylistId  The ID of the first playlist.
     * @param secondPlaylistId The ID of the second playlist.
     */
    public static void mergePlaylists(String firstPlaylistId, String secondPlaylistId) { 
        List<Track> playlistTracks1 = getPlaylistTracks(firstPlaylistId);
        List<Track> playlistTracks2 = getPlaylistTracks(secondPlaylistId);
        String[] uris = new String[playlistTracks1.size() + playlistTracks2.size()];
        int i = 0;
        for (Track track : playlistTracks1) { uris[i++] = track.getUri(); }
        for (Track track : playlistTracks2) { uris[i++] = track.getUri(); }

        addToNewPlaylist(uris);
        System.out.println("\nMerging " + SpotifyService.getPlaylistById(firstPlaylistId).getName()
                + " and " + SpotifyService.getPlaylistById(secondPlaylistId).getName());
    }

    // ------------------------------------- Helper Functions -------------------------------------

    /**
     * Retrieves the audio features for the tracks in a playlist.
     * 
     * @param playlistId The ID of the playlist.
     * @return An array of {@code AudioFeatures} objects.
     */
    public static AudioFeatures[] getAudioFeatures(String playlistId) {
        Playlist playlist = SpotifyService.getPlaylistById(playlistId);
        PlaylistTrack[] tracks = playlist.getTracks().getItems();
        String trackIds = "";
        for (PlaylistTrack track : tracks)
            trackIds += (trackIds.isEmpty() ? "" : ",") + track.getTrack().getId();
        return SpotifyService.getAudioFeatures(trackIds);
    }

    /**
     * Retrieves the tracks from a playlist.
     * 
     * @param playlistId The ID of the playlist.
     * @return A list of {@code Track} objects.
     */
    public static List<Track> getPlaylistTracks(String playlistId) {
        Playlist playlist = SpotifyService.getPlaylistById(playlistId);
        PlaylistTrack[] playlistTracks = playlist.getTracks().getItems();
        List<Track> tracks = new ArrayList<Track>();

        for (PlaylistTrack playlistTrack : playlistTracks)
            tracks.add(SpotifyService.getTrackById(playlistTrack.getTrack().getId()));

        return tracks;
    }

    /**
     * Retrieves the release date of a track.
     *
     * @param track The {@code Track} object.
     * @return The release year of the track.
     */
    public static Integer getReleaseDate(Track track) {
        return Integer.valueOf(track.getAlbum().getReleaseDate().substring(0, 4));
    }

    /**
     * Retrieves the track associated with an audio feature.
     *
     * @param audioFeature The {@code AudioFeatures} object.
     * @return The {@code Track} object.
     */
    public static Track getTrack(AudioFeatures audioFeature) {
        return SpotifyService.getTrackById(audioFeature.getId());
    }

    /**
     * Adds tracks to a new playlist.
     *
     * @param uris The array of track URIs to be added.
     */
    public static void addToNewPlaylist(String[] uris) {
        String id = SpotifyService.createPlaylist().getId(); /* Retrieve ID of the new playlist. */
        SpotifyService.addToPlaylist(id, uris);
    }

    /**
     * Converts a list of tracks to an array of URIs.
     *
     * @param tracks The list of {@code Track} objects.
     * @return An array of track URIs.
     */
    public static String[] tracksToUriStr(List<Track> tracks) {
        String[] uris = new String[tracks.size()];
        int i = 0;
        for (Track track : tracks)
            uris[i++] = track.getUri();
        return uris;
    }
}