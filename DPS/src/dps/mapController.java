/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;

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
    
    
    /**
     * 
     * Uhhhhhhhh.... figure it out later, lmao
     * Will def need to create methods for editing map, generally using getters
     * and setters in the map class. Some other stuff, idk
     * 
     */
    
    /**
     * 
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
