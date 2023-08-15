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

public class Simulation implements DashboardObserver, VolumeObserver{

    private SimulationModel model;
    private List<CoursSimulation> coursSimulations;
    private TurbinSimulation turbinSimultaion;
    private TempsSimulation tempsSimulation;
    private EnregistreurCSV printer;

    private List<SimulationUiObserver> simulationUiObservers = new ArrayList<>();

    public Simulation(SimulationParametres param){
        this.model = new SimulationModel(param);
    }

    public void startSimulation(){
        try {
            initServices();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startServices();
    }

    public void endSimulation(){
        for(SimulationUiObserver observer : this.simulationUiObservers){
            observer.endSimulation();
        }
        printer.fermer();
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
            if(this.tempsSimulation == null) {
                startSimulation();
            }
        }
        if (etat.getEtat() == Etat.FIN){
            endSimulation();
        }
    }

    // Volume Observer method

    @Override
    public void updateVolume(int volume){
        model.getReservoirModel().addVolume(volume);
    }

    // private methods :
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
    
    private void updateUI(){
        for(SimulationUiObserver observer : simulationUiObservers){
            observer.updateUi(tempsSimulation.getTemps());
        }
    }

}
