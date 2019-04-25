/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;

import database.connector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * This class handles most of the editing and such for the map class. It will 
 * take user input and change the map accordingly, as well as handle any "behind
 * the scenes" changes that need to be made and work that needs to be done.
 * 
 * Update: this will need to handle rendering the thing and converting it to a 
 * flat png or jpg.
 * @author Phillip
 */
public class mapController {
    public Map currMap;
    private final Color roomColor;
    private final Color wallColor;
    public Canvas c;
    public GraphicsContext gc;
    public TokenMaster tm;
    private enum tileEnum{
        WALL, FLOOROPEN, FLOORTAKEN
    };
    
    
    //Default Main
    public static void main(String[] args) {
        System.out.println("Got called successfully");
    }
    
    /**
     * Basic default constructor. Initializes some fields. Might need changing
     * later
     */
    public mapController(){
        currMap = new Map();
        roomColor = Color.WHITE;
        wallColor = Color.BLACK;
        
    }
    
    /**
     * Iterates through each tile in the 2D array and draws it. 
     * This gon' be reaaal complicated.
     * hold on to ya butts
     * @param c
     */
    public void drawMap(Canvas c){
        gc = c.getGraphicsContext2D();
        for(int v = 0; v < currMap.numRooms; v++){
            currMap.placeRoom();
        }

        System.out.println("Placed rooms");
        
        //iterates across each row
        for(int i = 0; i < currMap.sizeX; i++){
            
                //iterates down each column
            for(int j = 0; j < currMap.sizeY; j++){
                
                if(this.currMap.getTileAt(i, j).getIsRoom() == true){
                    gc.setFill(roomColor);
                }
                else {
                    gc.setFill(wallColor);
                }
                
                gc.strokeRect((i*20), (j*20), 19, 19);
                gc.fillRect((i*20), (j*20), 19, 19);
                
            }
        }
    
    }
    public void drawMap2(Canvas c){
        gc = c.getGraphicsContext2D();
        
        //iterates across each row
        for(int i = 0; i < currMap.sizeX; i++){
            
                //iterates down each column
            for(int j = 0; j < currMap.sizeY; j++){
                
                if(this.currMap.getTileAt(i, j).getIsRoom() == true){
                    gc.setFill(roomColor);
                }
                else {
                    gc.setFill(wallColor);
                }
                
                gc.strokeRect((i*20), (j*20), 19, 19);
                gc.fillRect((i*20), (j*20), 19, 19);
                
            }
        }
    
    }
    
    ////////////////////////////////////////
    /// Section for 
    /// Token manipulation methods 
    ///////////////////////////////////////
    
    
    /**
     * Makes a token!
     * @param x
     * @param y
     * @param type
     * @return 
     */
    public token createToken(int x, int y, String type){
        int id = (currMap.tokenList.size() + 1);
        token t = new token(tm, id, x, y, type, Color.DARKORANGE);
        this.drawTokens();
        return t;
    }
    
    /**
     * 
     * @param t
     * @param x
     * @param y 
     */
    public void moveTokenTo(token t, int x, int y){
        t.moveTo(x, y);
        currMap.Grid[x][y].isOccupied = true;
        currMap.Grid[x][y].prop = 2;
        this.drawTokens();
    }
    
    
    /**
     * This method just iterates through the list of tokens and draws them at 
     * the position specified by their X and Y coordinates in the color specified
     * by their color variable. Call this whenever you want to update a 
     * token's position or color, etc.
     */
    public void drawTokens(){
        gc = c.getGraphicsContext2D();
        for(int i = 0; i < currMap.tokenList.size(); i++){
            gc.setFill(currMap.tokenList.get(i).tokenColor);
            gc.fillOval((currMap.tokenList.get(i).xPos*20), (currMap.tokenList.get(i).yPos*20), currMap.tokenList.get(i).tokenSizeX, currMap.tokenList.get(i).tokenSizeY);
        }
    }
    
    
    //////End of token Section////////
    
    
    /**
     * Checks if the tile at specified x and y is occupied.
     * @param x
     * @param y
     * @return 
     */
    public Boolean checkIfOccupied(int x, int y){
        //if false, it's not occupied and you can move there.
        //if true, it's occupied and you can't move there.
        return currMap.getTileAt(x, y).isOccupied;
    }
    
    //takes various args from csv file, puts them into the constructor, Map 
    //class builds it, then returns it as a Map object exactly the same as it 
    //saved.
    public Map readFrom(int mapID){
        //yoink vals from db, pass them to vars, instantiate map using those vars as args
        String fullMapString = connector.getMapValues(mapID);
        System.out.println(fullMapString);
        String mapArray[] = fullMapString.split("-");
        
        /*
        * This is the part where we pull map fields from the string and 
        * instantiate a new map object with them. 
        */
        String mapFields[] = mapArray[0].split(",");
        Boolean loaded = true;
        String name = mapFields[0];
        int sizeX = Integer.parseInt(mapFields[1]);
        int sizeY = Integer.parseInt(mapFields[2]);
        int rooms = Integer.parseInt(mapFields[3]);
        String setting = mapFields[4];
        String halls = mapFields[5];
        String deadEnds = mapFields[6];
        
        
        Map loadedMap = new Map(loaded, name, sizeX, sizeY, rooms, setting, halls, deadEnds);
        
        
        //This part separates the map fields from the tile data
        String tileArray[] = new String[mapArray.length - 1];
        System.arraycopy(mapArray, 1, tileArray, 0, (mapArray.length - 1));
        
        for(int i = 0; i < tileArray.length; i++){
            String tile[] = tileArray[i].split(",");
                for (int z = 0; z < tile.length; z++){
                    loadedMap.Grid[i][z] = new tile(Integer.parseInt(tile[z]), i, z);
                }
        }
        
        
        //loadedMap.Grid
        return loadedMap;
    }
    
    public Map loadMap(String s){
        String mapArray[] = s.split("-");
        
        /*
        * This is the part where we pull map fields from the string and 
        * instantiate a new map object with them. 
        */
        String mapFields[] = mapArray[0].split(",");
        Boolean loaded = true;
        String name = mapFields[0];
        int sizeX = Integer.parseInt(mapFields[1]);
        int sizeY = Integer.parseInt(mapFields[2]);
        int rooms = Integer.parseInt(mapFields[3]);
        String setting = mapFields[4];
        String halls = mapFields[5];
        String deadEnds = mapFields[6];
        
        
        Map loadedMap = new Map(loaded, name, sizeX, sizeY, rooms, setting, halls, deadEnds);
        
        
        //This part separates the map fields from the tile data
        String tileArray[] = new String[mapArray.length - 1];
        System.arraycopy(mapArray, 1, tileArray, 0, (mapArray.length - 1));
        
        for(int i = 0; i < tileArray.length - 1; i++){
            String tile[] = tileArray[i].split(",");
                for (int z = 0; z < tile.length; z++){
                    System.out.println(i + " " + z);
                    loadedMap.Grid[i][z] = new tile(Integer.parseInt(tile[z]), i, z);
                }
        }
        
        
        //loadedMap.Grid
        return loadedMap;
    }
    
    //saves the map to the database
    public void saveMap(int ses){
        connector.makeMap(currMap.toString(), ses);
    }
}
