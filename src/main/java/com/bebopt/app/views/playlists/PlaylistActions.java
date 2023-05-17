package com.bebopt.app.views.playlists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bebopt.app.data.controller.SpotifyService;

import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;

public class PlaylistActions {

    static Map<Integer, List<Track>> decadeMap;

// ---------------------------------------- Sort ----------------------------------------

    public static void sortPlaylist(String playlistId, String option) {
        List<Track> sortedTracks = new ArrayList<>();

        if (option.equals("Release date") || option.equals("Popularity")) {
            sortedTracks = getPlaylistTracks(playlistId);

            Comparator<Track> comparator;
            if (option.equals("Release date")) 
                comparator = Comparator.comparingInt(track -> getReleaseDate(track));
            else
                comparator = Comparator.comparingInt(Track::getPopularity).reversed();
            
            Collections.sort(sortedTracks, comparator);

        } else {
            AudioFeatures[] audioFeatures = getAudioFeatures(playlistId);
            sortedTracks = sortAudioFeatures(audioFeatures, option);
        }

        // addToNewPlaylist(tracksToUriStr(sortedTracks));   // add songs to new playlist

        System.out.println("\nSorted by " + option);
        for (Track track : sortedTracks)
            System.out.println(track.getName());

    }
    
    // sort array of Tracks
    public static List<Track> sortTracks(List<Track> tracks) {
        return null;
    }
    // sort array of AudioFeatures
    public static List<Track> sortAudioFeatures(AudioFeatures[] audioFeatures, String selectedSort) {
        SortBy sortByFunction = getFunction(selectedSort);
        Arrays.sort(audioFeatures, Comparator.comparing(sortByFunction::getValue));
        return getTracksFromAudioFeatures(audioFeatures);
    }

    private static List<Track> getTracksFromAudioFeatures(AudioFeatures[] sortedFeatures) {
        List<Track> tracks = new ArrayList<>();
        for (AudioFeatures feature : sortedFeatures) {
            tracks.add(SpotifyService.getTrackById(feature.getId()));
        }
        return tracks;
    }

    interface SortBy {
        Float getValue(AudioFeatures audioFeatures);
    }

    // create instance of SortBy interface for selected sort type
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

// ---------------------------------------- Filter ----------------------------------------

    public static void filterPlaylist(String playlistId, String filterBy, Integer option) {
        // List<Track> tracks = getPlaylistTracks(playlistId);
        List<Track> filteredTracks = new ArrayList<>();
        System.out.println(filterBy);

        // filter songs in playlist
        switch (filterBy) {
            case "Release decade":
                filteredTracks = filterByReleaseDecade(playlistId, filterBy, option);
                break;
        }

        // if songs are found, add to new playlist
        if (filteredTracks != null) {
            // addToNewPlaylist(tracksToUriStr(filteredTracks));   // add songs to new playlist

            System.out.println("\nFiltered by " + filterBy.toLowerCase() +
                (filterBy.equals("Release decade") ? " (" + option + "s) - " : " - ")  
                + filteredTracks.size() + " song(s) found:");
            for (Track track : filteredTracks) {
                System.out.println(track.getName());
            }
        } else { System.out.println("No songs found"); }
    }

    public static List<Track> filterByReleaseDecade(String playlistId, String filterBy, Integer option) {
        if (decadeMap != null && option != null)
            return decadeMap.get(option);
        else if (decadeMap == null)
            System.out.println("Decade map not found");
        else if (option == null)
            System.out.println("No decade selected");
        return null;
    }

    // create decade map, return keys (to display + allow user to choose from keys)
    public static Set<Integer> filterGetKeys(String playlistId) {
        List<Track> tracks = getPlaylistTracks(playlistId);
        decadeMap = createDecadeMap(tracks);
        return decadeMap.keySet();

    }

    // filter playlist and create map of tracks from each decade
    public static Map<Integer, List<Track>> createDecadeMap(List<Track> tracks) {
        Integer earliest = Integer.MAX_VALUE;
        Integer latest = Integer.MIN_VALUE;
        Map<Integer, List<Track>> decadeMap = new HashMap<>();

        for (Track track : tracks) {
            Integer releaseYear = getReleaseDate(track);

            if (releaseYear < earliest)
                earliest = releaseYear;
            if (releaseYear > latest)
                latest = releaseYear;

            Integer decade = releaseYear / 10 * 10;
            decadeMap.computeIfAbsent(decade, k -> new ArrayList<>()).add(track);
        }

        return decadeMap;
    }

// ---------------------------------------- Merge ----------------------------------------

    public static void mergePlaylists(String firstPlaylistId, String secondPlaylistId) { // + location
        List<Track> playlistTracks1 = getPlaylistTracks(firstPlaylistId);
        List<Track> playlistTracks2 = getPlaylistTracks(secondPlaylistId);
        String[] uris = new String[playlistTracks1.size() + playlistTracks2.size()];
        int i = 0;
        for (Track track : playlistTracks1) uris[i++] = track.getUri();
        for (Track track : playlistTracks2) uris[i++] = track.getUri();

        System.out.println("\nMerging " + SpotifyService.getPlaylistById(firstPlaylistId).getName()
            + " and " + SpotifyService.getPlaylistById(secondPlaylistId).getName());
        addToNewPlaylist(uris);
    }

// ---------------------------------------- Helper functions ----------------------------------------

    public static AudioFeatures[] getAudioFeatures(String playlistId) {
        // get tracks in playlist
        Playlist playlist = SpotifyService.getPlaylistById(playlistId);
        PlaylistTrack[] tracks = playlist.getTracks().getItems();

        // send playlist tracks to getAudioFeatures method as a comma-separated list of track ids
        String trackIds = "";
        for (PlaylistTrack track : tracks) {
            trackIds += (trackIds.isEmpty() ? "" : ",") + track.getTrack().getId();
        }
        AudioFeatures[] audioFeatures = SpotifyService.getAudioFeatures(trackIds);
        return audioFeatures;
    }

    // pull tracks from playlist into Track objects (necessary data is not saved in
    // PlaylistTrack objects)
    public static List<Track> getPlaylistTracks(String playlistId) {
        Playlist playlist = SpotifyService.getPlaylistById(playlistId);
        PlaylistTrack[] playlistTracks = playlist.getTracks().getItems();
        List<Track> tracks = new ArrayList<Track>();

        for (PlaylistTrack playlistTrack : playlistTracks) {
            tracks.add(SpotifyService.getTrackById(playlistTrack.getTrack().getId()));
        }
        return tracks;
    }

    // get track release date
    public static Integer getReleaseDate(Track track) {
        return Integer.valueOf(track.getAlbum().getReleaseDate().substring(0, 4));
    }

    // get track related to audioFeature object
    public static Track getTrack(AudioFeatures audioFeature) {
        return SpotifyService.getTrackById(audioFeature.getId());
    }

    // add songs to new playlist
    public static void addToNewPlaylist(String[] uris) {
        String id = SpotifyService.createPlaylist().getId(); // get id of newly created playlist
        SpotifyService.addToPlaylist(id, uris);
    }

    // return array of track uris
    public static String[] tracksToUriStr(List<Track> tracks) {
        String[] uris = new String[tracks.size()];
        int i = 0;
        for (Track track : tracks) uris[i++] = track.getId();
        return uris;
    }

}
