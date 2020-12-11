package app;

import javafx.beans.property.SimpleObjectProperty;

public class InteractionModel {
    public static boolean paused = false;
    private final SimpleObjectProperty<Tile> hoverTile = new SimpleObjectProperty<>(null);
    private String towerToPlace = null;
    private SimpleObjectProperty<Tower> selectedTower = new SimpleObjectProperty<>(null);

    public InteractionModel(){

    }
    public void setPauseScreen(boolean state){
        Main.view.pmv.setVisible(state);
        Main.view.pmv.toFront();
    }

    public SimpleObjectProperty<Tile> getHoverTile() { return hoverTile; }
    public void setHoverTile(Tile newTile) { hoverTile.set(newTile); }

    public void setTowerToPlace(String s){ towerToPlace = s; }
    public Tower getTowerToPlace(){
        if(towerToPlace.equals("Arrow")){
            return new ArrowTower();
        }
        else if(towerToPlace.equals("Laser")){
            return new LaserTower();
        }
        return new ArrowTower();
    }

    public void setSelectedTower(Tower st) { selectedTower.set(st); }
    public SimpleObjectProperty<Tower> getSelectedTower() { return selectedTower; }
}
