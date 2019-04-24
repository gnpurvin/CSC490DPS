/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;



import database.connector;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    public Button CreateNew;
    @FXML
    public PasswordField PasswordIn1;
    @FXML
    public Label Password1;
    @FXML
    public Button CreateLogin;
    @FXML
    public Button Cancel;
    @FXML
    public Label Error1;
    
    private Connection con;
    
    public LoginController(Connection connect){
        con = connect;
    }
    
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
    
    @FXML
    public void CreateNewLogin(ActionEvent event){
        LoginBtn.setVisible(false);
        CreateNew.setVisible(false);
        CreateLogin.setVisible(true);
        PasswordIn1.setVisible(true);
        Password1.setVisible(true);
        Cancel.setVisible(true);
        Error.setVisible(false);
    }
    
    @FXML
    public void Create(ActionEvent event){
        if(PasswordIn.getText().equals(PasswordIn1.getText())){
            connector.makeUser(UserNameIn.getText(), PasswordIn.getText());
            LoginBtn.setVisible(true);
            CreateNew.setVisible(true);
            CreateLogin.setVisible(false);
            PasswordIn1.setVisible(false);
            Password1.setVisible(false);
            Cancel.setVisible(false);
            Error1.setVisible(false);
        }
        else{
            Error1.setVisible(true);
        }
    }
    
    @FXML
    public void CancelNew(ActionEvent event){
        LoginBtn.setVisible(true);
        CreateNew.setVisible(true);
        CreateLogin.setVisible(false);
        PasswordIn1.setVisible(false);
        Password1.setVisible(false);
        Cancel.setVisible(false);
        Error1.setVisible(false);
    }
    @FXML
    public void Quit(ActionEvent event){
        System.exit(0);
    }
}