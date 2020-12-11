package app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TowerIcon extends ImageView {
    private int cost;
    private boolean disabled = true;
    public String towerType;

    public TowerIcon(int cost, String type){
        towerType = type;
        this.cost = cost;
        setImage(new Image("Sprites/Towers/" + type + "TowerIcon.png"));
        setFitHeight(200);
        setPreserveRatio(true);

        //Set tower from type

    }

    public int getCost(){ return cost;}
    public boolean getStatus() { return disabled; }
    public void Enable(){ disabled = false; }
    public void Disable(){ disabled = true; }
}
