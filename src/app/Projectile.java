package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Projectile extends ImageView {
    public Enemy target;
    private int damage;
    private int speed = 700;


    public Projectile(double startX, double startY, Enemy enemy, int damage, String type){
        target = enemy;
        this.damage = damage;
        //Rotation
        double angle=Math.atan2(target.getLayoutY()-getY(), target.getLayoutX()-getX());
        setRotate((angle*180/Math.PI)+90);
        //Set image
        setImage(new Image("Sprites/Towers/"+type+".png", 70, 70, true, true));
        setX(startX-getImage().getWidth()/2);
        setY(startY-getImage().getHeight()/2);

    }
    public boolean Update(){
        //Update position
        double angle=Math.atan2(target.getLayoutY()-getY(), target.getLayoutX()-getX());
        setX(getX() + Main.model.deltaTime*speed*Math.cos(angle));
        setY(getY() + Main.model.deltaTime*speed*Math.sin(angle));
        //Set rotation
        setRotate((angle*180/Math.PI)+90);
        //If intersecting with target, destroy and deal damage
        if(getBoundsInParent().intersects(target.getBoundsInParent())){
            target.updateHealth(damage);
            return true;
        }
        return false;
    }
}