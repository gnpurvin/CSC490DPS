/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dps;

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
public class DMMainController implements Initializable {

    @FXML
    public AnchorPane DMMain;
    @FXML
    public Button CreateChar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void SessionList(ActionEvent event) throws Exception{
        //TODO
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
    public void HostSession(ActionEvent event) throws Exception{
        //TODO
    }

    @FXML
    public void MainMenu(ActionEvent event) throws Exception{
        AnchorPane Main = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        DMMain.getChildren().setAll(Main);
    }
}
