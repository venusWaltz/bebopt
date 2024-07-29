package com.bebopt.app.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bebopt.app.objects.PlaylistCard;

import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

/**
 * The {@code PlaylistActions} class provides methods to perform various actions on playlists.
 */
public class PlaylistManager {

    // ------------------------------------------- Sort -------------------------------------------

    /**
     * Sorts the tracks in a playlist based on the specified option.
     * 
     * @param playlistId The ID of the playlist to be sorted.
     * @param option     The sorting criterion (e.g., "Release Date" or "Popularity").
     */
    public static void sortPlaylist(PlaylistCard playlistCard, String option) {
        List<Track> sortedTracks = new ArrayList<>();

        if (option.equals("Release date") || option.equals("Popularity")) {
            sortedTracks = getPlaylistTracks(playlistCard.getSpotifyId());
            Comparator<Track> comparator = "Release date".equals(option) ?
                Comparator.comparingInt(track -> getReleaseDate(track)) :
                Comparator.comparingInt(Track::getPopularity).reversed();
            sortedTracks.sort(comparator);
        } else { sortedTracks = sortAudioFeatures(getAudioFeatures(playlistCard.getSpotifyId()), option); }

        addToNewPlaylist(tracksToUriStr(sortedTracks), playlistCard.getName() + " - " + option);
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
    private static List<Track> sortAudioFeatures(AudioFeatures[] audioFeatures, String selectedSort) {
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
    private static SortBy getFunction(String option) {
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
     * Filters the tracks in a playlist by release decade.
     * 
     * @param playlistCard The {@code PlaylistCard} for the playlist to be filtered.
     * @param option The specific option for the filter criterion.
     */
    public static void filterPlaylist(PlaylistCard playlistCard, Integer option) {
        if (option == null) { System.out.println("No decade selected"); }
        else if (playlistCard.isDecadeMapNull() == true) { System.out.println("Decade map not found"); }
        else {
            List<Track> filteredTracks = playlistCard.getDecadeMap().get(option);
            addToNewPlaylist(tracksToUriStr(filteredTracks), playlistCard.getName() + " - " + option + "s"); 
            printFilteredTracks(filteredTracks, "release decade", String.valueOf(option));
        }
    }

    /**
     * Filters the tracks in a playlist by genre.
     * 
     * @param playlistCard The {@code PlaylistCard} for the playlist to be filtered.
     * @param option The specific option for the filter criterion.
     */
    public static void filterPlaylist(PlaylistCard playlistCard, String option) {
        if (option == null)   { System.out.println("No genre selected"); }
        else if (playlistCard.isGenreMapNull() == true) { System.out.println("Genre map not found"); }
        else {
            List<Track> filteredTracks = playlistCard.getGenreMap().get(option);
            addToNewPlaylist(tracksToUriStr(filteredTracks), playlistCard.getName() + " - " + option); 
            printFilteredTracks(filteredTracks, "genre", option);
        }
    }

    /**
     * Creates a map of playlist tracks grouped by release decade.
     * 
     * @param playlistCard The {@code PlaylistCard} object for the playlist to be filtered.
     */
    public static void createDecadeMap(PlaylistCard playlistCard) {
        List<Track> tracks = getPlaylistTracks(playlistCard.getSpotifyId());
        Map<Integer, List<Track>> map = createDecadeMap(tracks);
        List<String> keysList = map.keySet().stream().map(key -> key + "s").collect(Collectors.toList());
        playlistCard.setDecadeMap(map);
        playlistCard.setDecades(keysList);
    }

    /**
     * Creates a map of playlist tracks grouped by genre.
     * 
     * @param playlistCard The {@code PlaylistCard} object for the playlist to be filtered.
     */
    public static void createGenreMap(PlaylistCard playlistCard) {
        List<Track> tracks = getPlaylistTracks(playlistCard.getSpotifyId());
        Map<String, List<Track>> map = createGenreMap(tracks);
        List<String> keysList = map.keySet().stream().collect(Collectors.toList());
        playlistCard.setGenreMap(map);
        playlistCard.setGenres(keysList);
    }
    
    /**
     * Creates a map of tracks from each decade.
     * 
     * @param tracks The list of tracks to be grouped by decade.
     * @return A map with decades as keys and lists of tracks as values.
     */
    private static Map<Integer, List<Track>> createDecadeMap(List<Track> tracks) {
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
        printMap(decadeMap);
        return decadeMap;
    }

    /**
     * Creates a map of tracks from each genre.
     * 
     * @param tracks The list of tracks to be grouped by genre.
     * @return A map with genres as keys and lists of tracks as values.
     */
    private static Map<String, List<Track>> createGenreMap(List<Track> tracks) {
        Map<String, List<Track>> genreMap = new HashMap<>();
        for (Track track : tracks) {
            String[] genres = getGenres(track);
            for (String genre : genres) { genreMap.computeIfAbsent(genre, k -> new ArrayList<>()).add(track); }
        }
        printMap(genreMap);     
        return genreMap;
    }

    /**
     * Print key-value pairs in a map.
     * 
     * @param map The map to print.
     */
    private static void printMap(Map<?, List<Track>> map) {
        map.forEach((key, value) -> {
            System.out.println(key + ":");
            value.forEach(track -> System.out.println("    " + track.getName()));
        });   
    }

    /**
     * Prints out the filtered tracks.
     * 
     * @param filteredTracks The filtered tracks to be printed.
     * @param filterBy The filtering criterion (e.g., "Release Decade", "Genre").
     * @param option The specific option for the filter criterion.
     */
    private static void printFilteredTracks(List<Track> filteredTracks, String filterBy, String option) {
        if (filteredTracks != null) {
            System.out.println("\nFiltered by " + filterBy +
                    (filterBy.equals("Release decade") ? " (" + option + "s) - " : " - ")
                    + filteredTracks.size() + " song(s) found:");
            for (Track track : filteredTracks) { System.out.println(track.getName()); }
        } else { System.out.println("No songs found"); }
    }

    // ------------------------------------------ Merge -------------------------------------------

    /**
     * Merges two playlists into a new playlist.
     * 
     * @param firstPlaylistId  The ID of the first playlist.
     * @param secondPlaylistId The ID of the second playlist.
     */
    public static void mergePlaylists(PlaylistCard firstPlaylistCard, String secondPlaylistId) { 
        List<Track> playlistTracks1 = getPlaylistTracks(firstPlaylistCard.getSpotifyId());
        List<Track> playlistTracks2 = getPlaylistTracks(secondPlaylistId);
        String[] uris = new String[playlistTracks1.size() + playlistTracks2.size()];
        int i = 0;

        for (Track track : playlistTracks1) { uris[i++] = track.getUri(); }
        for (Track track : playlistTracks2) { uris[i++] = track.getUri(); }

        String playlistName1 = firstPlaylistCard.getName();
        String playlistName2 = SpotifyService.getPlaylistById(secondPlaylistId).getName();

        addToNewPlaylist(uris, playlistName1 + " + " + playlistName2);
        System.out.println("\nMerging " + playlistName1 + " and " + playlistName2);
    }

    // ------------------------------------- Helper Functions -------------------------------------

    /**
     * Retrieves the audio features for the tracks in a playlist.
     * 
     * @param playlistId The ID of the playlist.
     * @return An array of {@code AudioFeatures} objects.
     */
    private static AudioFeatures[] getAudioFeatures(String playlistId) {
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
    private static List<Track> getPlaylistTracks(String playlistId) {
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
    private static Integer getReleaseDate(Track track) {
        return Integer.valueOf(track.getAlbum().getReleaseDate().substring(0, 4));
    }

    /**
     * Retrieves the genre of a track.
     * * Gets track genres from artist instead of album as most Spotify albums have no genre data.
     * 
     * @param track The {@code Track} object.
     * @return The genre of the track.
     */
    private static String[] getGenres(Track track) {
        return SpotifyService.getArtistById(track.getArtists()[0].getId()).getGenres();
    }

    /**
     * Adds tracks to a new playlist.
     *
     * @param uris The array of track URIs to be added.
     */
    private static void addToNewPlaylist(String[] uris, String name) {
        System.out.println("\nNew playlist name: " + name);
        // String id = SpotifyService.createPlaylist(name).getId();
        // SpotifyService.addToPlaylist(id, uris);
    }

    /**
     * Converts a list of tracks to an array of URIs.
     *
     * @param tracks The list of {@code Track} objects.
     * @return An array of track URIs.
     */
    private static String[] tracksToUriStr(List<Track> tracks) {
        String[] uris = new String[tracks.size()];
        int i = 0;
        for (Track track : tracks) uris[i++] = track.getUri();
        return uris;
    }
}
