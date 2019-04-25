/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;
import java.util.ArrayList;
import java.util.List;
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
    public String mapName;
    public Random rng;
    public List<token> tokenList;
    
    
    //main method, maaay not actually need this, we'll see
    public static void main(String[] args) {
        System.out.println("Map instantiated");
    }
    
    
    /**
     * Default constructor. Takes no args, outputs a super basic map.
     */
    public Map(){
        sizeX = 40;
        sizeY = 40;
        numRooms = 5;
        setting = "Doesn't matter lmao";
        hallType = "Endless";
        deadEnds = "nah";
        Grid = new tile[sizeX][sizeY];
        //fills the grid with blank tiles
        for(int i = 0; i < sizeX; i++){
            for(int j = 0; j < sizeY; j++){
                Grid[i][j] = new tile(i, j);
            }
        }
        floorNum = 1;
        mapName = "default";
        System.out.println(this.toString());
        tokenList = new ArrayList<>();
    }
    
    /**
     * Overloaded constructor. Takes arguments passed to it by user input 
     * through a method in the controller class. This is the one used when the 
     * user picks options for the map before it is created.
     * 
     * Finish later!
     * @param loaded
     * @param name
     * @param id
     * @param width
     * @param length
     * @param rooms
     * @param environment
     * @param halls
     * @param deadEndsYN
     */
    public Map(Boolean loaded, String name, int width, int length, int rooms, String environment, String halls, String deadEndsYN){
        mapName = name;
        sizeX = width;
        sizeY = length;
        numRooms = rooms;
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
        if(loaded == false){
            for (int k = 0; k < rooms; k++){
                placeRoom();
            }
        }
    }
    
    
    
    /***********
     * A heccload of getters and setters. Like, just a shitload of getters and 
     * setters. More getters and setters than your body has room for. 
     * 
     * Everything happens so much
     * @return 
     ***********/
    
    public String getMapName(){
        return this.mapName;
    }
    
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
    
    //Getter and Setter for number of Rooms. This should be useful for GUI stuff.
    public void setNumRooms(int n){
        numRooms = n;
    }
    
    public int getNumRooms(){
        return numRooms;
    }
    
    
    /***************************
     * Tile Specific Getters and setters go here
     * v v v v v v v v v v v v v 
     ***************************/
    
    /**
     * Exactly what it sounds like
     * @param x
     * @param y
     * @return tile
     */
    public tile getTileAt(int x, int y){
        return Grid[x][y];
    }
    
    //Outputs whatever the tile's type is. Kinda useless right now, implement stuff later
    public String getType (tile t){
        return t.type;
    }
    
    //Given a tile, outputs the coordinates. Might want to use this for GUI stuff
    public String getTileCoords(tile t){
        String tileCoords = Integer.toString(t.xPos) + ", " + Integer.toString(t.yPos);
        return tileCoords;
    }
    
    //Names the map. Exactly what it says
    public void setMapName(String name){
        this.mapName = name;
    }
    
    /**
     * Method for randomly placing rooms on the map. 
     * This shit is gonna get complex.
     */
    public void placeRoom(){
        //The math here is gonna get real fucky, hang on to your butts folks
        this.rng = new Random();
        
        int roomWidth = (rng.nextInt(10)+3);
        int roomLength = (rng.nextInt(10)+3);
        int coordX = (rng.nextInt((sizeX - roomWidth)-1));
        int coordY = (rng.nextInt((sizeY - roomLength)-1));
        
        //jfc this code is janky as all fuck. pls halp me clean this up
        //Like, it works but. Ew. y'know?
        
        boolean free = true;
        //Check if it's within the bounds of the array
        if((coordX + roomWidth + 2) < (sizeX - coordX) || (coordY + roomLength + 2) < (sizeY - coordY) ){
           //cycles through each tile in the area to check if it's already a room
            for(int i = 0; i < roomWidth; i++){
                for(int j = 0; j < roomLength; j++){
                    //System.out.println((coordX + i) + ", " + (coordY + j));
                    if(Grid[coordX + i][coordY + j].isRoom == true || Grid[coordX + i][coordY + j].isHall == true){
                        free = false;
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
                System.out.println("Placed a room!");
            }
            else if(free == false){
                placeRoom();
            }
        }
        else {
            placeRoom();
        }
           
    }
    
    //Make hallways do the thing. Figure out algorithm
    public void placeHalls(){
        //uhhhh
    }
    
    
    /**
     * The toString method. Real heckin important for saving. Should put things 
     * in a CSV format. Constantly update algorithm to make sure it outputs /right/
     * @return the dungeon in a CSV format
     */
    @Override
    public final String toString(){
        String dungeon = "";
        dungeon = dungeon.concat(mapName + ", ");
        dungeon = dungeon.concat(Integer.toString(sizeX) + ", ");
        dungeon = dungeon.concat(Integer.toString(sizeY) + ", ");
        dungeon = dungeon.concat(Integer.toString(numRooms));
        dungeon = dungeon.concat(setting + ", ");
        dungeon = dungeon.concat(hallType + ", ");
        dungeon = dungeon.concat(deadEnds.toString() + "\n");
        for(int x = 0; x < sizeX; x++){
            for(int y = 0; y < sizeY; y++){
                dungeon = dungeon.concat(Integer.toString(sizeX));
                dungeon = dungeon.concat(Integer.toString(sizeY));
                dungeon = dungeon.concat(this.getTileAt(x, y).toString());
            }
        }
        return dungeon;
    }
    
}
