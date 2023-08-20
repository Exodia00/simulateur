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
import javafx.scene.layout.VBox;

/**
 * Cette classe agit comme un contrôleur de la vue des metriques dans la simulation
 * <p>
 * Ce controlleur est responsable de l'affichage des differents paramètres de la simulation,
 * et met à jour l'interface selon l'état de la simulation
 * 
 * @author Anass MEHDAOUI
 */
public class MetriquesController implements Initializable, SimulationUiObserver{

    /**
     * Le chemin vers le fichier FXML
     */
    private final String FXML_PATH = "/com/anass/fxml/metriques.fxml";

    @FXML private Label debitConduiteFld;
    @FXML private Label debitEntrantFld;
    @FXML private Label etatFld;
    @FXML private Label etatGroupeFld;
    @FXML private Label heureFld;
    @FXML private Label niveauFld;
    @FXML private VBox metriquesBox;
    
    /**
     * le modèle de la simulation
     */
    private SimulationModel model;

    /**
     * Constructeur à partir d'une simulation.
     * <p>
     * Ce constructeur définit le modèle de l'instance et l'enregistre comme observateur de la simulation,
     * ce qui permet de mettre à jour l'interface selon l'état de la simulation
     * 
     * @param simulation Simulation
     */
    public MetriquesController(Simulation simulation){
        this.model = simulation.getModel();
        simulation.addSimulationUiObserver(this);
    }

    /**
     * Initialise la vue lorsqu'elle est chargée
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUi(new Integer[]{0,0});
    }

    /**
     * Cette méthode s'occupe du mappage entre les données de la simulation et les élements de controle de la vue
     * 
     * @param temps Integer[] l'instance actuelle de la simulation
     */
    @Override
    public void updateUi(Integer[] temps) {
        this.debitConduiteFld.setText(Integer.toString(model.getTurbinModel().getDebit(temps[0])));
        this.debitEntrantFld.setText(Integer.toString(model.getEnsembleCoursModel().getDebitTotal()));
        this.etatFld.setText(model.getEtatSimulation().toString());
        this.etatGroupeFld.setText(model.getEtatTurboStr(temps[0]));
        this.heureFld.setText(TempsOperations.toString(temps));
        this.niveauFld.setText(Integer.toString(model.getReservoirModel().getVolume()));
    }

    /**
     * Cette méthode met à jours l'interface à la fin de la simulation
     */
    @Override
    public void endSimulation(){
        // Button btn = new Button("Sauvegarder");
        // metriquesBox.getChildren().add(btn);
    }
    
    /**
     * Renvoie la vue des métriques, en spécifiant cette instance comme controleur.
     * 
     * @throws IOException
     */
    public Parent getView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent view = (Parent) loader.load(getClass().getResourceAsStream(FXML_PATH));
        return view;
    }

}
