package com.anass;

import java.io.IOException;

import com.anass.controllers.SetupController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {

    public void start(Stage stage) throws IOException {

        SetupController controller = new SetupController();
        controller.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

}