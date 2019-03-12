/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;

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
    
    
    //main method, maaay not actually need this, we'll see
    public static void main(String[] args) {
        
    }
    
    
    /**
     * Default constructor. Takes no args, outputs a super basic map.
     */
    public Map(){
        
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
        public Boolean isOccupied;
        
        /*
        This miiiiiight need some vars for x and y position, for use by other 
        methods. Simple enough to implement, just see if it's needed first.
        */
        
        
        //constuctor
        public tile(){
            
        }
        
        //toString method. Important for saving. Fix later
        @Override
        public String toString(){
            String tileString = "";
            
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
    
    
    //more getters and setters go here
    
    
    
    
    
    /**
     * The toString method. Real heckin important for saving. Should put things 
     * in a CSV format. FIGURE OUT GOOD ALGORITHM FOR THIS
     * @return the dungeon in a CSV format
     */
    @Override
    public String toString(){
        String dungeon = "";
        
        return dungeon;
    }
    
}
