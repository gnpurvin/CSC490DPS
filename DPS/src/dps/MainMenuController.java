package dps;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

public class MainMenuController{
    
    @FXML
    private VBox MainMenu;
    
    @FXML
    private void loadCharacterCreate(ActionEvent event) throws IOException{
        VBox CharacterCreation = FXMLLoader.load(getClass().getResource("CharacterCreation.fxml")); //loads new FXML file
        MainMenu.getChildren().setAll(CharacterCreation); //shows FXML file
    }
}