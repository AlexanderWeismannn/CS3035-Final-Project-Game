package app;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class OverlayView extends Pane {
    public static ImageView PlayButton;
    public OverlayView(){
        //Bring to front
        toFront();
        //Set pick on bounds ignores pane clicks
        setPickOnBounds(false);
        setWidth(Main.viewWidth);
        setHeight(Main.viewHeight);

        //Create Money
        Group money = new Group();
        ImageView moneyImg = new ImageView(new Image("Sprites/money_t_updated.png", Main.model.getTileWidth()*4,
                Main.model.getTileWidth()*4, true, true));

        //Money label bound to currency var
        Label moneyText = new Label();
        moneyText.setFont(new Font("Book Antiqua", 40));
        moneyText.setTextFill(Color.GOLD);
        moneyText.setLayoutX(70);
        moneyText.setLayoutY(getLayoutY() + 10);
        moneyText.textProperty().bind(Bindings.convert(Main.model.getCurrency()));
        money.getChildren().addAll(moneyImg, moneyText);

        //Create health
        Group health = new Group();
        ImageView healthImg = new ImageView(new Image("Sprites/heart_t_updated.png", Main.model.getTileWidth()*4,
                Main.model.getTileWidth()*4, true, true));
        Label healthText = new Label();
        healthText.setFont(new Font("Book Antiqua", 40));
        healthText.setTextFill(Color.RED);
        healthText.setLayoutX(70);
        healthText.setLayoutY(getLayoutY() + 10);
        healthText.textProperty().bind(Bindings.convert(Main.model.getPlayerHealth()));
        health.getChildren().addAll(healthImg, healthText);

        VBox money_health = new VBox(money, health);
        money_health.setMouseTransparent(true);

        //Next wave button
        PlayButton = new ImageView(new Image("Sprites/Next_wave.png", Main.model.getTileWidth()*4,
                Main.model.getTileWidth()*2, true, true));
        //set location
        PlayButton.setX(Main.viewWidth-PlayButton.getImage().getWidth()-50);
        PlayButton.setY(Main.viewHeight-PlayButton.getImage().getHeight()-50);

        //Here is all the Tower taskbar UI
        //Add new towers here
        HBox towerBar = new HBox();
        TowerIcon tower1 = new TowerIcon(10, "Arrow");
        TowerIcon tower2 = new TowerIcon(30, "Laser");

        //Add towers to model
        Main.model.towerIcons.add(tower1);
        Main.model.towerIcons.add(tower2);

        //Setting for towerBar
        towerBar.setSpacing(30);
        towerBar.setPickOnBounds(false);
        towerBar.getChildren().addAll(tower1, tower2);

        //set alignment
        towerBar.setLayoutY(Main.viewHeight-tower1.getFitHeight()-20);
        towerBar.setLayoutX(20);

        //Add all the children
        getChildren().addAll(money_health, PlayButton, towerBar);

        //Update disabled status on towerIcons
        Main.model.updateTowerIconStatus();
    }
}
