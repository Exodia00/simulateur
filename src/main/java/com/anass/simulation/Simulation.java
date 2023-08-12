package com.anass.simulation;

import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.models.SimulationParametres;
import com.anass.observers.DashboardObserver;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

public class Simulation implements DashboardObserver{

    private SimulationModel model;

    public Simulation(SimulationParametres param){
        this.model = new SimulationModel(param);
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



    // Dashboard Observer methods

    @Override
    public void updateDebitCours(int index, int value) {
        this.model.getEnsembleCoursModel().setDebit(index, value);
    }


    @Override
    public void updateEtatsimulation(EtatSimulation etat) {
        this.model.setEtatSimulation(etat);
    }

}
