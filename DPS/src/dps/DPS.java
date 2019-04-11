/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Spencer
 */
public class DPS extends Application {

    public mapController mpc = new mapController();
    
    StackPane mapping = mpc.mapping;
    Scene mapScene = mpc.mapScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/MainMenu.fxml"));
        primaryStage.setTitle("DPS");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}

