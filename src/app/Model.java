package app;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Model {
    private double tileWidth=0;
    private int tileRowCount=0, tileColCount=0;
    private final SimpleListProperty<Tile> tiles;
    private final SimpleListProperty<Enemy> enemies;
    private final SimpleListProperty<Tower> towers;
    public final ArrayList<TowerIcon> towerIcons;
    public final SimpleListProperty<Projectile> projectiles;
    private Map<String, ArrayList<Image>> enemyAnims;
    private final String[] enemyNames;
    private final SimpleIntegerProperty currency = new SimpleIntegerProperty(15);
    private final SimpleIntegerProperty playerHealth = new SimpleIntegerProperty(20);
    public static int[] map;
    public static int[] spawnTile, endTile;
    public double deltaTime;


    public Model(int rowCount, int colCount){
        //Initialize tileWidth and tiles per row
        this.tileWidth = Main.viewHeight / (double) rowCount;
        this.tileRowCount = rowCount;
        this.tileColCount = colCount;

        //Create list of tiles
        ArrayList<Tile> list = new ArrayList<>();
        ObservableList<Tile> observableList = FXCollections.observableArrayList(list);
        tiles = new SimpleListProperty<Tile>(observableList);

        //Create list of enemies
        ArrayList<Enemy> list2 = new ArrayList<>();
        ObservableList<Enemy> observableList2 = FXCollections.observableArrayList(list2);
        enemies = new SimpleListProperty<Enemy>(observableList2);

        //Create list of towers
        ArrayList<Tower> list3 = new ArrayList<>();
        ObservableList<Tower> observableList3 = FXCollections.observableArrayList(list3);
        towers = new SimpleListProperty<Tower>(observableList3);

        //Create list of towers
        ArrayList<Projectile> list4 = new ArrayList<>();
        ObservableList<Projectile> observableList4 = FXCollections.observableArrayList(list4);
        projectiles = new SimpleListProperty<Projectile>(observableList4);

        towerIcons = new ArrayList<TowerIcon>();

        //enemy names
        enemyNames = new String[]{"Goblin"};

        //Instantiate the mapConstruction
        constructMap(rowCount, colCount);

        //Preload animations
        GetAnimationsFromFile();
    }

    //Getter for tile width/height
    public double getTileWidth() { return tileWidth; }

    //Get the total number of tiles in a row
    public int getRowTileCount() { return tileRowCount; }
    //Get the total number of tiles in a column
    public int getColTileCount() { return tileColCount; }

    //Getter and setter for currency
    public SimpleIntegerProperty getCurrency() { return currency; }
    public void addCurrency(int reward) {
        currency.set(currency.get() + reward);
        updateTowerIconStatus();
    }

    //Getter and decrementer for player health
    public SimpleIntegerProperty getPlayerHealth(){ return playerHealth; }
    public void dealDamage(int damage){
        playerHealth.set(playerHealth.get() - damage);
        if(playerHealth.get() <= 0){
            //Game overs
        }
    }

    //Getter and setter for tileState list.
    //This is a 2D list used for storing tile status. 0 is free, 1 is occupied, 2 is path
    public void setTileState(int newState, Tile tile){ tile.state = newState; }

    //Get walk anim arraylist
    public ArrayList<Image> getWalkAnim(String name){ return enemyAnims.get(name); }
    //List of tiles
    public Tile getTile(int x, int y) { return tiles.get(x*y); }
    public SimpleListProperty<Tile> getTileList() { return tiles; }

    //Get enemies
    public SimpleListProperty<Enemy> getEnemyList() { return enemies; }

    //Get towers
    public SimpleListProperty<Tower> getTowerList() { return towers; }

    //Get projectiles
    public SimpleListProperty<Projectile> getProjectilesList() { return projectiles; }

    //Add new tile. Only used on startup
    public void addTile(Tile tile) { tiles.add(tile); }
    public Tile getTileFromPos(double x, double y){
        if(x < 0 || x > Main.viewWidth || y < 0 || y > Main.viewHeight)
            return null;
        return tiles.get((int)(y/getTileWidth()) * getColTileCount() + (int)(x/getTileWidth()));
    }

    //Construct the map. 3's are water, 2's are enemy path, 1's are dark green, 0's are light green,
    private void constructMap(int xTiles, int yTiles){
        map = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 0, 3, 0, 3, 0, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 3, 3, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 3, 0, 0, 2, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 2, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 0, 0, 2, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 2, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
                        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        //Hard code spawn tile and end tile
        spawnTile = new int[]{7, 0};
        endTile = new int[]{7,25};

        //Instantiate Tiles
        for (int i = 0; i < xTiles; i++){
            for(int j = 0; j < yTiles; j++){
                addTile(new Tile(map[i*yTiles + j], i, j, getImage(i, j, yTiles, map)));
            }
        }
    }

    //Retrieve image for tile based on number in map
    private Image getImage(int row, int col, int yTiles, int[] map) {
        int imageNum = map[row*yTiles+col];
        String url = "Sprites/";
        //Light green grass
        if (imageNum == 0) {
            url += "grass_tile_2.png";
        }
        //Darker border grass
        else if (imageNum == 1) {
            url += "grass_tile_1.png";
        }
        //Enemy Path tile
        else if (imageNum == 3) {
            url += "sand_tile.png";
        }
        //Water tiles (This doesn't work for every scenario unfortunately. May crash sometimes)
        else if (imageNum == 2) {
            String temp = "WaterTiles/";
            //Advanced Water Logic (assuming edges are not water)
            //If above is land
            temp += map[(row-1)*yTiles + col] != 2 ? "1" : "0";
            //If below is land
            temp += map[(row+1)*yTiles + col] != 2 ? "1" : "0";
            //If left is land
            temp += map[row*yTiles + col-1] != 2 ? "1" : "0";
            //If right is land
            temp += map[row*yTiles + col+1] != 2 ? "1" : "0";
            //If top-left is land
            temp += map[(row-1)*yTiles + col-1] != 2 ? "1" : "0";
            //If top-right is land
            temp += map[(row-1)*yTiles + col+1] != 2 ? "1" : "0";
            //If bottom-left is land
            temp += map[(row+1)*yTiles + col-1] != 2 ? "1" : "0";
            //If bottom-right is land
            temp += map[(row+1)*yTiles + col+1] != 2 ? "1" : "0";

            //Special case for all water
            if(temp.equals("WaterTiles/00000000"))
                temp += Integer.toString(ThreadLocalRandom.current().nextInt(0, 3));

            //Update url to image
            temp += ".png";
            url += temp;
        }

        return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(url)),
                tileWidth, tileWidth, false, false);
    }

    //Get animation sprites and add to list
    private void GetAnimationsFromFile(){
        ArrayList<Image> imgSet = new ArrayList<>();
        enemyAnims = new HashMap<>();

        for (String name : enemyNames){
            File[] files = new File("src/Sprites/" + name +"Walk").listFiles();
            for (File file: files) {
                if(!file.isDirectory())
                    imgSet.add(new Image("Sprites/" + name + "Walk/" + file.getName(), getTileWidth()*2.2,
                            getTileWidth(), true, true));
            }
            enemyAnims.put(name, imgSet);
        }
    }
    public void updateTowerIconStatus(){
        for(TowerIcon ti : towerIcons){
            //If get status is true, it's disabled
            if(currency.get() >= ti.getCost() && ti.getStatus()){
                ti.Enable();
            }
            else if(currency.get() < ti.getCost() && !ti.getStatus()){
                ti.Disable();
            }
        }
    }
}
