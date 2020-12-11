package app;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.awt.geom.Point2D;

public abstract class Tower extends Pane {
    int value;
    public int damage;
    public Circle attackCircle;
    private double radius;
    public Enemy selectedEnemy = null;
    public double centerX, centerY;
    public Timeline attackTimer;
    private double rateOfFire = 0;
    private String type;
    public HBox menu = new HBox();

    Tower(int val, String type, double radius, int damage, double rof) {
        setPickOnBounds(false);

        //Some vars
        this.damage = damage;
        this.radius = radius;
        rateOfFire = rof;
        value = val;
        this.type = type;

        //Set image
        ImageView iv = new ImageView(new Image("Sprites/Towers/" + type + "Tower.png", Main.model.getTileWidth()*1.2,
                Main.model.getTileWidth()*1.2, true, true));
        iv.setLayoutX(-Main.model.getTileWidth()*0.6);
        iv.setLayoutY(-Main.model.getTileWidth()*0.6);

        //Attack radius
        attackCircle = new Circle(radius, Color.TRANSPARENT);
        attackCircle.setMouseTransparent(true);

        //Tower menu
        ImageView sellButton = new ImageView(new Image("Sprites/Towers/sell.png",50,50, true,true));
        menu.setLayoutX(-25);
        menu.setLayoutY(15);
        menu.getChildren().add(sellButton);
        menu.setPickOnBounds(false);
        menu.setVisible(false);

        getChildren().addAll(attackCircle, iv, menu);

        //Attack timeline
        attackTimeline();
    }

    public void CheckForEnemy(){
        //First we find the closest enemy
        double minDist = Integer.MAX_VALUE;
        double temp;
        Enemy select = null;
        for(Enemy e: Main.model.getEnemyList()){
            temp = Point2D.distance(centerX, centerY, e.getLayoutX(), e.getLayoutY());
            if(temp < minDist){
                minDist = temp;
                select = e;
            }
        }
        //Now that we've found it, if its not null we check for intersect with circle
        if(select != null){
            Bounds bounds = localToParent(attackCircle.getLayoutBounds());
            if(bounds.intersects(select.getBoundsInParent())){
                selectedEnemy = select;
                attackTimer.playFromStart();
            }
        }

    }
    private void Attack(){
        //Check if enemy left radius
        Bounds bounds = localToParent(attackCircle.getLayoutBounds());
        if(!bounds.intersects(selectedEnemy.getBoundsInParent())){
            selectedEnemy = null;
            return;
        }
        //If enemy died
        if(selectedEnemy.health <= 0){
            selectedEnemy = null;
            return;
        }

        //Attack
        Main.model.getProjectilesList().add(new Projectile(centerX, centerY, selectedEnemy, damage, type));

    }
    public void setPosition(double x, double y){
        setLayoutX(x);
        setLayoutY(y);
        centerX = x;
        centerY = y;
    }

    private void attackTimeline() {
        attackTimer = new Timeline(
            new KeyFrame(Duration.ZERO, e->{
                if(selectedEnemy != null) {
                    Attack();
                }
            }),
            new KeyFrame(Duration.seconds(rateOfFire),e->{

            })
        );

        attackTimer.setCycleCount(Timeline.INDEFINITE);
    }

}
