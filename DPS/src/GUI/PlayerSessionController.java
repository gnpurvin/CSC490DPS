/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Connectivity.Client;
import Connectivity.Server;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.File;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * FXML Controller class
 *
 * @author Spencer
 */
public class PlayerSessionController implements Initializable {

    @FXML
    public ChoiceBox TypeofDice;
    @FXML
    public TextField NumberofDice;
    @FXML
    public MenuItem OpenM;
    @FXML
    public MenuItem CloseM;
    @FXML
    public MenuItem CreateM;
    @FXML
    public MenuItem Create;
    @FXML
    public Menu AddC;
    @FXML
    public Button BackBtn;
    @FXML
    public TextArea ChatIn;
    @FXML
    public TabPane CharacterTabs;
    @FXML
    public AnchorPane MapPane;
    @FXML
    public TextField ChatOut;
    @FXML
    public ScrollPane Session;
    @FXML
    public Label Sessioncode;
    @FXML
    public Canvas MapCanvas;



    private String username;
    private int session;
    private boolean DM;
    private Client Player;

    public PlayerSessionController(String name, int ses, boolean dm){
        username = name;
        session = ses;
        DM = dm;


    }

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
        if(DM == true){
            this.DMSes();
        }
        TypeofDice.getItems().clear();
        TypeofDice.getItems().addAll("d4","d6","d8","d10","d12","d20","d100");
        NumberofDice.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 1, integerFilter));
        try{
            Player = new Client(11064, session, this);
            Player.start(username);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        File[] files = new File("Characters\\").listFiles();
        for(File f : files){
            if(f.isFile()){
                MenuItem temp = new MenuItem(f.getName());
                temp.setUserData(temp.getText());
                temp.setOnAction((ActionEvent event) -> {
                    AddCharacter(temp);
                });
                AddC.getItems().add(temp);
            }
        }
    }



    @FXML
    protected void ChatSend(ActionEvent action) throws IOException{
        Player.sendMsg(ChatOut.getText());
        this.onMessage(ChatOut.getText());
        ChatOut.clear();
    }

    @FXML
    protected void AddToken(ActionEvent action){

    }

    @FXML
    protected void DeleteToken(ActionEvent action){

    }

    @FXML
    protected void OpenMap(ActionEvent action){
        
    }

    @FXML
    protected void CloseMap(ActionEvent action){
        
    }
    
    @FXML
    protected void tokenMove(ActionEvent action){
        
    }

    @FXML
    protected void CreateMap(ActionEvent action) throws IOException{
       FXMLLoader Map = new FXMLLoader(getClass().getResource("MapMaker.fxml"));
       Map.setController(new MapMakerController());
       Stage stage = new Stage();
       stage.initOwner(CreateM.getParentPopup().getOwnerWindow());
       stage.setScene(new Scene((Parent) Map.load()));
       stage.setMaximized(true);
       stage.show();
    }

    protected void AddCharacter(MenuItem m){
        CharacterSheet character = new CharacterSheet();
        try {
            File file = new File("Characters\\" + m.getUserData());
            JAXBContext jaxbContext = JAXBContext.newInstance(CharacterSheet.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            character = (CharacterSheet) jaxbUnmarshaller.unmarshal(file);


	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
        Tab CharacterTab = new Tab();
        AnchorPane newTab = new AnchorPane();
        //add name from character sheet
        Label Name = new Label();
        Name.setText("Name: " + character.getName());
        AnchorPane.setTopAnchor(Name, 50.0);
        AnchorPane.setLeftAnchor(Name, 100.0);
        newTab.getChildren().add(Name);


        //add class from character sheet
        Label ClassName = new Label();
        ClassName.setText("Class: " + character.getClassName());
        AnchorPane.setTopAnchor(ClassName, 50.0);
        AnchorPane.setLeftAnchor(ClassName, 300.0);
        newTab.getChildren().add(ClassName);


        //add Race from character sheet
        Label Race = new Label();
        Race.setText("Race: " + character.getRace());
        AnchorPane.setTopAnchor(Race, 100.0);
        AnchorPane.setLeftAnchor(Race, 100.0);
        newTab.getChildren().add(Race);


        //add Level from character sheet
        Label Level = new Label();
        Level.setText("Level: " + character.getLevel());
        AnchorPane.setTopAnchor(Level, 100.0);
        AnchorPane.setLeftAnchor(Level, 300.0);
        newTab.getChildren().add(Level);


        //add Strength from character sheet
        Label str = new Label();
        str.setText("Strength: " + character.getStr());
        AnchorPane.setTopAnchor(str, 150.0);
        AnchorPane.setLeftAnchor(str, 100.0);
        newTab.getChildren().add(str);


        //add Dexterity from character sheet
        Label dex = new Label();
        dex.setText("Dexterity: " + character.getDex());
        AnchorPane.setTopAnchor(dex, 150.0);
        AnchorPane.setLeftAnchor(dex, 300.0);
        newTab.getChildren().add(dex);        

        //add Intelligence from character sheet
        Label Int = new Label();
        Int.setText("Intelligence: " + character.getInt());
        AnchorPane.setTopAnchor(Int, 200.0);
        AnchorPane.setLeftAnchor(Int, 100.0);
        newTab.getChildren().add(Int);

        //add Wisdom from character sheet
        Label wis = new Label();
        wis.setText("Wisdom: " + character.getWis());
        AnchorPane.setTopAnchor(wis, 200.0);
        AnchorPane.setLeftAnchor(wis, 300.0);
        newTab.getChildren().add(wis);
        
        //add Constitution from character sheet
        Label con = new Label();
        con.setText("Constitution: " + character.getCon());
        AnchorPane.setTopAnchor(con, 250.0);
        AnchorPane.setLeftAnchor(con, 100.0);
        newTab.getChildren().add(con);

        //add Charisma from character sheet
        Label cha = new Label();
        cha.setText("Charisma: " + character.getCha());
        AnchorPane.setTopAnchor(cha, 250.0);
        AnchorPane.setLeftAnchor(cha, 300.0);
        newTab.getChildren().add(cha);
        
        
        //add Hit points from character sheet
        Label hp = new Label();
        hp.setText("Hit points: " + character.getHP());
        AnchorPane.setTopAnchor(hp, 300.0);
        AnchorPane.setLeftAnchor(hp, 100.0);
        newTab.getChildren().add(hp);

        //add Armor class from character sheet
        Label ac = new Label();
        ac.setText("Armor class: " + character.getAC());
        AnchorPane.setTopAnchor(ac, 300.0);
        AnchorPane.setLeftAnchor(ac, 300.0);
        newTab.getChildren().add(ac);
        
        CharacterTab.setContent(newTab);
        CharacterTabs.getTabs().add(CharacterTab);
    }

    @FXML
    protected void CreateCharacter(ActionEvent action) throws IOException{
       //opens character creation
       FXMLLoader CreateChar = new FXMLLoader(getClass().getResource("CharacterMain.fxml"));
       Stage stage = new Stage();
       stage.initOwner(Create.getParentPopup().getScene().getWindow());
       stage.setScene(new Scene((Parent) CreateChar.load()));
       stage.setMaximized(true);
       stage.show();
    }

    @FXML
    protected void Roll(ActionEvent action) throws IOException{
        //rolls dice using
        Player.sendMsg("roll " + NumberofDice.getText() + (String) TypeofDice.getValue());
    }

    @FXML
    protected void Back(ActionEvent action)throws Exception{
        Player.logoff();
        Session.getScene().getWindow().hide();
    }

    private void DMSes(){
        try{
            Server serv = new Server(11064, session);
            serv.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Sessioncode.setText("Session Code: " + Integer.toString(session));
        Sessioncode.setVisible(DM);
        OpenM.setVisible(DM);
        CloseM.setVisible(DM);
        CreateM.setVisible(DM);
    }

    public void onMessage(String msg){
        //Prints new messages in chat
        ChatIn.setText(ChatIn.getText() + "\n" + msg);
    }
}
