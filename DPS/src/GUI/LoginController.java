/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class LoginController implements Initializable {
    @FXML
    public TextField UserNameIn;
    @FXML
    public TextField SessionCodeIn;
    @FXML
    public TextField ServerNameIn;
    @FXML
    public Button DM;
    @FXML
    public Button PC;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    public void PClog(){
        SessionCodeIn.setVisible(true);
        PC.setVisible(true);
    }
    
    public void DMlog(){
        ServerNameIn.setVisible(true);
        DM.setVisible(true);
    }

    public void LoginDM(ActionEvent action) throws IOException {
        FXMLLoader Session = new FXMLLoader(getClass().getResource("PlayerSession.fxml"));
        Stage stage = new Stage();
        stage.initOwner(DM.getScene().getWindow());
        stage.setScene(new Scene((Parent) Session.load()));
        stage.setHeight(800);
        stage.setWidth(1280);
        stage.show();
    }
    
    public void LoginPC(ActionEvent action) throws IOException {
        FXMLLoader Session = new FXMLLoader(getClass().getResource("PlayerSession.fxml"));
        Stage stage = new Stage();
        stage.initOwner(PC.getScene().getWindow());
        stage.setScene(new Scene((Parent) Session.load()));
        stage.setHeight(800);
        stage.setWidth(1280);
        stage.show();
    }
}
