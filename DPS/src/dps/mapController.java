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
     * @return 
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
    
    public void moveTokenTo(token t, int x, int y){
        t.moveTo(x, y);
        currMap.Grid[x][y].isOccupied = true;
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
    
    /**
     * 
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
    public Map readFrom(connector c){
        //yoink vals from db, pass them to vars, instantiate map using those vars as args
        String fullMapString = c.getMapValues(currMap.mapID);
        
        Map loadedMap = new Map();
        //loadedMap.Grid
        return loadedMap;
    }
    
    //saves the map to the database
    public void saveMap(connector c){
        c.makeMap(currMap.toString(), currMap.mapID);
    }
}
