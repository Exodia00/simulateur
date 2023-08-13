package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.anass.barrage.TempsOperations;
import com.anass.models.SimulationModel;
import com.anass.observers.SimulationUiObserver;
import com.anass.simulation.Simulation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MetriquesController implements Initializable, SimulationUiObserver{
    private final String FXML_PATH = "/com/anass/fxml/metriques.fxml";

    @FXML private Label debitConduiteFld;
    @FXML private Label debitEntrantFld;
    @FXML private Label etatFld;
    @FXML private Label etatGroupeFld;
    @FXML private Label heureFld;
    @FXML private Label niveauFld;
    
    private SimulationModel model;

    public MetriquesController(Simulation simulation){
        this.model = simulation.getModel();
        simulation.addSimulationUiObserver(this);
    }

       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUi();
    }

    @Override
    public void updateUi() {
        this.debitConduiteFld.setText(Integer.toString(model.getTurbinModel().getDebit(Simulation.getHeure())));
        this.debitEntrantFld.setText(Integer.toString(model.getEnsembleCoursModel().getDebitTotal()));
        this.etatFld.setText(model.getEtatSimulation().toString());
        this.etatGroupeFld.setText(model.getEtatTurboStr(Simulation.getHeure()));
        this.heureFld.setText(TempsOperations.toString(Simulation.getTemps()));
        this.niveauFld.setText(Integer.toString(model.getReservoirModel().getVolume()));
    }
  
    public Parent getView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent view = (Parent) loader.load(getClass().getResourceAsStream(FXML_PATH));
        return view;
    }

}
