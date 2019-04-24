/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.MenuItem;
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
        FXMLLoader Edit = new FXMLLoader(getClass().getResource("CharacterCreation.fxml"));
        Parent root = Edit.load();
        CharacterCreationController con = Edit.getController();
        File[] files = new File("Characters\\").listFiles();
        int i=0; 
        String temp[] = new String[files.length];        
        for(File f : files){
            if(f.isFile()){
               temp[i] = f.getName();
                i++;
            }
        }
       
        ChoiceDialog dia = new ChoiceDialog((String)temp[0], temp);
        dia.showAndWait();
        con.editChar((String) dia.getSelectedItem());
        CharacterMain.getChildren().setAll(root);
    }

    @FXML
    public void Back(ActionEvent event) throws Exception{
        BackButton.getScene().getWindow().hide();
    }
}