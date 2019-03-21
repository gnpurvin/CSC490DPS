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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class CharacterMainController implements Initializable {

    @FXML
    public AnchorPane CharacterMain;
    @FXML
    public Button BackButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void NewCharacter(ActionEvent event) throws Exception{
        AnchorPane Create = FXMLLoader.load(getClass().getResource("CharacterCreation.fxml"));
        CharacterMain.getChildren().setAll(Create);
    }

    @FXML
    public void EditCharacter(ActionEvent event) throws Exception{

    }

    @FXML
    public void Back(ActionEvent event) throws Exception{
        BackButton.getScene().getWindow().hide();
    }
}
