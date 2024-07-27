package com.bebopt.app.views;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bebopt.app.api.PlaylistManager;
import com.bebopt.app.api.SpotifyService;
import com.bebopt.app.objects.PlaylistCard;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;

import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;

/**
 * The {@code PlaylistsView} class represents the view for managing Spotify playlists.
 * This class interacts with the {@code PlaylistActions} class for business logic.
 */
@AnonymousAllowed
@PageTitle("Playlists")
@Route(value = "playlists", layout = MainLayout.class)
public class PlaylistsView extends Main {

    private static PlaylistSimplified selectedPlaylist;
    private static String selectedPlaylistID;
    private static PlaylistSimplified selectedMergePlaylist;
    static String selectedMergePlaylistID;

    private OrderedList playlistsContainer;

    private static TabSheet tabsheet;
    private static VerticalLayout sortTab;
    private static VerticalLayout filterTab;
    private static VerticalLayout mergeTab;

    private static RadioButtonGroup<String> radioGroupSort;
    private static RadioButtonGroup<String> radioGroupFilter;
    private static RadioButtonGroup<String> radioGroupFilterDecades;
    private static RadioButtonGroup<String> radioGroupFilterGenres;
    private static ListBox<PlaylistSimplified> listBoxMerge;

    private static String selectedSort;
    private static String selectedFilter;
    private static Div decadesContainer;
    private static List<String> decadeKeysStr;
    private static Integer selectedDecade;
    private static Div genresContainer;
    private static List<String> genreKeysStr;
    private static String selectedGenre;

    public static Dialog optionsDialog;

    private static List<String> sortItems = Arrays.asList("Release date", "Duration", "Popularity", "Acousticness",
                "Danceability", "Energy", "Instrumentalness", "Loudness", "Speechiness", "Tempo", "Valence");
    private static List<String> filterItems = Arrays.asList("Release decade", "Genre");

    /**
     * Constructor for the {@code PlaylistsView} class.
     * Initializes the UI and loads Spotify playlists.
     * 
     * @throws Exception if an error occurs during Spotify API interaction.
     */
    public PlaylistsView() throws Exception {
        constructUI();
        loadPlaylists();
    }

    /**
     * Constructs the user interface for the playlists view.
     * Sets up the header, description, and container for displaying playlists.
     */
    private void constructUI() {
        addClassNames("playlists-view", "page-view");
        playlistsContainer = new OrderedList();

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames("header-container");

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Playlists");
        Paragraph description = new Paragraph("Select a playlist to view more options");
        headerContainer.add(header, description);

        Dialog dialog = createDialog(); /* Playlist action dialog opens when the user selects a playlist. */
        container.add(headerContainer);
        add(container, playlistsContainer, dialog);
    }

    /**
     * Loads Spotify playlists.
     * Updates the playlist container with the fetched playlists.
     * 
     * @throws Exception If an error occurs during Spotify API interaction.
     */
    private void loadPlaylists() throws Exception {
        PlaylistSimplified[] playlists = SpotifyService.getPlaylists();
        for (PlaylistSimplified playlist : playlists) {
            playlistsContainer.add(new PlaylistCard(playlist));
        }
    }
    
// -------------------------------------- Options dialog --------------------------------------

    /**
     * Create dialog window displaying playlist options.
     * 
     * @return The playlist options dialog window.
     */
    public static Dialog createDialog() {
        optionsDialog = new Dialog();
        optionsDialog.setHeaderTitle("Playlist Options");
        optionsDialog.setHeight("800px");
        optionsDialog.setWidth("650px");
        optionsDialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
        optionsDialog.add(createTabsheet());

        Button closeButton = new Button(new Icon("lumo", "cross"), e -> optionsDialog.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        optionsDialog.getHeader().add(closeButton);

        Button newPlaylist = new Button("New Playlist", e -> dialogChooseActionEvent());
        newPlaylist.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        optionsDialog.getFooter().add(newPlaylist);

        return optionsDialog;
    }

    /**
     * Display dialog prompting user to confirm their selected action.
     */
    public static void dialogChooseActionEvent() {
        Tab t = tabsheet.getSelectedTab();
        if (t == tabsheet.getTabAt(0))      { confirmDialog("Sort", selectedSort); } 
        else if (t == tabsheet.getTabAt(1)) { confirmDialog("Filter", selectedFilter); } 
        else if (t == tabsheet.getTabAt(2)) { confirmDialog("Merge", selectedMergePlaylistID); }
    }
    
    /**
     * Save the selected playlist and display a dialog for other options.
     * 
     * @param selectedPlaylist The playlist selected by the user.
     */
    public static void onPlaylistSelect(PlaylistSimplified playlist) {
        resetMergePlaylistSelection();
        selectedPlaylist = playlist;
        selectedPlaylistID = selectedPlaylist.getId();
        optionsDialog.setHeaderTitle(selectedPlaylist.getName());
        optionsDialog.open();
    }

    /**
     * Creates the TabSheet component with tabs for sorting, filtering, and merging playlists.
     * 
     * @return The TabSheet component.
     */
    private static TabSheet createTabsheet() {
        tabsheet = new TabSheet();
        createSortTab(); createFilterTab(); createMergeTab();

        tabsheet.add("Sort", sortTab);
        tabsheet.add("Filter", filterTab);
        tabsheet.add("Merge", mergeTab);

        tabsheet.addThemeVariants(TabSheetVariant.LUMO_TABS_EQUAL_WIDTH_TABS);
        return tabsheet;
    }
    
    /**
     * Creates the tab for sorting playlists.
     */
    private static void createSortTab() {
        sortTab = new VerticalLayout();
        radioGroupSort = createRadioGroup("Sort by: ", null, sortItems);
        radioGroupSort.setValue(sortItems.get(0));
        radioGroupSort.addValueChangeListener(e -> selectedSort = e.getValue());
        sortTab.add(radioGroupSort);
    }
    
    /**
     * Creates the tab for filtering playlists.
     */
    private static void createFilterTab() {
        filterTab = new VerticalLayout();
        radioGroupFilter = createRadioGroup("Filter by: ", null, filterItems);

        radioGroupFilterDecades = createRadioGroup(null, "indented-radio-group", null);
        radioGroupFilterDecades.addValueChangeListener(event -> {
            selectedDecade = Integer.valueOf(event.getValue().substring(0, 4));
            selectedGenre = null;
        });
        decadesContainer = new Div();
        decadesContainer.setVisible(false);
        decadesContainer.addClassName("indented-radio-group-container");
        decadesContainer.add(radioGroupFilterDecades);

        radioGroupFilterGenres = createRadioGroup(null, "indented-radio-group", null);
        radioGroupFilterGenres.addValueChangeListener(event -> {
            selectedGenre = event.getValue();
            selectedDecade = null;
        });
        genresContainer = new Div();
        genresContainer.setVisible(false);
        genresContainer.addClassName("indented-radio-group-container");
        genresContainer.add(radioGroupFilterGenres);

        addFilterValueChangeListener();
        filterTab.add(radioGroupFilter, decadesContainer, genresContainer);
    }
    
    /**
     * Creates the tab for merging playlists.
     */
    private static void createMergeTab() {
        mergeTab = new VerticalLayout();
        Label label = new Label("Select a second playlist: ");
        List<PlaylistSimplified> items = Arrays.asList(SpotifyService.getPlaylists());
        createMergeListBox(items);
        mergeTab.add(label, listBoxMerge);
    }
    
    /**
     * Creates a radio button group with the given label, class name, and/or items.
     * 
     * @param label The label for the radio button group.
     * @param className The class name for the radio button group.
     * @param items The items for the radio button group options.
     * @return The radio button group component.
     */
    private static RadioButtonGroup<String> createRadioGroup(String label, String className, List<String> items) {
        RadioButtonGroup<String> group = new RadioButtonGroup<>();
        group.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        if (label != null) group.setLabel(label);
        if (className != null) group.addClassNames(className);
        if (items != null) group.setItems(items);
        return group;
    }
    
    /**
     * Adds a value change listener to the filter radio group. The listener updates the selected
     * filter and manages the visibility and contents of the filter containers.
     * 
     * @return The {@code Registration} object representing the registration of the value change listener.
     */
    private static Registration addFilterValueChangeListener() {
        return radioGroupFilter.addValueChangeListener(e -> {
            selectedFilter = e.getValue();
            if (selectedFilter.equals("Release decade")) {
                genresContainer.setVisible(false);
                if (radioGroupFilterDecades.isEmpty()) {
                    List<String> decadeKeysStr = getDecadeKeys();
                    Collections.sort(decadeKeysStr);
                    radioGroupFilterDecades.setItems(decadeKeysStr);
                    decadesContainer.add(radioGroupFilterDecades);
                }   decadesContainer.setVisible(true);
            } else if (selectedFilter.equals("Genre")) {
                decadesContainer.setVisible(false);
                if (radioGroupFilterGenres.isEmpty()) {
                    List<String> genresKeysStr = getGenreKeys();
                    Collections.sort(genresKeysStr);
                    radioGroupFilterGenres.setItems(genresKeysStr);
                    genresContainer.add(radioGroupFilterGenres);
                }   genresContainer.setVisible(true);
            } else { 
                decadesContainer.setVisible(false); 
                genresContainer.setVisible(false);
            }
        });
    }

    /**
     * Display and return list of decades present in a playlist.
     * 
     * @return Keys of map corresponding to music decades.
     */
    public static List<String> getDecadeKeys() {
        Set<Integer> decadeMapKeys = PlaylistManager.getDecadeKeys(selectedPlaylistID);
        decadeKeysStr = decadeMapKeys.stream().map(key -> key + "s").collect(Collectors.toList());
        return decadeKeysStr;
    }

    /**
     * Display and return list of genres present in a playlist.
     * 
     * @return Keys of map corresponding to music genres.
     */
    public static List<String> getGenreKeys() {
        Set<String> genreMapKeys = PlaylistManager.getGenreKeys(selectedPlaylistID);
        genreKeysStr = genreMapKeys.stream().collect(Collectors.toList());
        return genreKeysStr;
    }

    /**
     * Creates and configures a list box for displaying and selecting playlists.
     * Initializes the {@code listBoxMerge} object with a given list of playlists, sets a custom
     * renderer to display each playlist with its image and title, and adds a value change listener
     * to update the selected playlist ID when a new playlist is selected.
     * 
     * @param items A list of {@code PlaylistSimplified} objects representing the user's playlists.
     */
    private static void createMergeListBox(List<PlaylistSimplified> items) {
        /* Create a list of the user's playlists. */ 
        listBoxMerge = new ListBox<>();
        listBoxMerge.setItems(items);
        listBoxMerge.setRenderer(new ComponentRenderer<>(item -> {
            HorizontalLayout row = new HorizontalLayout();
            row.addClassNames("row");
            Avatar a = new Avatar();
            if (item.getImages().length != 0) { a.setImage(item.getImages()[0].getUrl()); } 
            else { a.setImage("images/empty-plant.png"); }
            Span name = new Span(item.getName());
            row.add(a, name);
            row.getStyle().set("line-height", "var(--lumo-line-height-m)");
            return row;
        }));
        listBoxMerge.addValueChangeListener(e -> {
            selectedMergePlaylist = listBoxMerge.getValue();
            selectedMergePlaylistID = selectedMergePlaylist.getId();
        });
    }

    // -------------------------------------- Confirm dialog --------------------------------------

    /**
     * Create a confirmation dialog and handle the selected action.
     * 
     * @param action The primary action chosen ("Sort", "Filter", or "Merge").
     * @param selectedAction The secondary action chosen based on the primary action.
     */
    private static void confirmDialog(String action, String selectedAction) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.addClassNames("confirm-dialog");
        confirmDialog.setHeader("Confirm");

        if ("Merge".equals(action)) {
            confirmDialog.setText("Do you want to merge the playlists\n"
                + "\"" + selectedPlaylist.getName() + "\" and \"" + selectedMergePlaylist.getName() + "\"?");
        } else if ("Sort".equals(action) || "Filter".equals(action)) {
            confirmDialog.setText("Do you want to " + action.toLowerCase() + " the playlist \"" 
                + selectedPlaylist.getName() + "\" by " + selectedAction.toLowerCase() + "?");
        } else { errorDialog(); return; }

        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmText(action);
        confirmDialog.addConfirmListener(e -> {
            switch (action) {
                case "Sort": PlaylistManager.sortPlaylist(selectedPlaylistID, selectedSort); break;
                case "Filter": 
                    if (selectedDecade != null) PlaylistManager.filterPlaylist(selectedPlaylistID, selectedFilter, selectedDecade); 
                    else if (selectedGenre != null) PlaylistManager.filterPlaylist(selectedPlaylistID, selectedFilter, selectedGenre);
                    break;
                case "Merge": PlaylistManager.mergePlaylists(selectedPlaylistID, selectedMergePlaylistID); break;
            }
        });
        confirmDialog.open();
    }

    /**
     * Reset the values of the second playlist selected under the "Merge" tab when the primary 
     * playlist is deselected and a new primary playlist is selected.
     */
    public static void resetMergePlaylistSelection() {
        radioGroupSort.setValue(null);
        radioGroupFilter.setValue(null);
        listBoxMerge.setValue(null);
        decadesContainer.setVisible(false);
        radioGroupFilterDecades.clear();
        decadeKeysStr = null;
        selectedDecade = null;
        selectedSort = selectedFilter = selectedMergePlaylistID = null;
    }

    // --------------------------------------- Error dialog ---------------------------------------

    /**
     * Display an error dialog when the user needs to select a second playlist.
     */
    private static void errorDialog() {
        ConfirmDialog errorDialog = new ConfirmDialog();
        errorDialog.setWidth("550px");
        errorDialog.setHeader("Error");
        errorDialog.setText("Please make a selection.");
        errorDialog.setConfirmText("Continue");
        errorDialog.open();
    }
}
