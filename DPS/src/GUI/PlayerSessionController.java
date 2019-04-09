/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class PlayerSessionController implements Initializable {
    
    @FXML
    private ChoiceBox TypeofDice;
    @FXML
    private TextField NumberofDice;
    
    UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if(newText.matches("[1-9][0-9]*")){
            return change;
        }
        else if(newText.matches("")){
            return change;
        }
        return null;
    };

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TypeofDice.getItems().clear();
        TypeofDice.getItems().addAll("d4","d6","d8","d10","d12","d20","d100");
        NumberofDice.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        
    }

    @FXML
    public void ChatSend(ActionEvent action){
        
    }
    
    @FXML
    public void AddToken(ActionEvent action){
        
    }
    
    @FXML
    public void DeleteToken(ActionEvent action){
        
    }
    
    @FXML
    public void OpenMap(ActionEvent action){
        
    }
    
    @FXML
    public void CloseMap(ActionEvent action){
        
    }
    
    @FXML
    public void CreateMap(ActionEvent action){
        
    }
    
    @FXML
    public void AddCharacter(ActionEvent action){
        
    }
    
    @FXML
    public void CreateCharacter(ActionEvent action){
        
    }
    
}
