package app;

import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.Objects;

public class View extends Pane {
    public OverlayView ov;
    public PauseMenuView pmv;
    private GraphicsContext gc;

    public View() {
        ov = new OverlayView();
        pmv = new PauseMenuView();
        //Since we're making a grid, it makes the most sense to use a gridpane
        //The reason I don't extend a gridpane is for the overlay
        GridPane root = new GridPane();
        //Add all the tiles to the root
        for (Tile t : Main.model.getTileList()) {
            root.add(t, t.getGridCol(), t.getGridRow());
        }
        getChildren().add(root);
        getChildren().add(ov);
        getChildren().add(pmv);

        //Canvas for drawing hover tiles
        Canvas canvas = new Canvas(Main.viewWidth, Main.viewHeight);
        canvas.setMouseTransparent(true);
        gc = canvas.getGraphicsContext2D();
        getChildren().add(canvas);

        //Listener for enemies
        Main.model.getEnemyList().addListener((ListChangeListener<Enemy>) c -> {
            while (c.next()) {
                for (Enemy e : c.getRemoved())
                    getChildren().remove(e);

                for (Enemy e : c.getAddedSubList()) {
                    getChildren().add(e);
                }
            }
        });

        //Listener for towers
        Main.model.getTowerList().addListener((ListChangeListener<Tower>) c -> {
            while (c.next()) {
                for (Tower t : c.getRemoved())
                    getChildren().remove(t);

                for (Tower t : c.getAddedSubList()) {
                    getChildren().add(t);
                }
            }
        });

        //Listener for projectiles
        Main.model.getProjectilesList().addListener((ListChangeListener<Projectile>) c -> {
            while (c.next()) {
                for (Projectile p : c.getRemoved())
                    getChildren().remove(p);

                for (Projectile p : c.getAddedSubList()) {
                    getChildren().add(p);
                }
            }
        });

        //Listen for selected Tower
        Main.iModel.getSelectedTower().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                selectTower(newValue);
            }
            if(oldValue != null) {
                deselectTower(oldValue);
            }
        });

        //Listener for hovering over tile
        Main.iModel.getHoverTile().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                selectTile(newValue);
            }
            if(oldValue != null) {
                deselectTile(oldValue);
            }
        });
    }

    //Draw tile highlight
    private void selectTile(Tile t){
        double tileWidth = Main.model.getTileWidth();
        //If the tile is free draw green
        if(t.state == 0){
            gc.setFill(new Color(0,1,0,0.6));
            gc.fillRect(t.getGridCol()*tileWidth, t.getGridRow()*tileWidth, tileWidth, tileWidth);
        }
        //Else draw red
        else{
            gc.setFill(new Color(1,0,0,0.6));
            gc.fillRect(t.getGridCol()*tileWidth, t.getGridRow()*tileWidth, tileWidth, tileWidth);
        }


    }

    //Clear the highlight
    private void deselectTile(Tile t){
        double tileWidth = Main.model.getTileWidth();
        gc.clearRect(t.getGridCol()*tileWidth, t.getGridRow()*tileWidth, tileWidth, tileWidth);
    }

    private void selectTower(Tower t){
        t.attackCircle.setFill(new Color(0,0.6,1,0.4));
        t.menu.setVisible(true);
    }
    private void deselectTower(Tower t){
        t.attackCircle.setFill(Color.TRANSPARENT);
        t.menu.setVisible(false);
    }
}
