package app;


import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import javafx.scene.media.*;

import java.io.File;


public class SplashScreenView extends BorderPane {

    // sets the splash screen to be the requisite background image.
    public SplashScreenView(){
        setMinSize(Main.viewWidth, Main.viewHeight);
        Image splashImage = new Image("Sprites/splash_screen.png");
        Background bg = new Background( new BackgroundImage(splashImage,null,null,null,null));
        setBackground(bg);

        loadSplashScreen();

    }
    private void loadSplashScreen(){

        //fade in effect
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), this);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        //fade out effect
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), this);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        //fade in
        fadeIn.play();

        //fade out once it has faded in
        fadeIn.setOnFinished((e) ->{
            fadeOut.play();
        });

        //after the fade out transition to the main menu
        fadeOut.setOnFinished((e) -> {
           Main.setScene(Main.mainMenu);
//            String path = "Audio/title.mp3";
//            Media media = new Media( new File(path).toURI().toString());
//
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setAutoPlay(true);
        });




    }

}