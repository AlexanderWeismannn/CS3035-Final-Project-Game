package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PauseMenuView extends BorderPane {
    public PauseMenuView(){
        setVisible(false);

        setPrefSize(Main.viewWidth, Main.viewHeight);
        //Create slightly transparent background
        BackgroundFill background = new BackgroundFill(new Color(0,0,0,0.5), new CornerRadii(0), new Insets(0,0,0,0));
        setBackground(new Background(background));

        //Create vbox with main buttons, centered

        VBox menuButtons = new VBox();
        Button resumeButton = new Button("Resume");
        Button mainMenuButton = new Button("Main Menu");
        Button quitButton = new Button("Quit");

        resumeButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px; ");
        mainMenuButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;");
        quitButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;");

        resumeButton.setOnMouseEntered( e -> resumeButton.setStyle("-fx-background-color: #00E0E0; -fx-border-color: #002020; -fx-border-width: 5px;"));
        resumeButton.setOnMouseExited( e -> resumeButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;"));

        quitButton.setOnMouseEntered( e -> quitButton.setStyle("-fx-background-color: #00E0E0; -fx-border-color: #002020; -fx-border-width: 5px;"));
        quitButton.setOnMouseExited( e -> quitButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;"));

        mainMenuButton.setOnMouseEntered( e -> mainMenuButton.setStyle("-fx-background-color: #00E0E0; -fx-border-color: #002020; -fx-border-width: 5px;"));
        mainMenuButton.setOnMouseExited( e -> mainMenuButton.setStyle("-fx-background-color: #009090; -fx-border-color: transparent; -fx-border-width: 5px;"));

        //set up the fonts for the buttons
        Font font = Font.font("Agency FB", FontWeight.EXTRA_BOLD, 20);

        //alignment of buttons
        resumeButton.setMinSize(Main.viewWidth * 0.28, Main.viewHeight * 0.04);
        mainMenuButton.setMinSize(Main.viewWidth * 0.28, Main.viewHeight * 0.04);
        quitButton.setMinSize(Main.viewWidth * 0.28, Main.viewHeight * 0.04);

        resumeButton.setFont(font);
        mainMenuButton.setFont(font);
        quitButton.setFont(font);

        VBox.setMargin(resumeButton, new Insets(12, 12, 15, 12));
        VBox.setMargin(mainMenuButton, new Insets(12, 12, 15, 12));
        VBox.setMargin(quitButton, new Insets(12, 12, 15, 12));

        //Add buttons to screen
        menuButtons.getChildren().addAll(resumeButton, mainMenuButton, quitButton);
        menuButtons.setAlignment(Pos.CENTER);
        setCenter(menuButtons);

        //Resume game (Bad mvc)
        resumeButton.setOnAction(event -> {
            Main.GAME_CONTROLLER.ResumeGame();
        });
        //Exit to main menu
        mainMenuButton.setOnAction(event -> {
            Main.GAME_CONTROLLER.returnToMenu();
        });
        //quit game
        quitButton.setOnAction(event -> {
            System.out.println("Exiting Game...");
            System.exit(1);});
    }
}
