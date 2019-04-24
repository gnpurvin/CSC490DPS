package dps;

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
            type = "";
            isRoom = false;
            isHall = false;
            isOccupied = false;
            isStairs = false;
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
            
            tileString.concat((Integer.toString(this.xPos)) + ", ");
            tileString.concat((Integer.toString(this.yPos)) + ", ");
            tileString.concat(type + ", ");
            tileString.concat((this.isRoom.toString()) + ", ");
            tileString.concat((this.isHall.toString()) + ", ");
            tileString.concat((this.isOccupied.toString()) + ", ");
            tileString.concat((this.isStairs.toString()) + "\n");
            return tileString;
        }
        
    }