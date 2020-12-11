package app;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class MainMenuView extends BorderPane {
    public static Button playButton;
    public static Button quitButton;
    public static Button settingsButton;
    public static Button helpButton;
    public OverlayMenuView ovm, ovm2;

    public MainMenuView() {
        ovm = new OverlayMenuView();
        ovm2 = new OverlayMenuView();
        getChildren().addAll(ovm,ovm2);

        setMinSize(Main.viewWidth, Main.viewHeight);

        // Set background to bg.png
        Image bgImg = new Image("Sprites/main_menu.png");
        Background bg = new Background(new BackgroundImage(bgImg, null, null, null, null));
        setBackground(bg);

        VBox optionsMenu = new VBox();

        optionsMenu.setAlignment(Pos.CENTER);
        optionsMenu.setTranslateX(300);
        optionsMenu.setTranslateY(50);

        playButton = new Button("Play");
        quitButton = new Button("Quit");
        settingsButton = new Button("Settings");
        helpButton = new Button("Help");

        //styling
        /**
         * This is to implement out own hover over feature so that we are not using the default buttons
         */
        playButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px; ");
        quitButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;");
        settingsButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;");
        helpButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;");

        playButton.setOnMouseEntered( e -> playButton.setStyle("-fx-background-color: #00E0E0; -fx-border-color: #002020; -fx-border-width: 5px;"));
        playButton.setOnMouseExited( e -> playButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;"));

        quitButton.setOnMouseEntered( e -> quitButton.setStyle("-fx-background-color: #00E0E0; -fx-border-color: #002020; -fx-border-width: 5px;"));
        quitButton.setOnMouseExited( e -> quitButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;"));

        settingsButton.setOnMouseEntered( e -> settingsButton.setStyle("-fx-background-color: #00E0E0; -fx-border-color: #002020; -fx-border-width: 5px;"));
        settingsButton.setOnMouseExited( e -> settingsButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;"));

        helpButton.setOnMouseEntered( e -> helpButton.setStyle("-fx-background-color: #00E0E0; -fx-border-color: #002020; -fx-border-width: 5px;"));
        helpButton.setOnMouseExited( e -> helpButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;"));

        //alignment of buttons
        playButton.setMinSize(Main.viewWidth * 0.28, Main.viewHeight * 0.04);
        quitButton.setMinSize(Main.viewWidth * 0.28, Main.viewHeight * 0.04);
        settingsButton.setMinSize(Main.viewWidth * 0.28, Main.viewHeight * 0.04);
        helpButton.setMinSize(Main.viewWidth * 0.28, Main.viewHeight * 0.04);

        //set up the fonts for the buttons
        Font font = Font.font("Agency FB", FontWeight.EXTRA_BOLD, 20);

        playButton.setFont(font);
        quitButton.setFont(font);
        settingsButton.setFont(font);
        helpButton.setFont(font);

        //margins
        VBox.setMargin(playButton, new Insets(12, 12, 15, 12));
        VBox.setMargin(quitButton, new Insets(12, 12, 15, 12));
        VBox.setMargin(settingsButton, new Insets(12, 12, 15, 12));
        VBox.setMargin(helpButton, new Insets(12, 12, 15, 12));

        //adding them all
        optionsMenu.getChildren().addAll(playButton, settingsButton, helpButton, quitButton);

        setCenter(optionsMenu);

        playButton.setOnAction(event -> {
            Main.menuController.startGame();
        });

        quitButton.setOnAction(event -> {
            Main.menuController.exitGame();
        });

        //allows the user to click off of the settings menu
        settingsButton.setOnAction(event -> {
            ovm.setImageToDisplay("Sprites/settings.png", true);
            ovm.setVisible(true);
            ovm.toFront();
            ovm.setOnMousePressed((e) -> {
                if(e.getTarget() == ovm){
                    ovm.setVisible(false);
                }
            });
        });

        // allows the user to click off off the help menu
        helpButton.setOnAction(event -> {
            ovm2.setImageToDisplay("Sprites/help.png", false);
            ovm2.setVisible(true);
            ovm2.toFront();
            ovm2.setOnMousePressed((e) -> {
                if(e.getTarget() == ovm2){
                    ovm2.setVisible(false);
                }
            });
        });

        //we want to display the team members names on the bottom of the screen
    }
}