/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;



import DB.connector;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class LoginController implements Initializable {
    
    @FXML
    public Label UserName;
    @FXML
    public Label Password;
    @FXML
    public TextField UserNameIn;
    @FXML
    public PasswordField PasswordIn;
    @FXML
    public Button LoginBtn;
    @FXML
    public AnchorPane LoginPane;
    @FXML
    public Label Error;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoginPane.setMaxSize(600, 400);
        LoginPane.setMinSize(600, 400);
    }
    
    @FXML
    public void Login(ActionEvent event)throws Exception{
        if(connector.Authenticate(UserNameIn.getText(), PasswordIn.getText()) == true){
            FXMLLoader Main =  new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            MainMenuController controller = new MainMenuController(UserNameIn.getText(), PasswordIn.getText());
            Main.setController(controller);
            LoginPane.getChildren().setAll((AnchorPane) Main.load());
        }
        else{
            UserNameIn.clear();
            PasswordIn.clear();
            Error.setVisible(true);
        }
    }
}