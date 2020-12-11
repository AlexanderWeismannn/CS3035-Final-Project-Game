package app;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.Timer;


public class GameController {
    private int totalEnemiesSpawned=0;
    private double time=3.5;
    public enum State {READY, READY_TO_PLACE}
    private State state;
    private double lastTime = System.currentTimeMillis();
    private Timeline spawnTimer;

    public GameController() {
        Main.view.addEventHandler(MouseEvent.ANY, new MouseHandler());
        Main.view.addEventHandler(KeyEvent.ANY, new KeyHandler());
        state = State.READY;

        //Animation timer is a method which gets called every frame
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Loop through enemies and update positions. We use iterator to avoid ConcurrentModification
                Main.model.getEnemyList().removeIf(Enemy::Update);
                //Loop through projectiles and update positions. We use iterator to avoid ConcurrentModification
                Main.model.getProjectilesList().removeIf(Projectile::Update);

                //Loop through towers and attack if appropriate
                for (Tower t: Main.model.getTowerList()) {
                    if(t.selectedEnemy == null)
                        t.CheckForEnemy();
                }
                //We also want to update our deltaTime for smooth animations and movement at any frame rate
                Main.model.deltaTime = (System.currentTimeMillis()-lastTime)/1000; //in seconds
                lastTime = System.currentTimeMillis();
            }
        }.start();
    }

    public class MouseHandler implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent e) {
            if(InteractionModel.paused){
                //Handle menu clicks
            }
            else {
                switch (state) {
                    case READY:
                         if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                            if (e.getTarget().getClass() == ImageView.class) {
                                if (((ImageView) e.getTarget()).getParent().getClass().getSuperclass() == Tower.class) {
                                    Main.iModel.setSelectedTower((Tower) ((ImageView) e.getTarget()).getParent());

                                }
                                else if(((ImageView) e.getTarget()).getParent().getClass()== HBox.class){
                                    removeTower();

                                }
                                else if (((ImageView) e.getTarget()).getParent().getClass() == OverlayView.class) {
                                    Main.iModel.setSelectedTower(null);
                                    ((ImageView) e.getTarget()).setVisible(false);
                                    enemySpawner();
                                }
                                else{
                                    Main.iModel.setSelectedTower(null);
                                }
                            }
                            else{
                                Main.iModel.setSelectedTower(null);
                            }

                        }
                        else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                            if(e.getTarget().getClass() == TowerIcon.class){
                                TowerIcon ti = (TowerIcon) e.getTarget();
                                if(!ti.getStatus()){
                                    //Set overlay opacity and unclickable
                                    Main.view.ov.setOpacity(0.25);
                                    Main.view.ov.setMouseTransparent(true);
                                    //Change cursor
                                    Main.gameScene.setCursor(Cursor.CROSSHAIR);
                                    //Set state
                                    state = State.READY_TO_PLACE;
                                    //Set tower to place
                                    Main.iModel.setTowerToPlace(ti.towerType);
                                }
                            }
                        }

                        break;
                    case READY_TO_PLACE:
                        if(e.getEventType() == MouseEvent.MOUSE_RELEASED){
                            //Reset state
                            state = State.READY;
                            //Revert cursor
                            Main.gameScene.setCursor(Cursor.DEFAULT);
                            //Revert overlay opacity and clickable
                            Main.view.ov.setOpacity(1);
                            Main.view.ov.setMouseTransparent(false);

                            //Check if we should make the tower
                            if(Main.iModel.getHoverTile().get().state==0){
                                addTower(Main.iModel.getHoverTile().get(), Main.iModel.getTowerToPlace());
                            }

                            //Reset hover tile
                            Main.iModel.setHoverTile(null);
                            break;
                        }
                        //Draw rectangle on tile grid to change color
                        Main.iModel.setHoverTile(Main.model.getTileFromPos(e.getX(), e.getY()));
                        break;
                }
            }
        }
    }

    public class KeyHandler implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent e){
            if(e.getEventType() == KeyEvent.KEY_RELEASED){
                if(e.getCode() == KeyCode.ESCAPE){
                    if(!InteractionModel.paused){
                        PauseGame();
                    }
                    else{
                        ResumeGame();
                    }
                }
            }
        }
    }


    //This will spawn the enemies on an interval based on how many enemies are meant to spawn this wave
    //It will run a threaded loop which starts when play is clicked each wave.
    //The wave information is stored in the model
    private void enemySpawner(){
        spawnTimer = new Timeline(
                new KeyFrame(Duration.seconds(time),e->{
                    totalEnemiesSpawned++;
                    if(totalEnemiesSpawned > 60){
                        time = 0.6;
                        Main.model.getEnemyList().add(new Goblin(20, 120));
                        spawnTimer.stop();
                        spawnTimer.getKeyFrames().clear();
                        enemySpawner();
                    }
                    else if(totalEnemiesSpawned > 30){
                        time = 1;
                        Main.model.getEnemyList().add(new Goblin(12, 100));
                        spawnTimer.stop();
                        spawnTimer.getKeyFrames().clear();
                        enemySpawner();
                    }
                    else if(totalEnemiesSpawned > 10){
                        time = 2.3;
                        Main.model.getEnemyList().add(new Goblin(7, 80));
                        spawnTimer.stop();
                        spawnTimer.getKeyFrames().clear();
                        enemySpawner();
                    }
                    else{
                        Main.model.getEnemyList().add(new Goblin(5, 65));
                    }

                })
        );

        spawnTimer.setCycleCount(Timeline.INDEFINITE);
        spawnTimer.play();
    }

    private void addTower(Tile tile, Tower tower){
        tile.setTower(tower);
        Main.model.getTowerList().add(tower);
        Main.model.addCurrency(-1*tower.value);
        Main.model.updateTowerIconStatus();
    }

    private void removeTower(){
        Main.model.addCurrency((int) (Main.iModel.getSelectedTower().get().value*0.6));
        Main.model.getTowerList().remove(Main.iModel.getSelectedTower().get());
        Main.model.getTileFromPos(Main.iModel.getSelectedTower().get().centerX, Main.iModel.getSelectedTower().get().centerY).setTower(null);
        Main.iModel.setSelectedTower(null);
    }

    private void PauseGame(){
        InteractionModel.paused = !InteractionModel.paused;
        Main.iModel.setPauseScreen(InteractionModel.paused);
        //Pause all enemies
        for (Enemy e: Main.model.getEnemyList()){
            e.WalkAnim_Stop();
        }
        //Pause spawning
        spawnTimer.pause();
        //Pause all towers
        for(Tower t : Main.model.getTowerList()){
            t.attackTimer.pause();
        }

    }
    public void ResumeGame(){
        InteractionModel.paused = !InteractionModel.paused;
        Main.iModel.setPauseScreen(InteractionModel.paused);
        //Resume all enemy animations and movement
        for (Enemy e: Main.model.getEnemyList()){
            e.WalkAnim_Start();
        }
        //Continue spawning
        spawnTimer.play();
        //Resume all tower firing
        for(Tower t : Main.model.getTowerList()){
            t.attackTimer.play();
        }
        //Enable keyboard handling again
        Main.view.requestFocus();
    }

    public void returnToMenu(){
        Main.setScene(Main.mainMenu);
    }
}
