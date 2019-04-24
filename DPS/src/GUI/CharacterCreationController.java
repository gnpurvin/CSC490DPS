/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
@XmlRootElement
public class CharacterCreationController implements Initializable {

    /**
     * Initializes the controller class.
     */

    @FXML
    public AnchorPane CharacterCreation;
    @FXML
    public TextField Name;
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
        ClassDropDown.getItems().addAll("Barbarian", "Bard", "Cleric", "Druid", "Fighter",
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

        CharacterSheet Character = new CharacterSheet();
        Character.setName(Name.getText());
        Character.setClassName(ClassDropDown.getValue().toString());
        Character.setRace(RaceDropDown.getValue().toString());
        Character.setLevel(LevelTxt.getText());
        Character.setStr(Str.getText());
        Character.setDex(Dex.getText());
        Character.setInt(Int.getText());
        Character.setCon(Con.getText());
        Character.setWis(Wis.getText());
        Character.setCha(Cha.getText());
        Character.setHP(HP.getText());
        Character.setAC(AC.getText());
        
                File file = new File("Characters\\" + Character.getName() + ".xml");
                JAXBContext jaxbContext = JAXBContext.newInstance(CharacterSheet.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                
                jaxbMarshaller.marshal(Character, file);
                jaxbMarshaller.marshal(Character, System.out);
                
                

        AnchorPane Back = FXMLLoader.load(getClass().getResource("CharacterMain.fxml"));
        CharacterCreation.getChildren().setAll(Back);
    }
    
    public void editChar(String name){
        CharacterSheet character = new CharacterSheet();
        try {
            File file = new File("Characters\\" + name);
            JAXBContext jaxbContext = JAXBContext.newInstance(CharacterSheet.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            character = (CharacterSheet) jaxbUnmarshaller.unmarshal(file);


	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
    }
}