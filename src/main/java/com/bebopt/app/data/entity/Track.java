package com.bebopt.app.data.entity;

import com.vaadin.flow.component.html.ListItem;

public class Track extends ListItem {

    private String track;
    private String artist;
    // track has artists
    // private Artist artist;
    private String image;

    public Track(String trackName, String artistName, String image) {
        this.track = trackName;
        this.artist = artistName;
        this.image = image;
    }
    public String getTrack() {
        return track;
    }
    public void setTrackName(String trackName) {
        this.track = trackName;
    }
    public String getArtist() {
        return artist;
    }
    public void setArtist(String artistName) {
        this.artist = artistName;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
