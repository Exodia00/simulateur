package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.anass.models.SimulationParametres;
import com.anass.simulation.Simulation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulationController implements Initializable{

    @FXML BorderPane borderPane;
    
    private SimulationParametres initParametres;
    private Simulation simulation;
    
    private String FXML_PATH = "/com/anass/fxml/simulation.fxml";
    private Stage stage;


    public SimulationController(SimulationParametres params){
        this.initParametres = new SimulationParametres(params);
        simulation = new Simulation(this.initParametres);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initDashboard();
            initMetriques();
            initAnimation();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(this.FXML_PATH));
        this.stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Simulation");
        stage.show();
    }

    // Methods privees 
    private void initDashboard() throws IOException{
        DashboardController dashboardController = new DashboardController(simulation);
        borderPane.setRight(dashboardController.getView());
    }

    private void initMetriques() throws IOException{
        MetriquesController metriquesController = new MetriquesController(simulation);
        borderPane.setLeft(metriquesController.getView());
    }

    private void initAnimation(){
        AnimationController controller = new AnimationController(simulation.getModel());
        borderPane.setCenter(controller.getView());
    }




}
