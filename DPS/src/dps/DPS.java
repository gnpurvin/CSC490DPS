/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dps;

import GUI.LoginController;
import database.connector;
import java.io.File;
import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Spencer
 */
public class DPS extends Application {
    mapController mpc = new mapController();
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        Connection con = connector.connect();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Login.fxml"));
        LoginController controller = new LoginController(con);        
        loader.setController(controller);
        Parent root = (AnchorPane) loader.load();
        primaryStage.setTitle("DPS");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 600, 400);
        File f = new File("flatred.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println(mpc.currMap.toString());
        //mpc.saveMap();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);    
    }

}