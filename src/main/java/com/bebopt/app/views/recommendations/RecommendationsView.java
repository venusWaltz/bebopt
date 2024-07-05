package com.bebopt.app.views.recommendations;

import com.bebopt.app.data.spotify.SpotifyService;
import com.bebopt.app.views.MainLayout;
import com.bebopt.app.views.stats.ArtistCard;
import com.bebopt.app.views.stats.TrackCard;
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
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import com.vaadin.flow.theme.lumo.LumoUtility.Grid.Column;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;


import se.michaelthelin.spotify.model_objects.specification.Track;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * The {@code RecommendationsView} class displays recommendations for tracks and related artists.
 */
@AnonymousAllowed
@PageTitle("Recommendations")
@Route(value = "Recommendations", layout = MainLayout.class)
public class RecommendationsView extends Div {
    
    private Div recommendedTracksTab;
    private Div recommendedArtistsTab;
    private TabSheet tabsheet;

    String trackSeed;
    String artistSeed;
    Recommendations recommended;
    OrderedList tracksContainer;
    OrderedList artistsContainer;
    Artist[] relatedArtist;

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
        trackSeed = SpotifyService.getTop5TrackIds();
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
    }

    /**
     * Create the tab to display recommended tracks.
     * 
     * @return The "Recommendations" tab.
     */
    private Div createTracksTab() {
        Div tab = createTab("Recommendations", "recommended-view");
        tab.add(tracksContainer);
        return tab;
    }

    /**
     * Create the tab to display recommended related artists.
     * 
     * @return The "Related Artists" tab.
     */
    private Div createArtistsTab() {
        Div tab = createTab("Related Artists", "artist-view");
        artistsContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE, JustifyContent.BETWEEN, Column.COLUMNS_5);
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
    private Div createTab(String headerText, String viewClassName) {
        Div tab = new Div();
        addClassNames(viewClassName, MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        
        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2(headerText);
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        headerContainer.add(header);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
        
        container.add(headerContainer);
        tab.add(container);

        return tab;
    }
}