/*
 * Part of the DPS app created by Ray Cockerham, Mei Lin Dong, Spencer Whyatt, 
 * Greg Purvine, and Phillip Jones.
 * Built for Senior Capstone.
 */
package dps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * This class handles most of the editing and such for the map class. It will 
 * take user input and change the map accordingly, as well as handle any "behind
 * the scenes" changes that need to be made and work that needs to be done.
 * 
 * Update: this will need to handle rendering the thing and converting it to a 
 * flat png or jpg.
 * @author Phillip
 */
public class mapController extends JPanel {
    public Map currMap;
    public Graphics g;
    public Graphics2D graphics2 = (Graphics2D) g;
    private Color roomColor;
    
    public static void main(String[] args) {
        
    }
    
    
    public mapController(){
        currMap = new Map();
        graphics2 = (Graphics2D) g;
        roomColor = Color.GRAY;
    }
    
    /**
     * 
     * Uhhhhhhhh.... figure it out later, lmao
     * Will def need to create methods for editing map, generally using getters
     * and setters in the map class. Some other stuff, idk
     * 
     * @param g
     */
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.drawMap(g2);
    }
    
    /**
     * This gon' be reaaal complicated.
     * hold on to ya butts
     * @param g2
     */
    public void drawMap(Graphics2D g2){
        
        //iterates across each row
        for(int i = 0; i < currMap.sizeX; i++){
            
            //iterates down each column
            for(int j = 0; j < currMap.sizeY; j++){
                Rectangle2D.Double currentRect = new Rectangle2D.Double((i*20), (j*20), 20, 20);
                g2.draw(currentRect);
                g2.setPaint(roomColor);
                g2.fill(currentRect);
                currMap.getTileAt(i, j).setAssocRect(currentRect);
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
