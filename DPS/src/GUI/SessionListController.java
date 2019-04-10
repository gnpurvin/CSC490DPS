/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.connector;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class SessionListController implements Initializable {

    @FXML
    public ChoiceBox Sessions;
    @FXML
    public Button Host;
    @FXML
    public AnchorPane SessionList;

    private String username;
    private String password;

    public SessionListController(String name,String pass){
        username = name;
        password = pass;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Sessions.getItems().clear();
        Sessions.getItems().addAll(connector.getSessionList(username));
        SessionList.setMaxSize(600, 400);
        SessionList.setMinSize(600, 400);
    }

    @FXML
    public void HostSession(ActionEvent event) throws Exception{
        FXMLLoader DM = new FXMLLoader(getClass().getResource("DMMainMenu.fxml"));
        DMMainController controller = new DMMainController(username, (String) Sessions.getValue(), password);
        DM.setController(controller);
        SessionList.getChildren().setAll((AnchorPane) DM.load());
    }
    
    @FXML
    public void NewSession(ActionEvent event) throws Exception{
        
    }


}
