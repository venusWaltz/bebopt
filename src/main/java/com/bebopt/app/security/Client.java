package com.bebopt.app.security;

/**
 * The {@code Client} class is a utility class for storing Spotify client credentials.
 */
public class Client {
    private static String clientID = "4d988bad11c14e7c844bd1b166b8a8ad";
    private static String clientSecret = "8f4b649440a14969996d221c06e25007";

    /**
     * Retrieves the client ID used for Spotify API authentication.
     * 
     * @return The Spotify client ID.
     */
    public static String getClientID() {
        return clientID;
    }

    /**
     * Retrieves the client secret used for Spotify API authentication.
     * 
     * @return The Spotify client secret.
     */
    public static String getClientSecret() {
        return clientSecret;
    }
}