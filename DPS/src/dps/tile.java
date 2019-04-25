package dps;

/***************
 * This is a class used mostly by the map class itself, sometimes 
 * accessed by the controller class. It defines what a tile is, and a map is
 * a collection of tiles arranged in a 2D Grid.
 * 
 * Each tile has to have a type and a few other properties. Stuff like what 
 * is there, if it's occupied, what cover it provides, etc. This can be 
 * changed.
 ****************/
public class tile {
        //property vars go here, figure out what that is later on
        public int prop;
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
            type = "";
            isRoom = false;
            isHall = false;
            isOccupied = false;
            isStairs = false;
            xPos = x;
            yPos = y;
        }
        
        public tile(int property, int x, int y){
            this.setProp(property);
            xPos = x;
            yPos = y;
        }
        /**
         * Custom overloaded constructor.
         * @param x
         * @param y
         * @param t
         * @param room
         * @param hall
         * @param occupied
         * @param stairs 
         */
        public tile(int x, int y, String t, String room, String hall, String occupied, String stairs){
            xPos = x;
            yPos = y;
            type = t;
            isRoom = Boolean.parseBoolean(room);
            isHall = Boolean.parseBoolean(hall);
            isOccupied = Boolean.parseBoolean(occupied);
            isStairs = Boolean.parseBoolean(stairs);
        }
        
        public void setIsRoom(Boolean b){
            this.isRoom = b;
        }
        
        public void setIsHall(Boolean b){
            this.isHall = b;
        }
        
        public void setIsOccupied(Boolean b){
            this.isOccupied = b;
        }
        
        public void setIsStairs(Boolean b){
            this.isStairs = b;
        }
        
        public Boolean getIsRoom(){
            return this.isRoom;
        }
        
        public Boolean getIsHall(){
            return this.isHall;
        }
        
        public Boolean getIsOccupied(){
            return this.isRoom;
        }
        
        public Boolean getIsStairs(){
            return this.isRoom;
        }
        
        public int getXCoord(){
            return this.xPos;
        }
        
        public int getYCoord(){
            return this.yPos;
        }
        
        //toString method. Important for saving. Fix later bc this is super gross
        @Override
        public String toString(){
            String tileString = "";            
            tileString = tileString.concat(Integer.toString(this.prop));
            return tileString;
        }

    public void setProp(int prop) {
        this.prop = prop;
        if(prop == 0){    
            this.isRoom = false;
            this.isHall = false;
            this.isOccupied = false;
            this.isStairs = false;
        }
        else if(prop == 1){
            this.isRoom = true;
            this.isHall = false;
            this.isOccupied = false;
            this.isStairs = false;
        }
        else if(prop == 2){
            this.isRoom = true;
            this.isHall = false;
            this.isOccupied = true;
            this.isStairs = false;
        }
    }
        
    }