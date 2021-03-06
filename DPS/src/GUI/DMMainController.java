/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.connector;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
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
    @FXML
    public Button Host;
    @FXML
    public Button MakeMap;
    
    private String username;
    private int session;
    private String password;
    private boolean DM;
    
    public DMMainController(String name, int sessionname, String pass){
        username = name;
        session = sessionname;
        password = pass;
        DM = true;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DMMain.setMaxSize(600, 400);
        DMMain.setMaxSize(600, 400);
    }

    @FXML
    public void MapMaker(ActionEvent event) throws Exception{
       FXMLLoader Map = new FXMLLoader(getClass().getResource("MapMaker.fxml"));
       Map.setController(new MapMakerController(username,session));
       Stage stage = new Stage();
       stage.initOwner(MakeMap.getScene().getWindow());
       Scene scene = new Scene((Parent) Map.load());
       File f = new File("flatred.css");
       scene.getStylesheets().clear();
       scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
       stage.setScene(scene);
       stage.setMaximized(true);
       stage.show();

    }
    
    @FXML
    public void CharacterCreate(ActionEvent event) throws Exception{
       FXMLLoader Create = new FXMLLoader(getClass().getResource("CharacterMain.fxml"));
       Stage stage = new Stage();
       stage.initOwner(CreateChar.getScene().getWindow());
       Scene scene = new Scene((Parent) Create.load());
       File f = new File("flatred.css");
       scene.getStylesheets().clear();
       scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
       stage.setScene(scene);
       stage.setMaximized(true);
       stage.show();

       CharacterMainController controller = Create.getController();

    }

    @FXML
    public void HostSession(ActionEvent event) throws Exception{
       FXMLLoader Session = new FXMLLoader(getClass().getResource("PlayerSession.fxml"));
       PlayerSessionController controller = new PlayerSessionController(username, session, DM);
       Session.setController(controller);
       Stage stage = new Stage();
       stage.initOwner(Host.getScene().getWindow());
       Scene scene = new Scene((Parent) Session.load());
       File f = new File("flatred.css");
       scene.getStylesheets().clear();
       scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
       stage.setScene(scene);
       stage.setMaximized(true);
       stage.show();
    }

    @FXML
    public void MainMenu(ActionEvent event) throws Exception{
        FXMLLoader Main =  new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        MainMenuController controller = new MainMenuController(username, password);
        Main.setController(controller);
        DMMain.getChildren().setAll((AnchorPane) Main.load());
    }
}
