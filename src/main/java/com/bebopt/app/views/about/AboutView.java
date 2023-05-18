package com.bebopt.app.views.about;

import com.bebopt.app.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("About")
@Route(value = "about", layout = MainLayout.class)
@AnonymousAllowed
public class AboutView extends VerticalLayout {

    public AboutView() {
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        setSpacing(false);
        setMargin(true);

        H2 header = new H2("User Guide");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.LARGE, FontSize.XXXLARGE);


        // introduction section
        H2 headerIntro = new H2("Introduction");
        headerIntro.addClassNames(Margin.Top.LARGE, Margin.Bottom.NONE, FontSize.XXLARGE);

        H2 headerPurpose = new H2("1.1. Purpose");
        headerPurpose.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerPurpose.getStyle().set("color", "#4f545e");
        headerPurpose.getStyle().set("margin-left", "30px");

        Paragraph description1 = new Paragraph("This manual will guide you through the various features and functionalities of this app, helping you make the most of your music streaming experience.");
        description1.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description1.getStyle().set("margin-left", "30px");

        H2 headerSystemReq = new H2("1.2. System Requirements");
        headerSystemReq.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerSystemReq.getStyle().set("color", "#4f545e");
        headerSystemReq.getStyle().set("margin-left", "30px");

        Paragraph description2 = new Paragraph("To access and use the Spotify Web App, you will need:");
        description2.addClassNames(Margin.Bottom.NONE, Margin.Top.NONE, TextColor.SECONDARY);
        description2.getStyle().set("margin-left", "30px");

        Paragraph desc1 = new Paragraph("A computer or mobile device with an internet connection");
        desc1.addClassNames(Margin.Bottom.NONE, Margin.Top.NONE, TextColor.SECONDARY);
        desc1.getStyle().set("margin-left", "60px");

        Paragraph desc2 = new Paragraph("A web browser");
        desc2.addClassNames(Margin.Bottom.NONE, Margin.Top.NONE, TextColor.SECONDARY);
        desc2.getStyle().set("margin-left", "60px");
        add(header, headerIntro, headerPurpose, description1, headerSystemReq, description2, desc1, desc2);
    
    
    
        // getting started section
        H2 headerStart = new H2("Getting Started");
        headerStart.addClassNames(Margin.Top.LARGE, Margin.Bottom.NONE, FontSize.XXLARGE);



        H2 headerInstallation = new H2("2.1. Project Installation and Setup");
        headerInstallation.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerInstallation.getStyle().set("color", "#4f545e");
        headerInstallation.getStyle().set("margin-left", "30px");

        Paragraph pt1 = new Paragraph("Ensure that the Java Development Kit (JDK) is installed on your computer. Also ensure that Maven, a build automation tool for Java projects, is installed; this can be downloaded online or though a package manager. You will also need an integrated Development Environment (IDE) to run the project.");
        pt1.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt1.getStyle().set("margin-left", "30px");

        Paragraph pt2 = new Paragraph("Clone the repository to your computer or download it as a ZIP file and extract it to a local directory.");
        pt2.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt2.getStyle().set("margin-left", "30px");

        Paragraph pt3 = new Paragraph("Configure your IDE to use the correct JDK version installed on your device, ensuring the correct path to the JDK is set in your IDE’s settings.");
        pt3.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt3.getStyle().set("margin-left", "30px");

        Paragraph pt4 = new Paragraph("Open the project in your IDE.");
        pt4.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt4.getStyle().set("margin-left", "30px");

        Paragraph pt5 = new Paragraph("Configure the project with Spotify:");
        pt5.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt5.getStyle().set("margin-left", "30px");

        Paragraph subpt1 = new Paragraph("Create a new Spotify application using the following guide: https://developer.spotify.com/documentation/web-api/concepts/apps.");
        subpt1.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        subpt1.getStyle().set("margin-left", "60px");

        Paragraph subpt2 = new Paragraph("Set the redirect URI in your application to http://localhost:8080/callback. This is where the application will be redirected after you log in.");
        subpt2.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        subpt2.getStyle().set("margin-left", "60px");

        Paragraph subpt3 = new Paragraph("The Client ID is the unique identifier of your new app, and the Client Secret is the key that will be used to authorize your Web API calls. As the Client Secret is a sensitive value, you should never reveal it publicly. If your Client Secret is exposed, you may request a new one within your Spotify application. Copy your Client ID and Client Secret into their respective fields in the Client class of the opened project in your IDE. If you wish, you may alternatively set these values in your application.properties file and update the AuthController class to retrieve these values directly instead of using the Client class.");
        subpt3.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        subpt3.getStyle().set("margin-left", "60px");

        Paragraph pt6 = new Paragraph("Run the project from your IDE. This will start the embedded Tomcat server.");
        pt6.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt6.getStyle().set("margin-left", "30px");

        Paragraph pt7 = new Paragraph("Access the application by entering http://localhost:8080 (or another port if you have configured the project differently) in your web browser.");
        pt7.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt7.getStyle().set("margin-left", "30px");

        Paragraph pt8 = new Paragraph("Close the application by closing your web browser and terminating the program in your IDE.");
        pt8.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        pt8.getStyle().set("margin-left", "30px");

    
        H2 headerAccount = new H2("2.2. Account Creation");
        headerAccount.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerAccount.getStyle().set("color", "#4f545e");
        headerAccount.getStyle().set("margin-left", "30px");

        Paragraph description3 = new Paragraph("If you don't already have a Spotify account, visit the Spotify website (www.spotify.com) to create a new account. Follow the registration process and provide the necessary information to create your account. Note that if your account lacks the necessary data, you may not be able to access many of the functions of this application.");
        description3.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description3.getStyle().set("margin-left", "30px");

        H2 headerLogin = new H2("2.3. Logging In");
        headerLogin.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerLogin.getStyle().set("color", "#4f545e");
        headerLogin.getStyle().set("margin-left", "30px");

        Paragraph description4 = new Paragraph("To access the Spotify Web App, click on the 'Sign in' button in the top right corner of the home page. You will be redirected to the Spotify login page. Enter your Spotify username and password to log in to your account.");
        description4.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description4.getStyle().set("margin-left", "30px");

        H2 headerHome = new H2("2.4. Home Page Overview");
        headerHome.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerHome.getStyle().set("color", "#4f545e");
        headerHome.getStyle().set("margin-left", "30px");

        Paragraph description5 = new Paragraph("Once logged in, you will be directed to the home page of the app. Here, you will find a menu at the top of the page and the main content area below it. The menu contains options which will allow you to toggle between the Statistics, Playlists, Recommendations, and About pages.");
        description5.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description5.getStyle().set("margin-left", "30px");

        H2 headerNavigate = new H2("2.5. Navigating the Sidebar Menu");
        headerNavigate.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerNavigate.getStyle().set("color", "#4f545e");
        headerNavigate.getStyle().set("margin-left", "30px");

        Paragraph description6 = new Paragraph("Use the menu to navigate between different sections of the app. Click on the respective menu item to access the desired page. The Statistics, Playlists, and Recommendations options of the menu will only be visible and accessible once you have successfully logged into your Spotify account.");
        description6.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description6.getStyle().set("margin-left", "30px");

        add(headerStart, headerInstallation, pt1, pt2, pt3, pt4, pt5, subpt1, subpt2, subpt3, pt6, pt7, pt8);
        add(headerAccount, description3, headerLogin, description4);
        add(headerHome, description5, headerNavigate, description6);
    

        
        // viewing statistics section
        H2 headerStats = new H2("Viewing Statistics");
        headerStats.addClassNames(Margin.Top.LARGE, Margin.Bottom.NONE, FontSize.XXLARGE);

        H2 headerTracks = new H2("3.1. Top Tracks");
        headerTracks.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerTracks.getStyle().set("color", "#4f545e");
        headerTracks.getStyle().set("margin-left", "30px");

        Paragraph description7 = new Paragraph("In the Statistics section, select the 'Top Tracks' tab to view your most listened to tracks. The tracks will be displayed in order along with the artist names and album images.");
        description7.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description7.getStyle().set("margin-left", "30px");

        H2 headerArtists = new H2("3.2. Top Artists");
        headerArtists.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerArtists.getStyle().set("color", "#4f545e");
        headerArtists.getStyle().set("margin-left", "30px");

        Paragraph description8 = new Paragraph("Navigate to the Statistics section and click on the 'Top Artists' tab to see your most listened to artists. The artists will be listed with their images.");
        description8.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description8.getStyle().set("margin-left", "30px");

        // H2 headerGenres = new H2("3.3. Top Genres");
        // headerGenres.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        // headerGenres.getStyle().set("color", "#4f545e");
        // headerGenres.getStyle().set("margin-left", "30px");

        // Paragraph description9 = new Paragraph("To view your top genres, go to the Statistics section and select the 'Top Genres' tab. The genres will be presented in a numbered list.");
        // description9.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        // description9.getStyle().set("margin-left", "30px");

        H2 headerTime = new H2("3.3. Time Range Selection");
        headerTime.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerTime.getStyle().set("color", "#4f545e");
        headerTime.getStyle().set("margin-left", "30px");

        Paragraph description10 = new Paragraph("In the Statistics section, you can choose to view your data for the last 4 weeks, 6 months, or all time. Use the drop-down select menu at the top right corner of the main content area to toggle between different time ranges and see how your listening preferences change over time.");
        description10.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description10.getStyle().set("margin-left", "30px");

        add(headerStats, headerTracks, description7, headerArtists, description8);
        add(headerTime, description10);
    
    

        // managing playlists section
        H2 headerPlaylists = new H2("Managing Playlists");
        headerPlaylists.addClassNames(Margin.Top.LARGE, Margin.Bottom.NONE, FontSize.XXLARGE);

        H2 headerOverview = new H2("4.1. Playlist Overview");
        headerOverview.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerOverview.getStyle().set("color", "#4f545e");
        headerOverview.getStyle().set("margin-left", "30px");

        Paragraph description11 = new Paragraph("In the Playlists section, you will find a grid of your playlists. Each playlist will be displayed with its name and cover image.");
        description11.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description11.getStyle().set("margin-left", "30px");

        H2 headerSort = new H2("4.2. Sorting Playlists");
        headerSort.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerSort.getStyle().set("color", "#4f545e");
        headerSort.getStyle().set("margin-left", "30px");

        Paragraph description12 = new Paragraph("To sort your playlists, navigate to the Playlists section and make a selection from the playlists that are displayed there. A window will appear allowing you to select the sorting criteria: release date, duration, popularity, acousticness, danceability, energy, instrumentalness, loudness, speechiness, tempo, or valence. Click on the desired sorting option, and the playlists will be rearranged accordingly.");
        description12.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description12.getStyle().set("margin-left", "30px");

        H2 headerFilter = new H2("4.3. Filtering Playlists");
        headerFilter.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerFilter.getStyle().set("color", "#4f545e");
        headerFilter.getStyle().set("margin-left", "30px");

        Paragraph description13 = new Paragraph("In the Playlists section, you can filter your playlists based on specific criteria. Click on a specific playlist and select the Filter tab. Currently our application only offers the option to filter by release decade. When the option to sort by release date is selected, the available decades for the selected playlist will be displayed on the window. Select the decade you wish to filter the playlist by then press the Create New Playlist button. Select Confirm in the confirmation dialog that opens after this and a new playlist containing the desired songs will be created.");
        description13.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description13.getStyle().set("margin-left", "30px");

        H2 headerMerge = new H2("4.4. Merging Playlists");
        headerMerge.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerMerge.getStyle().set("color", "#4f545e");
        headerMerge.getStyle().set("margin-left", "30px");

        Paragraph description14 = new Paragraph("If you wish to merge multiple playlists into a single playlist, go to the Playlists section and select the first playlist you wish to merge. In the dialog that opens, navigate to the Merge tab then select the second playlist you wish to merge with the first. Click on Create New Playlist, then click Confirm in the dialog that appears, and the selected playlists will be combined into a new playlist.");
        description14.addClassNames(Margin.Bottom.XSMALL, Margin.Top.XSMALL, TextColor.SECONDARY);
        description14.getStyle().set("margin-left", "30px");

        Paragraph note = new Paragraph("Note: The original playlists will remain unaffected; new playlists will be created for each option.");
        note.addClassNames(TextColor.SECONDARY);
        note.getStyle().set("margin-left", "30px");

        add(headerPlaylists, headerOverview, description11, headerSort, description12);
        add(headerFilter, description13, headerMerge, description14, note);



        // recommendations section
        H2 headerRecs = new H2("Getting Recommended Songs");
        headerRecs.addClassNames(Margin.Top.LARGE, Margin.Bottom.NONE, FontSize.XXLARGE);
  
        H2 headerRecommendations = new H2("5.1. Recommended Songs");
        headerRecommendations.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerRecommendations.getStyle().set("color", "#4f545e");
        headerRecommendations.getStyle().set("margin-left", "30px");

        Paragraph description15 = new Paragraph("The recommended songs section provides you with a list of song recommendations based off of your most listened to tracks.");
        description15.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description15.getStyle().set("margin-left", "30px");

        H2 headerRelatedArtists = new H2("5.2. Related Artists");
        headerRelatedArtists.addClassNames(Margin.Top.MEDIUM, Margin.Bottom.XSMALL, FontSize.LARGE);
        headerRelatedArtists.getStyle().set("color", "#4f545e");
        headerRelatedArtists.getStyle().set("margin-left", "30px");

        Paragraph description16 = new Paragraph("Selecting the Related Artists tab of the recommendations page will give you a list of artists similar to those you have recently listened to.");
        description16.addClassNames(Margin.Bottom.XSMALL, Margin.Top.NONE, TextColor.SECONDARY);
        description16.getStyle().set("margin-left", "30px");

        add(headerRecs, headerRecommendations, description15, headerRelatedArtists, description16);  
        
        
        
        Paragraph end = new Paragraph("We hope this user manual has provided you with a comprehensive understanding of the Spotify Web App and its features. Enjoy exploring your top tracks, managing playlists, and discovering new music with ease! If you have any further questions or encounter any issues, please refer to the contact information provided in the app or reach out to our support team. Happy listening!");
        end.addClassNames(Margin.Top.XLARGE, TextColor.SECONDARY);

        add(end);
    }

}
