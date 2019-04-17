/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;

import database.connector;
import java.sql.Connection;

/**
 * This is the class that defines the tokens used to represent players, NPCs,
 * and Creatures on the map. Used mostly by the Map Controller, and can be
 * accessed by the DM and sometimes the players, depending on settings.
 *
 * @author Phillip
 */
public class Token {

    public String tokenValues;
    public int coordX;
    public int coordY;
    public int sizeX;
    public int sizeY;
    private String username;
    private int mapID;
    private int tokenID;
    Connection con = connector.connect();

    public TokenMaster tokenMaster;

    //Token constructor for user specified size of token
    public Token(int coordX, int coordY, int sizeX, int sizeY, String username, int mapID) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.sizeX = coordX;
        this.sizeY = coordY;
        tokenValues = "" + coordX + ", " + coordY + ", " + sizeX + ", " + sizeY + "";
        tokenID = connector.makeToken(username, mapID, tokenValues);
    }

    //Token constructor for default player token
    public Token(int coordX, int coordY, String username, int mapID) {
        this.coordX = coordX;
        this.coordY = coordY;
        sizeX = 1;
        sizeY = 1;
        tokenValues = "" + coordX + ", " + coordY + ", " + sizeX + ", " + sizeY + "";
        tokenID = connector.makeToken(username, mapID, tokenValues);
    }

    //setTokenCoord changes the coordinates of the token on the database
    public void setTokenCoord(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        tokenValues = "" + coordX + ", " + coordY + ", " + sizeX + ", " + sizeY + "";
        connector.editTokenValues(tokenID, tokenValues);
    }

    //getTokenCoord gets the updates token coordinates from the database
    public void getTokenCoord() {
        tokenValues = connector.getTokenValues(tokenID);
        String[] val = tokenValues.split(", ");
        this.coordX = Integer.parseInt(val[0]);
        this.coordY = Integer.parseInt(val[1]);
    }

    //deletes a token from the database
    public void deleteToken() {
        connector.deleteToken(tokenID);
    }
}
