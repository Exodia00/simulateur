package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.anass.models.CoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.SimulationUiObserver;
import com.anass.simulation.Dashboard;
import com.anass.simulation.Simulation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Cette classe agit comme un contrôleur de la vue dashbord
 * 
 * @author Anass MEHDAOUI
 */
public class DashboardController implements Initializable, SimulationUiObserver{

    /**
     * Chemin vers le fichier fxml
     */
    private final String FXML_PATH = "/com/anass/fxml/dashboard.fxml";

    @FXML   private Button btnDemarrer;
    @FXML   private Button btnPause;
    @FXML   private Button btnReinit;
    @FXML   private Button btnStop;
    @FXML   private VBox debitsBox;

    /**
     * Le tableau de bord
     */
    private Dashboard dashboard;

    /**
     * Constructeur à partir d'une simulation
     * <p>
     * Ce constructeur crée un tableau de bord et ajoute cette instance aux observateurs de la simulation
     * 
     * @param simulation Simulation
     */
    public DashboardController(Simulation simulation){
        this.dashboard = new Dashboard(simulation);
        simulation.addSimulationUiObserver(this);
    }

    /**
     * Initialise le controlleur lorsque la vue est chargée
     * <p>
     * Cette method permet d'initialiser la partie cours d'eau du dashboard à partir des données de la simulation
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initDebitsContainer();
    } 

    /**
     * Renvoie la vue
     * <p>
     * Initialise la vue, definit cette instance comme controleur 
     * 
     * @return view Parent
     */
    public Parent getView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent view = (Parent) loader.load(getClass().getResourceAsStream(this.FXML_PATH));
        
        return view;
    }

    /**
     * Récepteur d'action pour le bouton de démarrage
     */
    @FXML
    void handleDemarrer(ActionEvent event) {
        this.updateEtatSimulation(Etat.ACTIVE);
    }
    
    /**
     * Récepteur d'action pour le bouton de pause
     */
    @FXML
    void handlePause(ActionEvent event) {
        this.updateEtatSimulation(Etat.PAUSE);
    }
    
    /**
     * Récepteur d'action pour le bouton d'arret
     */
    
    @FXML
    void handleStop(ActionEvent event) {
        this.updateEtatSimulation(Etat.FIN);
    }
    
    /**
     * Récepteur d'action pour le bouton de réinitialisation
     */
    @FXML
    void handleReinit(ActionEvent event) {
        dashboard.reinitialiserSimulation();
    }

    @Override
    public void updateUi(Integer[] temps){}

    /**
     * Terminer la simulation
     * <p>
     * Definit la valeur de l'etat sur Etat.FIN
     */
    @Override
    public void endSimulation(){
        updateEtatSimulation(Etat.FIN);
    }

    /**
     * Met à jour l'état de la simulation et l'état des boutons du tableau de bord
     * 
     * @param etat Etat.
     */
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
    
    /**
     * Initialiser l'ensemble des controls des cours d'eau
     */
    private void initDebitsContainer(){
        try {
            for(int i=0; i<this.dashboard.getNbCours(); i++){
                initDebitBox(this.dashboard.getCours(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialiser un control d'un cours d'eau
     * 
     * @param cours CoursModel
     * @throws IOException
     */
    private void initDebitBox(CoursModel cours) throws IOException{
        CoursController controller = new CoursController(cours);
        this.debitsBox.getChildren().add(controller.getView());
    }

}
