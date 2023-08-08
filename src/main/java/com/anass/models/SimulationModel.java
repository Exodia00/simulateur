package com.anass.models;

public class SimulationModel {
    
    private EnsembleCoursModel ensembleCoursModel;
    private ReservoirModel reservoirModel;
    private TurbinModel turbinModel;
    private SimulationRslt simulationRslt;
    private EtatSimulation etatSimulation;


    public SimulationModel(SimulationParametres param){
        this.ensembleCoursModel = new EnsembleCoursModel((param.getDebitsCours()));
        this.reservoirModel = new ReservoirModel(param.getNiveauReservoir());
        this.turbinModel = new TurbinModel(param.getDebitsConduite());
        this.simulationRslt = new SimulationRslt();
        this.etatSimulation = new EtatSimulation(EtatSimulation.PAUSE);
    } 


    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("---- Simulation Model\n");
        builder.append(etatSimulation.toString());
        builder.append(reservoirModel.toString());
        builder.append(turbinModel.toString());
        builder.append(ensembleCoursModel.toString());
        builder.append(simulationRslt.toString());

        return builder.toString();
    }


    public EnsembleCoursModel getEnsembleCoursModel() {
        return ensembleCoursModel;
    }

    public EtatSimulation getEtatSimulation() {
        return etatSimulation;
    }




}
