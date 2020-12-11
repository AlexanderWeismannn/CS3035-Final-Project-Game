package app;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

public abstract class Enemy extends Pane {
    int damage;
    double health = 0;
    int reward=0;
    int imgIndex = 0;
    double frameTime = 0.13;
    double speed=100, originalSpeed=100;
    private int dx=0, dy=0;
    private int targetCol, targetRow;
    public Timeline timeline;
    private ImageView img;
    private Rectangle healthBar;
    private double healthScale = 0;

    public Enemy(int row, int col, double health){
        //Set initial position
        targetCol = col;
        targetRow = row;
        setPickOnBounds(true);

        setLayoutX((targetCol-1)*Main.model.getTileWidth());
        setLayoutY(targetRow*Main.model.getTileWidth());

        //Create healthbar
        this.health = health;
        healthScale = 35/health;
        healthBar = new Rectangle(10, -10, 35, 5);
        healthBar.setFill(Color.RED);
        healthBar.setPickOnBounds(true);
        healthBar.setMouseTransparent(true);
        //Set imageview
        img = new ImageView();
        getChildren().addAll(img, healthBar);
        //Find the path to follow
        FindPath();

    }

    //This function will update the enemy position and call findPath() if the target tile is reached
    //Return true if ready to be deleted
    public boolean Update(){
        double localWidth = Main.model.getTileWidth();
        setLayoutX(getLayoutX() + dx*speed*Main.model.deltaTime);
        setLayoutY(getLayoutY() + dy*speed*Main.model.deltaTime);

        //If we reached our target
        if(getLayoutX()*dx > (localWidth*targetCol)*dx){
            setLayoutX(localWidth*targetCol);
            FindPath();
        }
        else if(getLayoutY()*dy > (localWidth*targetRow)*dy){
            setLayoutY(localWidth*targetRow);
            FindPath();
        }
        //if we reached the end, deal damage and delete self
        if(getLayoutX() >= Model.endTile[1]*Main.model.getTileWidth()){
            DestroySelf(true);
            return true;
        }
        if(health <= 0) {
            DestroySelf(false);
            return true;
        }
        return false;
    }

    //The idea behind this method is the target tile we travel to is determined by finding
    //The longest straight path not travelled.
    private void FindPath(){
        //Store rowCount locally
        int tempColCount = Main.model.getColTileCount();

        //Check surroundings for new tile, excluding previous tile
        //and find the direction of travel
        if(Model.map[(targetRow-1)*tempColCount+targetCol] == 3 && dy != 1){
            dy = -1;
            dx = 0;
        }
        else if(Model.map[(targetRow+1)*tempColCount+targetCol] == 3 && dy != -1){
            dy = 1;
            dx = 0;
        }
        else if(Model.map[targetRow*tempColCount+targetCol-1] == 3 && dx != 1){
            dx = -1;
            dy = 0;
        }
        else if(Model.map[targetRow*tempColCount+targetCol+1] == 3 && dx != -1){
            dx = 1;
            dy = 0;
        }

        //Set target to end of path
        while(Model.map[(targetRow+dy)*tempColCount+(targetCol+dx)] == 3){
            targetCol += dx;
            targetRow += dy;
        }
        //If our target is the end tile, set target one tile past so the enemy goes off screen and dies
        if(targetCol+dx==Model.endTile[1] && targetRow+dy==Model.endTile[0]){
            targetRow+=dy;
            targetCol+=dx;
        }
    }
    public void WalkAnim_Init(String name){

        ArrayList<Image> imgSet = Main.model.getWalkAnim(name);

        /*
         * Create a timeline that executes every 0.13 seconds
         * calls an event handler to cycle through the arraylist
         * and replace the Goblins current image with the next
         * resetting back to pos(0) once it reaches the end
         */

        timeline = new Timeline(new KeyFrame(Duration.seconds(frameTime), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                img.setImage(imgSet.get(imgIndex));
                imgIndex++;
            }
        }));
        /*
         * Set the timeline to run forever
         * and play it.
         */
        timeline.setCycleCount(imgSet.size());
        timeline.setOnFinished(e -> {imgIndex = 0; timeline.playFromStart(); });
        timeline.play();
    }

    public void WalkAnim_Start(){
        timeline.play();
        speed = originalSpeed;
    }

    public void WalkAnim_Stop(){
        timeline.pause();
        speed = 0;
    }

    public void updateHealth(int damage){
        health -= damage;
        healthBar.setWidth(health*healthScale);
    }

    private void DestroySelf(boolean reachedEnd){
        if(reachedEnd)
            Main.model.dealDamage(damage);
        Main.model.addCurrency(reward);
    }
}
