package app;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class Tile extends ImageView {

    public int state = 0;
    private final int gridRow, gridCol;
    private Tower tower = null;

    public Tile(int newState, int row, int col, Image i) {
        setImage(i);

        state = newState;
        gridRow = row;
        gridCol = col;
    }

    public int getGridRow() { return gridRow; }
    public int getGridCol() { return gridCol; }

    //Set tower to tile
    public void setTower(Tower t){
        double tileWidth = Main.model.getTileWidth();
        if(t != null) {
            state = 4;
            //set tower position
            t.setPosition(gridCol*tileWidth+tileWidth/2, gridRow*tileWidth+tileWidth/2);
            //t.setPosition(gridCol*tileWidth-(tileWidth*1.2-tileWidth)/2, gridRow*tileWidth-(tileWidth*1.2-tileWidth)/2);

        }
        else
            state=0;
        tower = t;
    }
}
