package app;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainMenuController {
    public MainMenuController() {
        Main.menuView.addEventHandler(MouseEvent.ANY, new MainMenuController.MouseHandler());
    }

    public class MouseHandler implements EventHandler<MouseEvent>{

        @Override
        public void handle(MouseEvent e){

            // if(e.getEventType() == MouseEvent.MOUSE_CLICKED) {
            //     System.out.println(e.getTarget().getClass());

//                //if the quit button was clicked exit the game
//                if(e.getTarget().getClass() == Main.menuView.quitButton.getClass()) {
//                    exitGame();
//                // if the play button was clicked start the game and call the new scene
//                } else if(e.getTarget().getClass() == Main.menuView.playButton.getClass()) {
//                    startGame();
//                //this will be for the settings button
//                } else if(e.getTarget().getClass() == Main.menuView.settingsButton.getClass()){
//                    settingsButtonClicked();
//                // this will be for the help button
//                }else if(e.getTarget().getClass() == Main.menuView.helpButton.getClass()){
//                    helpButtonClicked();
////                }
//            }
        }
    }

    //exits the game
    public void exitGame(){
        System.out.println("Exiting Game...");
        System.exit(1);
    }

    //call for the game to be started
    
    public void startGame(){
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5),Main.menuView);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.1),Main.menuView);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        //plays a fade out transition and after calls the new scene to be referenced.
        fadeOut.play();
        fadeOut.setOnFinished((e) -> {
            Main.setScene(Main.gameScene);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();
        });
    }

    // call the setting menu
    public void settingsButtonClicked(){

    }

    public void helpButtonClicked(){

    }
}