package com.github.andreasbraun5.thesis.main;/**
 * Created by AndreasBraun on 31.07.2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;

public class Thesis extends Application {

    @Getter
    private Stage primaryStage;
    private Parent rootLayout; // former Borderpane

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        initRootLayout();
        Controller controller = new Controller();
        controller.setThesis(this);
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Thesis.class.getResource("/GUI.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            primaryStage.setTitle("CYK Instances Generator");
            primaryStage.setScene(new Scene(rootLayout));
            primaryStage.sizeToScene();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}



