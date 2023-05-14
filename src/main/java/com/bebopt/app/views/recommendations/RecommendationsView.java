package com.bebopt.app.views.recommendations;

import com.bebopt.app.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import com.vaadin.flow.component.html.OrderedList;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import com.bebopt.app.data.controller.SpotifyService;
//import se.michaelthelin.spotify.model_objects.specification.RecommendationsSeed;

import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;

@PageTitle("Recommendations")
@Route(value = "Recommendations", layout = MainLayout.class)
@PermitAll
public class RecommendationsView extends VerticalLayout{
    //Recommendations recommended = SpotifyService.getRec();
    //Track[] rlist = 
    OrderedList t = RecommendedTracks();
    

    private OrderedList RecommendedTracks() {
        // get user's top tracks
        Recommendations recommended = SpotifyService.getRecommendations();
        //int i = 0;
        OrderedList trackContainer = new OrderedList();
        for(int i = 0; i < recommended.getTracks().length; i++){
            trackContainer.add(new RecommendationsCard(recommended.getTracks()[i], i));
        }
        /*for(TrackSimplified track : recommended.getTracks()) {
            System.out.println(track.getName());
            System.out.println(track.getArtists());
            System.out.println(track.getExternalUrls());
            //System.out.println(track.getPreviewUrl());//not all have preview
            System.out.println(track.getUri());
            System.out.println(track.getUri());
        }*/
        //for(TrackSimplified track : recommended.getTracks()) {
            //trackContainer.add(new RecommendationsCard(track, i++));
        //}
        return trackContainer;
    }
    
}