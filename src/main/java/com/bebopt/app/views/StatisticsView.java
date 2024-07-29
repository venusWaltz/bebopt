package com.bebopt.app.views;

import com.bebopt.app.api.SpotifyService;
import com.bebopt.app.objects.TimeRange;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * The {@code StatsView} class represents the statistics view of the application.
 * It displays the user's top tracks and artists over different time frames.
 */
@AnonymousAllowed
@PageTitle("Statistics")
@Route(value = "statistics", layout = MainLayout.class)
public class StatisticsView extends Div {

    private TabSheet tabsheet;
    private Div trackTab;
    private Div artistTab;

    private OrderedList trackContainerShortTerm;
    private OrderedList trackContainerMediumTerm;
    private OrderedList trackContainerLongTerm;
    private OrderedList artistContainerShortTerm;
    private OrderedList artistContainerMediumTerm;
    private OrderedList artistContainerLongTerm;
    
    private final String[] timePeriods = {"Last four weeks", "Last six months", "All time"};

    /**
     * Constructor for the {@code StatsView} class.
     * Initializes the view components and loads user data from Spotify.
     */
    public StatisticsView() {
        loadStats();
        constructUI();
        add(tabsheet);
    }
    
    /**
     * Loads statistics data.
     */
    private void loadStats() {
        trackContainerShortTerm = SpotifyService.getTopTracks(TimeRange.SHORT_TERM);
        artistContainerShortTerm = SpotifyService.getTopArtists(TimeRange.SHORT_TERM);
        trackContainerMediumTerm = SpotifyService.getTopTracks(TimeRange.MEDIUM_TERM);
        artistContainerMediumTerm = SpotifyService.getTopArtists(TimeRange.MEDIUM_TERM);
        trackContainerLongTerm = SpotifyService.getTopTracks(TimeRange.LONG_TERM);
        artistContainerLongTerm = SpotifyService.getTopArtists(TimeRange.LONG_TERM);
    }
    
    /**
     * Creates the TabSheet and tabs.
     */
    private void constructUI() {
        addClassNames("page-view", "stats-view");
        tabsheet = new TabSheet();
        trackTab = createTopTracks();
        artistTab = createTopArtists();
        tabsheet.add("Top Tracks", trackTab);
        tabsheet.add("Top Artists", artistTab);
        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
    }

// ------------------------------------------- Tabsheet -------------------------------------------

    /**
     * Creates the "Top Tracks" tab.
     * 
     * @return The Div containing the "Top Tracks" tab content.
     */
    private Div createTopTracks() {
        Div div = new Div();

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("header-container");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Tracks");
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setItems(timePeriods);
        sortBy.setValue(timePeriods[0]);

        Div trackDiv = new Div();
        trackDiv.add(trackContainerShortTerm);
        container.add(headerContainer, sortBy);
        div.add(container, trackDiv);

        setChangeListener(sortBy, trackDiv, trackContainerShortTerm, trackContainerMediumTerm, trackContainerLongTerm);
        return div;
    }

    /**
     * Creates the "Top Artists" tab.
     * 
     * @return The Div containing the "Top Artists" tab content.
     */
    private Div createTopArtists() {
        Div div = new Div();
        div.addClassNames("artist-view");
        
        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("header-container");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Top Artists");
        headerContainer.add(header);

        Select<String> sortBy = new Select<>();
        sortBy.setItems(timePeriods);
        sortBy.setValue(timePeriods[0]);

        Div artistDiv = new Div();
        artistDiv.add(artistContainerShortTerm);
        container.add(headerContainer, sortBy);
        div.add(container, artistDiv);

        setChangeListener(sortBy, artistDiv, artistContainerShortTerm, artistContainerMediumTerm, artistContainerLongTerm);
        return div;
    }

    /**
     * Sets a value change listener on the given Select component to update the content
     * of the given Div based on the selected time frame.
     * 
     * @param sortBy The Select component to set the listener on.
     * @param contentDiv The Div to update based on the selected time frame.
     * @param shortTerm The container for short term data.
     * @param mediumTerm The container for medium term data.
     * @param longTerm The container for long term data.
     */
    private void setChangeListener(Select<String> sortBy, Div contentDiv, OrderedList shortTerm, 
                                   OrderedList mediumTerm, OrderedList longTerm) {
        sortBy.addValueChangeListener(e -> {
            contentDiv.removeAll();
            String value = sortBy.getValue();
            if (value == timePeriods[0]) contentDiv.add(shortTerm);
            else if (value == timePeriods[1]) contentDiv.add(mediumTerm);
            else if (value == timePeriods[2]) contentDiv.add(longTerm);
        });
    }
}
