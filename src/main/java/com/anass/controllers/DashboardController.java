package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.anass.models.CoursModel;
import com.anass.models.EnsembleCoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.DashboardObserver;
import com.anass.observers.SimulationUiObserver;
import com.anass.simulation.Dashboard;
import com.anass.simulation.Simulation;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DashboardController implements Initializable, SimulationUiObserver{
    private final String FXML_PATH = "/com/anass/fxml/dashboard.fxml";

    @FXML   private Button btnDemarrer;
    @FXML   private Button btnPause;
    @FXML   private Button btnReinit;
    @FXML   private Button btnStop;
    @FXML   private VBox debitsBox;

    private Dashboard dashboard;

    public DashboardController(Simulation simulation){
        this.dashboard = new Dashboard(simulation);
        simulation.addSimulationUiObserver(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initDebitsContainer();
    } 

    public Parent getView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent view = (Parent) loader.load(getClass().getResourceAsStream(this.FXML_PATH));
        
        return view;
    }

    @FXML
    void handleDemarrer(ActionEvent event) {
        this.updateEtatSimulation(Etat.ACTIVE);
    }
    
    @FXML
    void handlePause(ActionEvent event) {
        this.updateEtatSimulation(Etat.PAUSE);
    }
    
    
    @FXML
    void handleStop(ActionEvent event) {
        this.updateEtatSimulation(Etat.FIN);
    }
    
    @FXML
    void handleReinit(ActionEvent event) {
        // this.etatSimulation = new EtatSimulation(EtatSimulation.ACTIVE);
    }

    // Simulation Ui Observer method
    @Override
    public void updateUi(Integer[] temps){}

    @Override
    public void endSimulation(){
        // Disable all buttons
        btnDemarrer.setDisable(true);
        btnPause.setDisable(true);
        btnStop.setDisable(true);
        btnReinit.setDisable(false);
    }

    // Handle Properties change
    private void updateEtatSimulation(Etat etat){
        if(etat == Etat.ACTIVE){
            btnDemarrer.setDisable(true);
            btnPause.setDisable(false);
            btnStop.setDisable(false);
        }else if(etat == Etat.PAUSE){
            btnDemarrer.setDisable(false);
            btnPause.setDisable(true);
            btnStop.setDisable(false);
        }else{
            btnDemarrer.setDisable(true);
            btnPause.setDisable(true);
            btnStop.setDisable(true);
        }
        dashboard.updateEtatSimulation(new EtatSimulation(etat));
        
    }   
    
    // UI Operations
    private void initDebitsContainer(){
        try {
            for(int i=0; i<this.dashboard.getNbCours(); i++){
                initDebitBox(this.dashboard.getCours(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDebitBox(CoursModel cours) throws IOException{
        CoursController controller = new CoursController(cours);
        this.debitsBox.getChildren().add(controller.getView());
    }

}
