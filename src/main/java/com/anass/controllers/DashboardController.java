package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.anass.models.EnsembleCoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class DashboardController implements Initializable{
    private final String FXML_PATH = "/com/anass/fxml/dashboard.fxml";

    private EnsembleCoursModel ensembleCoursModel;
    private EtatSimulation etatSimulation;

    public DashboardController(SimulationModel model){
        this.ensembleCoursModel = model.getEnsembleCoursModel();
        this.etatSimulation = model.getEtatSimulation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Init Dashboard with data :");
        System.out.println(ensembleCoursModel.toString());
    } 

    public Parent getView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent view = (Parent) loader.load(getClass().getResourceAsStream(this.FXML_PATH));
        
        return view;
    }


}
