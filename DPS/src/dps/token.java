/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;


import javafx.scene.paint.Color;


/**
 * This is the class that defines the tokens used to represent players, NPCs, 
 * and Creatures on the map. Used mostly by the Map Controller, and can be 
 * accessed by the DM and sometimes the players, depending on settings.
 * @author Phillip
 */
public class token {
    public int xPos;
    public int yPos;
    //public String owner;
    public String type; //maaaay not actually need, to be determined
    public int tokenID;
    public String tokenImage;
    public int tokenSizeX;
    public int tokenSizeY;
    public TokenMaster tokenMaster;
    public Color tokenColor = Color.DARKMAGENTA;
    
    
    /**
     * Overloaded constructor for making non-PC tokens.
     * @param tokenMaster
     * @param tokenID
     * @param xLoc
     * @param yLoc
     * @param type
     * @param xSize
     * @param ySize 
     * @param c 
     */
    public token(TokenMaster tokenMaster, int tokenID, int xLoc, int yLoc, String type, int xSize, int ySize, Color c){
        this.tokenMaster = tokenMaster;
        this.tokenID = tokenID;
        this.type = type;
        this.xPos = xLoc;
        this.yPos = yLoc;
        this.tokenSizeY = ySize;
        this.tokenSizeX = xSize;
        this.tokenColor = c;
        this.tellMaster();
        //create token in db with tokenInfo
    }
    
    /**
     * Default constructor for making PC tokens. Does require a few args, but they're easy to get.
     * @param tokenMaster
     * @param tokenID 
     * @param xLoc
     * @param yLoc
     * @param type 
     * @param c 
     */
    public token(TokenMaster tokenMaster, int tokenID, int xLoc, int yLoc, String type, Color c){
        this.tokenID = tokenID;
        this.yPos = yLoc;
        this.xPos = xLoc;
        this.type = type;
        this.tokenSizeX = 1; //or however we decide to set size for PC tokens
        this.tokenSizeY = 1; //same as above
        this.tokenColor = c;
        this.tellMaster();
        //create token in db with tokenInfo
        
    }
    
    public void updateLoc(){
        //fetch new vals from db
        //this.moveTo(db_newX, db_newY);
    }
    
    //for moving tokens.
    public void moveTo(int newX, int newY){
        this.xPos = newX;
        this.yPos = newY;
        
    }
    
    public void deleteToken(){
        //take off of GUI
        tokenMaster.removeTokenFromList(this);
        //rip from db
    }
    
    public String createTokenInfo(){
        String info = "";
        //concat what we need
        return info;
    }
    
    public int getTokenID(){
        return this.tokenID;
    }

    private void tellMaster() {
        tokenMaster.addTokenToList(this);
    }
    
    
}
