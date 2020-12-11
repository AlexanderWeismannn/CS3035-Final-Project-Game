package app;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;


public class Goblin extends Enemy {
    public Goblin(int health, double speed){
        super(Model.spawnTile[0], Model.spawnTile[1], health);
        //StartAnim
        WalkAnim_Init("Goblin");
        //Goblin stuff
        damage = 1;
        reward = 2;
        speed = originalSpeed = speed;
    }
}
