package com.bebopt.app.views.playlists;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bebopt.app.data.spotify.SpotifyService;
import com.bebopt.app.views.MainLayout;
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
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.theme.lumo.LumoUtility.Grid.Column;

import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import com.vaadin.flow.server.auth.AnonymousAllowed;

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
    private static ListBox<PlaylistSimplified> listBoxMerge;

    private static String selectedSort;
    private static String selectedFilter;
    private static Div decadesContainer;
    private static List<String> decadeKeysStr;
    private static Integer selectedDecade;

    public static Dialog optionsDialog;

    private static List<String> sortItems = Arrays.asList("Release date", "Duration", "Popularity", "Acousticness",
                "Danceability", "Energy", "Instrumentalness", "Loudness", "Speechiness", "Tempo", "Valence");
    private static List<String> filterItems = Arrays.asList("Release decade");

    /**
     * Constructor for the PlaylistsView class.
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
        addClassNames("playlists-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Your Playlists");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("Select a playlist to view more options");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        headerContainer.add(header, description);

        playlistsContainer = new OrderedList();
        playlistsContainer.addClassNames(Gap.SMALL, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE,
                JustifyContent.BETWEEN, Column.COLUMNS_6);
        
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
            playlistsContainer.add(new PlaylistsViewCard(playlist));
        }
    }

    // --------------------------------- Options dialog tabsheet ----------------------------------

    /**
     * Creates the TabSheet component with tabs for sorting, filtering, and merging playlists.
     * 
     * @return The TabSheet component.
     */
    private static TabSheet createTabsheet() {
        tabsheet = new TabSheet();

        createSortTab();
        createFilterTab();
        createMergeTab();

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

        radioGroupSort = new RadioButtonGroup<>();
        radioGroupSort.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupSort.setLabel("Sort by: ");
        radioGroupSort.setItems(sortItems);
        radioGroupSort.setValue("Release date");
        
        radioGroupSort.addValueChangeListener(e -> {
            selectedSort = e.getValue();
        });

        sortTab.add(radioGroupSort);
    }

    /**
     * Creates the tab for filtering playlists.
     */
    private static void createFilterTab() {
        filterTab = new VerticalLayout();

        radioGroupFilter = new RadioButtonGroup<>();
        radioGroupFilter.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupFilter.setLabel("Filter by: ");
        radioGroupFilter.setItems(filterItems);
        createDecadeSelectRadioGroup();
        decadesContainer = new Div();
        decadesContainer.setVisible(false);
        decadesContainer.addClassName("indented-radio-group-container");
        decadesContainer.add(radioGroupFilterDecades);
        
        radioGroupFilter.addValueChangeListener(e -> {
            selectedFilter = e.getValue();
            
            if (selectedFilter.equals("Release decade")) {
                if (radioGroupFilterDecades.isEmpty()) {
                    List<String> decadeKeysStr = getDecadeKeys();
                    Collections.sort(decadeKeysStr);
                    
                    /* Add decade options to the dialog view. */
                    radioGroupFilterDecades.setItems(decadeKeysStr);
                    decadesContainer.add(radioGroupFilterDecades);
                    decadesContainer.setVisible(true);
                } 
                else { decadesContainer.setVisible(true); }
            } else { decadesContainer.setVisible(false); }
        });
        
        filterTab.add(radioGroupFilter, decadesContainer);
    }

    /**
     * Display and return list of decades present in a playlist.
     * 
     * @return Keys of map corresponding to music decades.
     */
    public static List<String> getDecadeKeys() {
        Set<Integer> decadeMapKeys = PlaylistActions.filterGetKeys(selectedPlaylistID);
        decadeKeysStr = decadeMapKeys.stream().map(key -> key + "s").collect(Collectors.toList());
        return decadeKeysStr;
    }

    public static void createDecadeSelectRadioGroup() {
        radioGroupFilterDecades = new RadioButtonGroup<>();
        radioGroupFilterDecades.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        radioGroupFilterDecades.addClassName("indented-radio-group");
        radioGroupFilterDecades.addValueChangeListener(event -> {
            selectedDecade = Integer.valueOf(event.getValue().substring(0, 4));
        });
    }

    /**
     * Creates the tab for merging playlists.
     */
    private static void createMergeTab() {
        mergeTab = new VerticalLayout();

        List<PlaylistSimplified> items = Arrays.asList(SpotifyService.getPlaylists());

        /* Create a list of the user's playlists. */ 
        listBoxMerge = new ListBox<>();
        listBoxMerge.setItems(items);
        Label label = new Label("Select a second playlist: ");
        label.addClassNames(FontSize.SMALL, Margin.Top.MEDIUM);

        /* Use a list box renderer to display the playlist image. */ 
        listBoxMerge.setRenderer(new ComponentRenderer<>(item -> {
            HorizontalLayout row = new HorizontalLayout();
            row.addClassNames(AlignItems.CENTER);

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
        mergeTab.add(label, listBoxMerge);
    }

    // -------------------------------------- Options dialog --------------------------------------

    /**
     * Save the selected playlist and display a dialog for other options.
     * 
     * @param selectedPlaylist The playlist selected by the user.
     */
    public static void onPlaylistSelect(PlaylistSimplified selectedPlaylist) {
        resetMergePlaylistSelection();
        selectedPlaylistID = selectedPlaylist.getId();
        optionsDialog.setHeaderTitle(selectedPlaylist.getName());
        optionsDialog.open();
    }

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

    // -------------------------------------- Confirm dialog --------------------------------------

    /**
     * Create a confirmation dialog and handle the selected action.
     * 
     * @param action The primary action chosen ("Sort", "Filter", or "Merge").
     * @param selectedAction The secondary action chosen based on the primary action.
     */
    private static void confirmDialog(String action, String selectedAction) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setWidth("550px");
        confirmDialog.setHeader("Confirm");

        if (selectedAction == null) {
            errorDialog();
            return;
        }

        if (action == "Merge") {
            confirmDialog.setText("Do you want to merge the following playlists:\n"
                + selectedPlaylist.getName() + " and " + selectedMergePlaylist.getName() + "?");
        } else if (action == "Sort" || action == "Filter") {
            confirmDialog.setText("Do you want to " + action.toLowerCase() + " the following playlist: " 
                + selectedPlaylist.getName() + " by " + selectedAction.toLowerCase() + "?");
        }

        confirmDialog.setCancelable(true);
        confirmDialog.setConfirmText(action);

        /* Check if the user has confirmed the action. */
        confirmDialog.addConfirmListener(e -> {
            if (action == "Sort") {
                PlaylistActions.sortPlaylist(selectedPlaylistID, selectedSort);
            } else if (action == "Filter") {
                PlaylistActions.filterPlaylist(selectedPlaylistID, selectedFilter, selectedDecade);
            } else if (action == "Merge") {
                PlaylistActions.mergePlaylists(selectedPlaylistID, selectedMergePlaylistID);
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