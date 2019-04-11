/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Spencer
 */
public class DPS extends Application {
    
    
    
     
    @Override
    public void start(Stage primaryStage) {
        mapController mpc = new mapController();
        StackPane mapping = mpc.mapping;
        Scene mapScene = mpc.mapScene;
    
        Button btn1 = new Button();
        btn1.setText("Say 'Hello World'");
        
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);
        root.getChildren().add(btn1);
        
        mpc.drawMap();
        
        Button btn2 = new Button();
        btn2.setText("Make map");
        mapping.getChildren().add(btn2);
        btn2.setOnAction(e -> mapping.getChildren().add(mpc.c));
        
        btn1.setOnAction(e -> primaryStage.setScene(mapScene));
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }
    
}
