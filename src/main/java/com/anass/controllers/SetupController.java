package com.anass.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SetupController implements Initializable{

    // FXML fields 
    @FXML private FlowPane conduiteBox;
    @FXML private VBox debitBox;
    @FXML private TextField dureeFld;
    @FXML private Button lancerBtn;
    @FXML private TextField nbCoursFld;
    @FXML private TextField niveauReservoirFld;
    @FXML private Label alert;

    // Data fields
    ObservableList<TextField> conduiteFlds = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Create 24 ui elements to take the values of Ci [i:1-24]
        for(int i=0; i<24; i++){
            HBox conduiteIBox = creerConduiteIBox(i);           // Get the element
            this.conduiteBox.getChildren().add(conduiteIBox);   // Add the element to the view
            this.conduiteFlds.add((TextField)conduiteIBox.getChildren().get(1));  
        }

    } 

    @FXML public void lancerSimulation(){
        // Get user input
        try{
            ArrayList<Integer> debitsConduite = getDebitsConduite();
        }catch(NumberFormatException e){
            alerter("Veuillez corriger les format numériques");
        }

    }

    private HBox creerConduiteIBox(int i){
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        Label label = new Label(String.format("%02d", i) + ":00H");
        TextField txtFld = new TextField();

        box.getChildren().addAll(label, txtFld);        

        return box;
    }

    private void alerter(String message){
        // Alert an error message
        this.alert.setText(message);
    }

    private ArrayList<Integer> getDebitsConduite() throws NumberFormatException{
        ArrayList<Integer> debitsConduite = new ArrayList<Integer>();
        for (TextField tf : conduiteFlds){
                debitsConduite.add(Integer.parseInt(tf.getText()));
        }
        return debitsConduite;
    }

}
