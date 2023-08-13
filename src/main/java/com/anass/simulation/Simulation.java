package com.anass.simulation;

import java.util.ArrayList;
import java.util.List;

import com.anass.barrage.TempsOperations;
import com.anass.models.CoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.models.SimulationParametres;
import com.anass.models.EtatSimulation.Etat;
import com.anass.observers.DashboardObserver;
import com.anass.observers.SimulationUiObserver;
import com.anass.observers.VolumeObserver;

import javafx.util.Duration;

public class Simulation implements DashboardObserver, VolumeObserver{

    private static Integer[] temps = new Integer[]{ 0, 0 };

    private SimulationModel model;
    private List<CoursSimulation> coursSimulations;
    private TurbinSimulation turbinSimultaion;
    private TempsSimulation tempsSimulation;

    private List<SimulationUiObserver> simulationUiObservers = new ArrayList<>();

    public Simulation(SimulationParametres param){
        this.model = new SimulationModel(param);
    }

    public void startSimulation(){
        initServices();
        startServices();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-- Simulation :  -------/");
        builder.append(model.toString());
        
        return builder.toString();
    }

    public SimulationModel getModel(){
        return model;
    }

    public static int getHeure(){
        return temps[0];
    }

    public static Integer[] getTemps(){
        return temps;
    }

    public static void addTemps(int h, int m){
        Simulation.temps = TempsOperations.add(temps, h, m);
    }

    public void addSimulationUiObserver(SimulationUiObserver observer){
        simulationUiObservers.add(observer);
    }

    // Dashboard Observer methods

    @Override
    public void updateDebitCours(int index, int value) {
        this.model.getEnsembleCoursModel().setDebit(index, value);
    }


    @Override
    public void updateEtatsimulation(EtatSimulation etat) {
        this.model.setEtatSimulation(etat);
        if (etat.getEtat() == Etat.ACTIVE){
            startSimulation();
        }
    }

    // Volume Observer method

    @Override
    public void updateVolume(int volume){
        model.getReservoirModel().addVolume(volume);
    }

    // private methods :
    private void initServices(){
        this.turbinSimultaion = new TurbinSimulation(model.getTurbinModel(), model.getEtatSimulation());
        this.turbinSimultaion.setVolumeObserver(this);
        this.coursSimulations = new ArrayList<>();
        for(int i=0; i<model.getEnsembleCoursModel().getNbCours(); i++){
            CoursModel cours = model.getEnsembleCoursModel().getCours(i);
            CoursSimulation coursSimulation = new CoursSimulation(cours, model.getEtatSimulation());
            coursSimulation.setVolumeObserver(this);
            coursSimulations.add(coursSimulation);
        }
        this.tempsSimulation = new TempsSimulation(model.getEtatSimulation());
    }

    private void startServices(){
        turbinSimultaion.setPeriod(Duration.seconds(1));
        turbinSimultaion.start();
        for(CoursSimulation coursSimulation : coursSimulations){
            coursSimulation.setPeriod(Duration.millis(250));
            coursSimulation.start();
        }
        tempsSimulation.setPeriod(Duration.millis(250));
        tempsSimulation.setOnSucceeded((event->{
            updateUI();
        }));
        tempsSimulation.start();
    }

    private void updateUI(){
        for(SimulationUiObserver observer : simulationUiObservers){
            observer.updateUi();
        }
    }

}
