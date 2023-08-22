package com.anass.simulation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.anass.csv.EnregistreurCSV;
import com.anass.models.CoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.models.SimulationParametres;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.DashboardObserver;
import com.anass.observers.SimulationUiObserver;
import com.anass.observers.VolumeObserver;

import javafx.util.Duration;

/**
 * Cette classe représente la simulation globale. Elle gère les différents aspects de la simulation tels que les cours d'eau,
 * les turbines, le temps, etc. Elle agit en tant qu'observateur du tableau de bord, de l'interface utilisateur et du volume.
 * Elle coordonne le fonctionnement global de la simulation.
 * 
 * @author Anass
 */
public class Simulation implements DashboardObserver, VolumeObserver{

    private SimulationParametres paramInit;
    private SimulationModel model;
    private List<CoursSimulation> coursSimulations;
    private TurbinSimulation turbinSimultaion;
    private TempsSimulation tempsSimulation;
    private EnregistreurCSV printer;

    private List<SimulationUiObserver> simulationUiObservers = new ArrayList<>();

    /**
     * Constructeur de la classe Simulation.
     * 
     * @param param Les paramètres initiaux de la simulation.
     */
    public Simulation(SimulationParametres param){
        this.paramInit = param;
        this.model = new SimulationModel(param);
    }

    /**
     * Lance la simulation.
     * 
     */
    public void startSimulation(){
        try {
            initServices();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startServices();
    }

    /**
     * Executé pour mettre fin à la simulation
     * 
     */
    public void endSimulation(){
        for(SimulationUiObserver observer : this.simulationUiObservers){
            observer.endSimulation();
        }
        printer.fermer();
    }

    /**
     * Obtient une représentation en chaine de caractères de la simulation
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-- Simulation :  -------/");
        builder.append(model.toString());
        
        return builder.toString();
    }

    /**
     * Renvoie le modèle de la simulation;
     * @return
     */
    public SimulationModel getModel(){
        return model;
    }

    /**
     * Enregistre un observateur de la simulation;
     * 
     * @param observer : Composant qui adapte l'interface graphique aux changements de la simulation
     */
    public void addSimulationUiObserver(SimulationUiObserver observer){
        simulationUiObservers.add(observer);
    }

    /**
     * Réinitialise la simulation, en utilisant les paramètres utiliser pour son initialisation au moment de son instanciation.
     */
    @Override
    public void reinitialiserSimulation(){
        this.model = new SimulationModel(this.paramInit);
        startSimulation();
        updateUI();
    }

    /**
     * Cette méthode sert à mettre à jour le débit d'un cours dans la simulation;
     * 
     * @param index : Indice du cours d'eau dans la simulation, identique à son identifiant
     * @param 
     */
    @Override
    public void updateDebitCours(int index, int value) {
        this.model.getEnsembleCoursModel().setDebit(index, value);
    }

    /**
     * Cette méthode sert à mettre à jour l'état de la simulation selon la valeur renvoyé par le tableau de bord
     */
    @Override
    public void updateEtatsimulation(EtatSimulation etat) {
        this.model.setEtatSimulation(etat);
        if (etat.getEtat() == Etat.ACTIVE){
            if(this.tempsSimulation == null) {
                startSimulation();
            }
        }
        if (etat.getEtat() == Etat.FIN){
            endSimulation();
        }
    }

    /**
     * Cette méthode sert à recalculer le volume du réservoir, selon les volumes d'eau fournis par les cours d'eau et utilisé par le turbo-alternateur
     * 
     * @param volume : le volume à ajouter, peut être négative pour effectuer une soustraction
     */
    @Override
    public void updateVolume(int volume){
        model.getReservoirModel().addVolume(volume);
    }

    /**
     * Initialise les different services d'ordonnancement (ScheduledServices) nécessaires pour le fonctionnement de la simulation
     * 
     * @throws IOException
     */
    private void initServices() throws IOException{
        this.printer = new EnregistreurCSV();

        this.turbinSimultaion = new TurbinSimulation(model.getTurbinModel(), model.getEtatSimulation());
        this.turbinSimultaion.setVolumeObserver(this);
        this.turbinSimultaion.setOnSucceeded(event -> {
            try {
                printer.ajouterRecord(tempsSimulation.getTemps(), model);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.coursSimulations = new ArrayList<>();
        for(int i=0; i<model.getEnsembleCoursModel().getNbCours(); i++){
            CoursModel cours = model.getEnsembleCoursModel().getCours(i);
            CoursSimulation coursSimulation = new CoursSimulation(cours, model.getEtatSimulation());
            coursSimulation.setVolumeObserver(this);
            coursSimulations.add(coursSimulation);
        }
        this.tempsSimulation = new TempsSimulation(model.getEtatSimulation(), model.getDuree());
    }

    /**
     * Lances les différents SchedulesServices de la simulation
     */
    private void startServices(){
        turbinSimultaion.setPeriod(Duration.seconds(1));
        turbinSimultaion.start();
        for(CoursSimulation coursSimulation : coursSimulations){
            coursSimulation.setPeriod(Duration.millis(250));
            coursSimulation.start();
        }
        tempsSimulation.setPeriod(Duration.millis(250));
        tempsSimulation.setOnSucceeded((event->{
            turbinSimultaion.setHeure(tempsSimulation.getTemps()[0]);
            if (tempsSimulation.estTerminee()){
                try {
                    updateEtatsimulation(new EtatSimulation(Etat.FIN));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            updateUI();
        }));
        tempsSimulation.start();
    }
    
    /**
     * Notifie les observateur pour mettre à jour les differents composants de la simulation
     * 
     */
    private void updateUI(){
        for(SimulationUiObserver observer : simulationUiObservers){
            observer.updateUi(tempsSimulation.getTemps());
        }
    }

}
