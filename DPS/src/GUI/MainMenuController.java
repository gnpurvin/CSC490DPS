package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainMenuController implements Initializable{

    @FXML
    public AnchorPane MainMenu;
    @FXML
    public Button Create;
    @FXML
    public TextField Sessioncode;
    @FXML
    public Label Error;
    
    private String username;
    private String password;
    
    public MainMenuController(String name, String pass){
        username = name;
        password = pass;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainMenu.setMaxSize(600, 400);
        MainMenu.setMinSize(600, 400);
    }

    //Opens Session lists scene
    @FXML
    public void DMMenu(ActionEvent event) throws Exception{
        FXMLLoader SessionList = new FXMLLoader(getClass().getResource("SessionList.fxml"));
        SessionListController controller = new SessionListController(username, password);
        SessionList.setController(controller);
        MainMenu.getChildren().setAll((AnchorPane) SessionList.load());
    }

    //Opens player menu scene and connects to a session
    @FXML
    public void PlayerMenu(ActionEvent event) throws Exception{
        //check to see if the session is open
        if(true){
            //load player menu and connect to session
            FXMLLoader PCMain = new FXMLLoader(getClass().getResource("PCMain.fxml"));
            PCMainController controller = new PCMainController(username, "", password);
            PCMain.setController(controller);
            MainMenu.getChildren().setAll((AnchorPane) PCMain.load());
        }
        else{
            //display error message if connection failed
            Error.setVisible(true);
        }
    }
    @FXML
    public void Character(ActionEvent event) throws IOException{
       //opens character creation
       FXMLLoader CreateChar = new FXMLLoader(getClass().getResource("CharacterMain.fxml"));
       Stage stage = new Stage();
       stage.initOwner(Create.getScene().getWindow());
       stage.setScene(new Scene((Parent) CreateChar.load()));
       stage.setMaximized(true);
       stage.show();

       CharacterMainController controller = CreateChar.getController();
    }

    @FXML
    public void Quit(ActionEvent event) throws IOException{
        System.exit(0);
    }
}