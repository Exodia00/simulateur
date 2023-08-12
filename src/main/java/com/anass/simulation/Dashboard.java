package com.anass.simulation;

import com.anass.models.CoursModel;
import com.anass.models.EnsembleCoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.observers.DashboardObserver;

public class Dashboard {

    private EnsembleCoursModel ensembleCoursModel;
    private EtatSimulation etatSimulation;
    private DashboardObserver dashboardObserver;

    public Dashboard(Simulation simulation){
        SimulationModel model = simulation.getModel();
        this.ensembleCoursModel = model.getEnsembleCoursModel();
        this.etatSimulation = model.getEtatSimulation();
    }

    public void setDashboardObserver(DashboardObserver observer){
        this.dashboardObserver = observer;
    }
    
    public int getNbCours(){
        return ensembleCoursModel.getNbCours();
    }

    public CoursModel getCours(int index){
        return this.ensembleCoursModel.getCours(index);
    }

    public void updateDebitCours(int index, int debit){
        this.ensembleCoursModel.setDebit(index, debit);
    }

    public void updateEtatSimulation(EtatSimulation etat){
        this.etatSimulation = etat;
    }



}