// package com.bebopt.app.security;

// import se.michaelthelin.spotify.SpotifyApi;
// import se.michaelthelin.spotify.SpotifyHttpManager;
// import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

// import java.net.URI;
// import java.util.concurrent.CancellationException;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.CompletionException;

// import com.bebopt.app.data.entity.Client;

// public class SpotifyAuthUri {
//   private static final String clientId = Client.getClientID();
//   private static final URI redirectUri = SpotifyHttpManager.makeUri("http://localhost:8080/callback");
//   private static final String codeChallenge = "w6iZIj99vHGtEx_NVl9u3sthTN646vvkiP8OMCGfPmo";

//   private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
//     .setClientId(clientId)
//     .setRedirectUri(redirectUri)
//     .build();
//   private static final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodePKCEUri(codeChallenge)
// //          .state("x4xkmn9pu3j6ukrs8n")
// //          .scope("user-read-birthdate,user-read-email")
// //          .show_dialog(true)
//     .build();

//   public static void authorizationCodeUri_Sync() {
//     final URI uri = authorizationCodeUriRequest.execute();

//     System.out.println("URI: " + uri.toString());
//   }

//   public static void authorizationCodeUri_Async() {
//     try {
//       final CompletableFuture<URI> uriFuture = authorizationCodeUriRequest.executeAsync();

//       // Thread free to do other tasks...

//       final URI uri = uriFuture.join();

//       System.out.println("URI: " + uri.toString());
//     } catch (CompletionException e) {
//       System.out.println("Error: " + e.getCause().getMessage());
//     } catch (CancellationException e) {
//       System.out.println("Async operation cancelled.");
//     }
//   }

//   public static void main (String[]args){
//     authorizationCodeUri_Sync();
//     authorizationCodeUri_Async();
//   }
// }


