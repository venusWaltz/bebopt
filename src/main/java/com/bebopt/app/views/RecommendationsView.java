package com.bebopt.app.views;

import com.bebopt.app.api.SpotifyService;
import com.bebopt.app.objects.ArtistCard;
import com.bebopt.app.objects.TrackCard;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.html.OrderedList;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * The {@code RecommendationsView} class displays recommendations for tracks and related artists.
 */
@AnonymousAllowed
@PageTitle("Recommendations")
@Route(value = "Recommendations", layout = MainLayout.class)
public class RecommendationsView extends Div {
    
    private static final int TOP_TRACK_COUNT = 5;
    private Div recommendedTracksTab;
    private Div recommendedArtistsTab;
    private TabSheet tabsheet;
    
    private String trackSeed;
    private String artistSeed;
    private Recommendations recommended;
    private OrderedList tracksContainer;
    private OrderedList artistsContainer;
    private Artist[] relatedArtist;
    
    /**
     * Constructor for the {@code RecommendationsView} class.
     */
    public RecommendationsView() {
        loadRecommendations();
        createTabSheet();
        add(tabsheet);
    }
    
    /**
     * Load recommendation data.
     */
    private void loadRecommendations() {
        trackSeed = SpotifyService.getTopNTrackIds(TOP_TRACK_COUNT);
        artistSeed = SpotifyService.getTopArtistId();
        recommended = SpotifyService.getRecommendations(trackSeed);
        tracksContainer = getRecommendedTracks();
        relatedArtist = SpotifyService.getRelatedArtists(artistSeed);
        artistsContainer = getRelatedArtists();
    }
    
    /**
     * Retrieves recommended tracks.
     * 
     * @return The OrderedList containing recommended tracks.
     */
    private OrderedList getRecommendedTracks() {
        OrderedList container = new OrderedList();

        String id[] = new String[recommended.getTracks().length];
        for(int i = 0; i < recommended.getTracks().length; i++){
            id[i] = recommended.getTracks()[i].getId();
        }
        Track[] tracks = SpotifyService.getSeveralTracks(String.join(",", id));
        for(int i = 0; i < recommended.getTracks().length; i++){
            container.add(new TrackCard(tracks[i], i));
        }

        return container;
    }

    /**
     * Retrieves recommended related artists.
     * 
     * @return The OrderedList containing recommended related artists.
     */
    private OrderedList getRelatedArtists() {
        OrderedList container = new OrderedList();
        for (Artist artist : relatedArtist) { container.add(new ArtistCard(artist)); }
        return container;
    }

    /**
     * Create the TabSheet and tabs.
     */
    private void createTabSheet() {
        tabsheet = new TabSheet();
        recommendedTracksTab = createTracksTab();
        recommendedArtistsTab = createArtistsTab();
        tabsheet.add("Recommended Tracks", recommendedTracksTab);
        tabsheet.add("Related Artists", recommendedArtistsTab);
        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        addClassNames("page-view", "recommendations-view");
    }

    /**
     * Create the tab to display recommended tracks.
     * 
     * @return The "Recommendations" tab.
     */
    private Div createTracksTab() {
        Div tab = createTab("Recommendations");
        tab.add(tracksContainer);
        return tab;
    }

    /**
     * Create the tab to display recommended related artists.
     * 
     * @return The "Related Artists" tab.
     */
    private Div createArtistsTab() {
        Div tab = createTab("Related Artists");
        tab.addClassNames("artist-view");
        artistsContainer.addClassNames("artists-container");
        tab.add(artistsContainer);
        return tab;
    }

    /**
     * Create a base tab layout.
     * 
     * @param headerText The title used for the header text of the tab.
     * @param viewClassName The class name for the tab.
     * @return The new tab.
     */
    private Div createTab(String headerText) {
        Div tab = new Div();
        HorizontalLayout container = new HorizontalLayout();
        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2(headerText);

        container.addClassNames("header-container");
        headerContainer.add(header);
        container.add(headerContainer);
        tab.add(container);
        return tab;
    }
}