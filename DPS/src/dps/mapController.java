/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

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
    StackPane mapping;
    Scene mapScene;
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
        mapping = new StackPane();
        mapScene = new Scene(mapping, 800, 800);
        
        
    }
    
    /**
     * 
     * Uhhhhhhhh.... figure it out later, lmao
     * Will def need to create methods for editing map, generally using getters
     * and setters in the map class. Some other stuff, idk
     * 
     * @param g
     */
    
    /**
     * This gon' be reaaal complicated.
     * hold on to ya butts
     */
    public void drawMap(){
        c  = new Canvas(600, 600);
        gc = c.getGraphicsContext2D();
       
        //iterates across each row
        for(int i = 0; i < currMap.sizeX; i++){
            
                //iterates down each column
            for(int j = 0; j < currMap.sizeY; j++){
                
                //if(this.currMap.getTileAt(i, j).isRoom == true){
                    //gc.setFill(roomColor);
                //}
                //else {
                    //gc.setFill(wallColor);
                //}
                
                gc.strokeRect((i*20), (j*20), 19, 19);
                gc.fillRect((i*20), (j*20), 19, 19);
                
                
                
            }
        }
    //presumably the to jpg/png method goes here
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
    public Map readFrom(){
        //fix later lmao
        Map loadedMap = new Map();
        //loadedMap.Grid
        return loadedMap;
    }
}
