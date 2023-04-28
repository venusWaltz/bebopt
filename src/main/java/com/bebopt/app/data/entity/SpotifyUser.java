package com.bebopt.app.data.entity;

public class SpotifyUser {
    private static String username;
    private static String profileImage;

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
