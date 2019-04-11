/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;

import java.lang.Math;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * This is the map class, and it is used mostly to construct maps with specific 
 * parameters. Most editing is done by the controller class, which should 
 * generate an instance of this class to work with. 
 * @author Phillip
 */


public class Map {

    //Properties of a Map
    public int sizeX;
    public int sizeY;
    public int numRooms;
    public String setting;
    public String hallType;
    public String deadEnds;
    public tile[][] Grid;
    public int floorNum;
    public int mapID;
    public String mapName;
    public Random rng;
    
    
    //main method, maaay not actually need this, we'll see
    public static void main(String[] args) {
        System.out.println("Map instantiated");
    }
    
    
    /**
     * Default constructor. Takes no args, outputs a super basic map.
     */
    public Map(){
        sizeX = 20;
        sizeY = 20;
        numRooms = 2;
        setting = "Doesn't matter lmao";
        hallType = "Endless";
        deadEnds = "nah";
        Grid = new tile[sizeX][sizeY];
        floorNum = 1;
        mapID = 00000;
        mapName = "default";
    }
    
    /**
     * Overloaded constructor. Takes arguments passed to it by user input 
     * through a method in the controller class. This is the one used when the 
     * user picks options for the map before it is created.
     * 
     * Finish later!
     * @param width
     * @param length
     * @param rooms
     * @param environment
     * @param halls
     * @param deadEndsYN
     */
    public Map(int width, int length, int rooms, String environment, String halls, String deadEndsYN){
        //sets the map size
        sizeX = width;
        sizeY = length;
        setting = environment;
        hallType = halls;
        deadEnds = deadEndsYN;
        
        //fills the grid with blank tiles
        for(int i = 0; i < sizeY; i++){
            for(int j = 0; j < sizeX; j++){
                Grid[j][i] = new tile(j, i);
            }
        }
        
        //Here's where we place rooms. Maaay wanna switch this to a while loop
        //that increments each time a room is placed and exits either a) when 
        //all rooms are placed or b) it runs out of room to put them. Go fucken
        //HAM on the error checking to ensure it doesn't break something
        for (int k = 0; k < rooms; k++){
            placeRoom();
        }
    }
    
    
    /***************
     * This is a subclass used mostly by the map class itself, sometimes 
     * accessed by the controller class. It defines what a tile is, and a map is
     * a collection of tiles arranged in a 2D Grid.
     * 
     * Each tile has to have a type and a few other properties. Stuff like what 
     * is there, if it's occupied, what cover it provides, etc. This can be 
     * changed.
     ****************/
    public class tile {
        //property vars go here, figure out what that is later on
        public String type;
        public Boolean isRoom;
        public Boolean isHall;
        public Boolean isOccupied;
        public Boolean isStairs;
        public int xPos;
        public int yPos;
        
        /*
        This miiiiiight need some vars for x and y position, for use by other 
        methods. Simple enough to implement, just see if it's needed first.
        */
        
        
        //default constuctor
        public tile(int x, int y){
            type = "filled";
            isRoom = false;
            isHall = false;
            isOccupied = false;
            isStairs = false;
            xPos = x;
            yPos = y;
        }
        
        
        //toString method. Important for saving. Fix later bc this is super gross
        @Override
        public String toString(){
            String tileString = "";
            tileString.concat(this.type);
            tileString.concat((this.isRoom.toString()) + ",");
            tileString.concat((this.isHall.toString()) + ", ");
            tileString.concat((this.isOccupied.toString()) + ", ");
            tileString.concat((this.isStairs.toString()) + ", ");
            tileString.concat((Integer.toString(this.xPos)) + ", ");
            tileString.concat((Integer.toString(this.yPos)) + "\n");
            return tileString;
        }
        
    }
    
    /***********
     * A heccload of getters and setters. Like, just a shitload of getters and 
     * setters. More getters and setters than your body has room for. 
     * 
     * Everything happens so much
     ***********/
    
    //Getter and Setter for width (x value)
    public int getWidth(){
        return sizeX;
    }
    
    public void setWidth(int newX){
        sizeX = newX;
    }
    
    //Getter and Setter for length (y value)
    public int getLength(){
        return sizeY;
    }
    
    public void setLength(int newY){
        sizeY = newY;
    }
    
    public void setNumRooms(int n){
        numRooms = n;
    }
    
    public int getNumRooms(){
        return numRooms;
    }
    
    //more getters and setters go here
    
    /***************************
     * Tile Specific Getters and setters go here
     * v v v v v v v v v v v v v 
     ***************************/
    
    /**
     * Exactly what it sounds like
     * @return tile
     */
    public tile getTileAt(int x, int y){
        return Grid[x][y];
    }
    
    public String getType (tile t){
        return t.type;
    }
    
    public String getTileCoords(tile t){
        String tileCoords = "";
        //uhhhhhhhhhhhh
        return tileCoords;
    }
    
    //Fix this thingy
    public void setTileColor(tile t, Color c){
        System.out.println("Implement later");
    }
    
    public void setMapId(){
        this.mapID = rng.nextInt(10000);
    }
    
    /**
     * Method for randomly placing rooms on the map
     */
    public void placeRoom(){
        //The math here is gonna get real fucky, hang on to your butts folks
        int roomWidth = rng.nextInt(((sizeX)%2)+1);
        int roomLength = rng.nextInt(((sizeY)%2)+1);
        //These modulo 2 bits are what will get modified to affect room size.
        //Replace with vars at a later date. Maybe make them an arg.
        
        //These bits make it start fom the top left, for simplicity's sake. 
        //However, there's a chance this will affect the room placement. 
        //This could be changed to be the center coordinates.
        int coordX = rng.nextInt(sizeX);
        int coordY = rng.nextInt(sizeY);
        
        
        //jfc this code is janky as all fuck. pls halp me clean this up
        //Like, it works but. Ew. y'know?
        boolean free = true;
        for(int q = 0; q < roomWidth+2; q++){
            for(int w = 0; w < roomLength+2; w++){
                //thiiiis is gonna take some effort.
                if((coordX + q) > this.sizeX || (coordY + w) > this.sizeY){
                    break;
                }
                if(Grid[(coordX + q)][(coordY + w)].isRoom == true ||Grid[coordX + q][coordY + w].isHall == true){
                    free = false;
                    break;
                }
            }
        }
        //god forgive me I'm back on my bullshit
        if(free == true){
            for(int q = 0; q < roomWidth; q++){
                for(int w = 0; w < roomLength; w++){
                    Grid[coordX + q][coordY + w].isRoom = true;
                    Grid[coordX + q][coordY + w].type = "Room";
                }
            }
        }
        
    }
    
    
    /**
     * The toString method. Real heckin important for saving. Should put things 
     * in a CSV format. Constantly update algorithm to make sure it outputs /right/
     * @return the dungeon in a CSV format
     */
    @Override
    public String toString(){
        String dungeon = "";
        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                dungeon.concat(this.getTileAt(x, y).toString());
            }
        }
        return dungeon;
    }
    
}