package com.anass.models;

import java.util.List;

import com.anass.barrage.BarrageOperations;
import com.anass.models.EtatSimulation.Etat;

public class SimulationModel {
    
    private EnsembleCoursModel ensembleCoursModel;
    private ReservoirModel reservoirModel;
    private TurbinModel turbinModel;
    private SimulationRslt simulationRslt;
    private EtatSimulation etatSimulation;
    private int duree;


    public SimulationModel(SimulationParametres param){
        this.ensembleCoursModel = new EnsembleCoursModel((param.getDebitsCours()));
        this.reservoirModel = new ReservoirModel(param.getNiveauReservoir());
        this.turbinModel = new TurbinModel(param.getDebitsConduite());
        this.simulationRslt = new SimulationRslt();
        this.etatSimulation = new EtatSimulation(EtatSimulation.PAUSE);
        this.duree = param.getDuree();
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

    public TurbinModel getTurbinModel(){
        return this.turbinModel;
    }

    public void setEtatSimulation(EtatSimulation etat){
        this.etatSimulation.setEtat(etat.getEtat());
    }

    public ReservoirModel getReservoirModel(){
        return this.reservoirModel;
    }

    public int getDuree(){
        return this.duree;
    }

    public Etat getEtatTurbo(int heure){
        return BarrageOperations.calcEtatTurbo(reservoirModel, ensembleCoursModel, turbinModel.getDebit(heure));
    }

    public String getEtatTurboStr(int heure){
        Etat etat = getEtatTurbo(heure);

        if (etat==Etat.ACTIVE){ return "A marche";}
        if (etat==Etat.PAUSE){  return "Arret";}
        if (etat==Etat.FIN){    return "Fin";}

        return "";
    }




}
