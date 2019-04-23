/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.connector;
import dps.Map;
import dps.mapController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class MapMakerController implements Initializable {

    @FXML
    public Button SaveBtn;
    @FXML
    public Button ExitBtn;
    @FXML
    public Button DrawRandMapButton;
    @FXML
    public Canvas MapCanvas;
    
    private String username;
    private int session;
    private Map newMap;
    private mapController control;
    /**
     * Initializes the controller class.
     */
    
    public MapMakerController(String name, int ses){
        username = name;
        session = ses;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    public void Save(ActionEvent e){
        if(newMap != null){
            connector.makeMap(newMap.toString(), session);
        }    
    }
    
    @FXML
    public void Exit(ActionEvent e){
        ExitBtn.getScene().getWindow().hide();
    }
    
    @FXML
    public void DrawMap(){
        newMap = new Map();
        control = new mapController();
        control.currMap = newMap;
        control.drawMap(MapCanvas);
        
    }
}
