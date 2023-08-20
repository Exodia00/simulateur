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

/**
 * Cette class contrôle la fenêtre de la simulation
 * 
 * @author Anass MEHDAOUI
 */
public class SimulationController implements Initializable{

    @FXML BorderPane borderPane;
    
    /**
     * Parametres de la simulation, utilisé pour initialiser et reinitialiser la simulation
     */
    private SimulationParametres initParametres;
    /**
     * Simulation
     */
    private Simulation simulation;
    
    private String FXML_PATH = "/com/anass/fxml/simulation.fxml";
    private Stage stage;

    /**
     * Constructeur par parametres de la simulation, permet d'initialiser la simulation à partir de ces paramètres
     * 
     * @param param SimulationParameters : les paramètres initiales de la simulation
     */
    public SimulationController(SimulationParametres params){
        this.initParametres = new SimulationParametres(params);
        simulation = new Simulation(this.initParametres);
    }

    /**
     * Initialise la vue en chargant les différents composants graphiques.
     */
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

    /**
     * Lance la fenêtre de la simulation
     * 
     * @throws IOException
     */
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

   /**
    * Cette méthode charge le tableau de bord et l'ajoute à la vue
    * 
    * @throws IOException
    */
    private void initDashboard() throws IOException{
        DashboardController dashboardController = new DashboardController(simulation);
        borderPane.setRight(dashboardController.getView());
    }

    /**
     * Cette methode charge la vue des métriques et l'ajoute à la vue
     * 
     * @throws IOException
     */
    private void initMetriques() throws IOException{
        MetriquesController metriquesController = new MetriquesController(simulation);
        borderPane.setLeft(metriquesController.getView());
    }

    /**
     * Cette méthode charge la vue des métriques et l'ajoute à la vue
     */
    private void initAnimation(){
        AnimationController controller = new AnimationController(simulation.getModel());
        simulation.addSimulationUiObserver(controller);
        borderPane.setCenter(controller.getView());
    }




}
