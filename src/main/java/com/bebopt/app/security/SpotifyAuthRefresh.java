// package com.bebopt.app.security;

// import se.michaelthelin.spotify.SpotifyApi;
// import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
// import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
// import se.michaelthelin.spotify.requests.authorization.authorization_code.pkce.AuthorizationCodePKCERefreshRequest;
// import org.apache.hc.core5.http.ParseException;

// import com.bebopt.app.data.entity.Client;

// import java.io.IOException;
// import java.util.concurrent.CancellationException;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.CompletionException;

// public class SpotifyAuthRefresh {
//   private static final String clientId = Client.getClientID();
//   private static final String refreshToken = "b0KuPuLw77Z0hQhCsK-GTHoEx_kethtn357V7iqwEpCTIsLgqbBC_vQBTGC6M5rINl0FrqHK-D3cbOsMOlfyVKuQPvpyGcLcxAoLOTpYXc28nVwB7iBq2oKj9G9lHkFOUKn";

//   private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
//     .setClientId(clientId)
//     .setRefreshToken(refreshToken)
//     .build();
//   private static final AuthorizationCodePKCERefreshRequest authorizationCodePKCERefreshRequest = spotifyApi.authorizationCodePKCERefresh()
//     .build();

//   public static void authorizationCodeRefresh_Sync() {
//     try {
//       final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodePKCERefreshRequest.execute();

//       // Set access and refresh token for further "spotifyApi" object usage
//       spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
//       spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

//       System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
//     } catch (IOException | SpotifyWebApiException | ParseException e) {
//       System.out.println("Error: " + e.getMessage());
//     }
//   }

//   public static void authorizationCodeRefresh_Async() {
//     try {
//       final CompletableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodePKCERefreshRequest.executeAsync();

//       // Thread free to do other tasks...

//       final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.join();

//       // Set access token for further "spotifyApi" object usage
//       spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

//       System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
//     } catch (CompletionException e) {
//       System.out.println("Error: " + e.getCause().getMessage());
//     } catch (CancellationException e) {
//       System.out.println("Async operation cancelled.");
//     }
//   }

//   public static void main(String[] args) {
//     authorizationCodeRefresh_Sync();
//     authorizationCodeRefresh_Async();
//   }
// }