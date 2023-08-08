package com.anass.simulation;

import com.anass.models.SimulationModel;
import com.anass.models.SimulationParametres;

public class Simulation {
    SimulationModel model;

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

}
