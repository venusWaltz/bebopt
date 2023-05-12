package com.bebopt.app.data.entity;

import se.michaelthelin.spotify.model_objects.specification.User;

public class SpotifyUser {
    private static String username;
    private static String profileImage;

    public SpotifyUser(User user) {
        SpotifyUser.username = user.getDisplayName();
        SpotifyUser.profileImage = user.getImages()[0].getUrl();
    }
    public static String getUsername() {
        return username;
    }
    public static void setUsername(String username) {
        SpotifyUser.username = username;
    }
    public static String getProfileImage() {
        return profileImage;
    }
    public static void setProfileImage(String profileImage) {
        SpotifyUser.profileImage = profileImage;
    }

}
