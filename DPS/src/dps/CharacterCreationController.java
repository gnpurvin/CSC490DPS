/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dps;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class CharacterCreationController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    public AnchorPane CharacterCreation;
    @FXML
    public ComboBox ClassDropDown;
    @FXML
    public ComboBox RaceDropDown;
    @FXML
    public TextField LevelTxt;
    @FXML
    public TextField Str;
    @FXML
    public TextField Dex;
    @FXML
    public TextField Int;
    @FXML
    public TextField Con;
    @FXML
    public TextField Wis;
    @FXML
    public TextField Cha;
    @FXML
    public TextField HP;
    @FXML
    public TextField AC;

    //int filter to only allow integers 1-20
    UnaryOperator<Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if(newText.matches("[1-9]")){
            return change;
        }
        else if(newText.matches("1[0-9]")){
            return change;
        }
        else if(newText.matches("20")){
            return change;
        }
        else if(newText.matches("")){
            return change;
        }
        return null;
    };
    //int filter to only allow positive integers
    UnaryOperator<Change> integerFilter2 = change -> {
        String newText = change.getControlNewText();
        if(newText.matches("[1-9][0-9]*")){
            return change;
        }
        else if(newText.matches("")){
            return change;
        }
        return null;
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets combo box with options below
        ClassDropDown.getItems().clear();
        ClassDropDown.getItems().addAll("Bard", "Cleric", "Druid", "Fighter",
                "Monk", "Paladin", "Ranger", "Rouge", "Sorcerer", "Warlock", "Wizard");

        //sets combo box with options below
        RaceDropDown.getItems().clear();
        RaceDropDown.getItems().addAll("DragonBorn", "Dwarf", "Elf", "Gnome",
                "Half-Elf", "Halfling", "Half-Orc", "Human", "Tiefling");

        //sets all int text fields
        LevelTxt.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        Str.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        Dex.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        Int.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        Con.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        Wis.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        Cha.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        HP.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter2));
        AC.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter2));
    }

    //loads previous menu
    @FXML
    public void Back(ActionEvent action) throws IOException{
        AnchorPane Back = FXMLLoader.load(getClass().getResource("CharacterMain.fxml"));
        CharacterCreation.getChildren().setAll(Back);
    }

    //saves file as xml and returns to previous menu
    @FXML
    public void Save(ActionEvent action) throws Exception{
        
        AnchorPane Back = FXMLLoader.load(getClass().getResource("CharacterMain.fxml"));
        CharacterCreation.getChildren().setAll(Back);
    }
}
