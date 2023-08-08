package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.anass.models.SimulationParametres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SetupController implements Initializable{

    // FXML fields 
    @FXML private FlowPane conduiteBox;
    @FXML private FlowPane debitBox;
    @FXML private TextField dureeFld;
    @FXML private Button lancerBtn;
    @FXML private TextField nbCoursFld;
    @FXML private TextField niveauReservoirFld;
    @FXML private Label alert;
    
    private Stage stage;
    private String FXML_PATH = "/com/anass/fxml/setup.fxml";

    // Data fields
    ObservableList<TextField> conduiteFlds = FXCollections.observableArrayList();
    ObservableList<HBox> debitHboxes = FXCollections.observableArrayList();

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

        try{
            lancerSimulationStage(this.getSimulationParametres());
        }catch (IllegalArgumentException e){ return;}
    }

    @FXML public void handleNbCoursChanged(){
        // Dynamically create debit input boxes
        try{
            int nbCours = Integer.parseInt(nbCoursFld.getText());
            alerter("");    // Reset any potential error message
            updateDebitBox(nbCours);
        }catch (NumberFormatException e){
            alerter("Le nombre de cours n'est pas valide");
        }
    }

    public void start() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(this.FXML_PATH));
        this.stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Nouvelle Simulation");
        stage.show();
    }

    private void lancerSimulationStage(SimulationParametres params){
        SimulationController simulationController = new SimulationController(params);
        try {
            simulationController.start();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDebitBox(int nbCours){
        int nbCoursActu = debitHboxes.size();
        int diff = nbCoursActu - nbCours;

        if( diff==0 ) return;

        if( diff < 0 ){
            for( int i=0; i<-diff; i++){
                System.out.println("Creating box : " + i);
                HBox box = creerDebitIBox();
                debitBox.getChildren().add(box);
                debitHboxes.add(box);
            }
        }else if(diff>0){
            for( int j=0; j<diff; j++){
                HBox box = debitHboxes.remove(nbCours);
                debitBox.getChildren().remove(nbCours);
                System.out.println("Removed " + ((Label) box.getChildren().get(0)).getText());
            }
        }
    }

    private HBox creerConduiteIBox(int i){
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        Label label = new Label(String.format("%02d", i) + ":00H");
        TextField txtFld = new TextField();
        txtFld.setPrefWidth(100);



        box.getChildren().addAll(label, txtFld);        

        return box;
    }

    private HBox creerDebitIBox(){
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        int position = this.debitHboxes.size() + 1;

        Label label = new Label("Debit N" + String.format("%02d", position) + " : ");
        TextField txtFld = new TextField();
        txtFld.setPrefWidth(100);

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

    private ArrayList<Integer> getDebitsCours(){
        ArrayList<Integer> debitsCours = new ArrayList<Integer>();
        for (HBox box : this.debitHboxes){
            debitsCours.add(Integer.parseInt(((TextField) box.getChildren().get(1)).getText()));
        }
        return debitsCours;
    }

    private SimulationParametres getSimulationParametres() throws IllegalArgumentException{
        SimulationParametres parametres = new SimulationParametres();
        // nb Cours :
        parametres.setNbCours(Integer.parseInt(this.nbCoursFld.getText()));
        // debits conduite
        try{
            parametres.setDebitsConduite(getDebitsConduite());
        }catch(NumberFormatException e){
            alerter("Valeurs des debits de la conduites pendant la journée ne sont pas valides.");
            throw new IllegalArgumentException();
        }
        // Debit cours :
        try{
            parametres.setDebitsCours(getDebitsCours());
        }catch(NumberFormatException e){
            alerter("Valeurs des debits des cours ne sont pas valides.");
            throw new IllegalArgumentException();
        }
        // Niveau du reservoir
        try{
            parametres.setNiveauReservoir(Integer.parseInt(this.niveauReservoirFld.getText()));
        }catch(NumberFormatException e){
            alerter("Valeur du niveau du reservoir n'est pas valide");
            throw new IllegalArgumentException();
        }
        // Duree de la simulation :
        try{
            parametres.setDuree(Integer.parseInt(this.dureeFld.getText()));
        }catch(NumberFormatException e){
            alerter("Valeur de la duree de la simulation n'est pas valide");
            throw new IllegalArgumentException();
        }
        return parametres;
    }
}
