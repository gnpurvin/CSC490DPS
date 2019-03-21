package dps;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class MainMenuController implements Initializable{

    @FXML
    public AnchorPane MainMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    //Opens DM Menu scene
    @FXML
    public void DMMenu(ActionEvent event) throws Exception{
        AnchorPane DM = FXMLLoader.load(getClass().getResource("DMMain.fxml"));
        MainMenu.getChildren().setAll(DM);
    }

    //Opens Player Menu scene
    @FXML
    public void PlayerMenu(ActionEvent event) throws Exception{
        AnchorPane Player = FXMLLoader.load(getClass().getResource("PCMain.fxml"));
        MainMenu.getChildren().setAll(Player);
    }
    @FXML
    public void Manual(ActionEvent event) throws IOException{
        //TODO
    }

    @FXML
    public void Quit(ActionEvent event) throws IOException{
        System.exit(0);
    }
}