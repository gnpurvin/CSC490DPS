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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class PCMainController implements Initializable {

    @FXML
    public AnchorPane PCMain;
    @FXML
    public Button CreateChar;
    @FXML
    public Button Join;
    
    private String username;
    private String session;
    private String password;
    
    public PCMainController(String name, String ses, String pass){
        username = name;
        session = ses;
        password = pass;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PCMain.setMaxSize(600, 400);
        PCMain.setMinSize(600, 400);
    }

    @FXML
    public void CharacterCreate(ActionEvent event) throws Exception{
       FXMLLoader Create = new FXMLLoader(getClass().getResource("CharacterMain.fxml"));
       Stage stage = new Stage();
       stage.initOwner(CreateChar.getScene().getWindow());
       stage.setScene(new Scene((Parent) Create.load()));
       stage.setMaximized(true);
       stage.show();

       CharacterMainController controller = Create.getController();

    }

    @FXML
    public void JoinSession(ActionEvent event) throws Exception{
        FXMLLoader Session = new FXMLLoader(getClass().getResource("PlayerSession.fxml"));
        PlayerSessionController controller = new PlayerSessionController(username, session);
        Session.setController(controller);
        PCMain.getChildren().setAll((AnchorPane) Session.load());
    }

    @FXML
    public void MainMenu(ActionEvent event) throws Exception{
        FXMLLoader Main =  new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        MainMenuController controller = new MainMenuController(username, password);
        Main.setController(controller);
        connector.closeCon();
        PCMain.getChildren().setAll((AnchorPane) Main.load());
    }
}
