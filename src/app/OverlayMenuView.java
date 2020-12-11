package app;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class OverlayMenuView extends Pane {
    public static ImageView imageToDisplay;
    public OverlayMenuView() {

        setVisible(false);
        setWidth(Main.viewWidth);
        setHeight(Main.viewHeight);

    }

    public void setImageToDisplay(String url, Boolean slider){

        //Bring to front
        toFront();

        //Set pick on bounds ignores pane clicks
        //setPickOnBounds(false);

        imageToDisplay = new ImageView(new Image(url, 1100,
                700, true, true));

        imageToDisplay.setX(Main.viewWidth*0.06);
        imageToDisplay.setY(Main.viewHeight*0.06);

        Slider my_slider = new Slider();
        my_slider.setMinWidth(Main.viewWidth*0.2);
        my_slider.setTranslateX(Main.viewWidth*0.428);
        my_slider.setTranslateY(Main.viewHeight*0.37);

        ChoiceBox difficulty = new ChoiceBox();
        difficulty.getItems().add("Too hard? Boohoo :(");
        difficulty.setMinWidth(Main.viewWidth*0.2);
        difficulty.setTranslateX(Main.viewWidth*0.47);
        difficulty.setTranslateY(Main.viewHeight*0.44);

        ChoiceBox graphics = new ChoiceBox();
        graphics.getItems().add("What, our game isn't pretty enough?");
        graphics.setMinWidth(Main.viewWidth*0.2);
        graphics.setTranslateX(Main.viewWidth*0.46);
        graphics.setTranslateY(Main.viewHeight*0.524);


        //Add all the children
        getChildren().add(imageToDisplay);

        if (slider) {
            getChildren().addAll(my_slider, difficulty, graphics);
        }
    }
}
