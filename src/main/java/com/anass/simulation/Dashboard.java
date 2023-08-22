package com.anass.simulation;

import com.anass.models.CoursModel;
import com.anass.models.EnsembleCoursModel;
import com.anass.models.EtatSimulation;
import com.anass.models.SimulationModel;
import com.anass.observers.DashboardObserver;

/**
 * Cette classe représente le tableau de bord de la simulation.
 * Elle permet d'accéder aux informations de la simulation et de mettre à jour
 * les débits des cours d'eau ainsi que l'état de la simulation.
 * Elle utilise un observateur pour communiquer avec d'autres parties du système.
 * 
 * @author Anass MEHDAOUI
 */
public class Dashboard {

    private EnsembleCoursModel ensembleCoursModel;
    private EtatSimulation etatSimulation;
    private DashboardObserver dashboardObserver;

    /**
     * Constructeur de la classe Dashboard.
     * 
     * @param simulation La simulation associée au tableau de bord.
     */
    public Dashboard(Simulation simulation) {
        SimulationModel model = simulation.getModel();
        this.ensembleCoursModel = model.getEnsembleCoursModel();
        this.etatSimulation = model.getEtatSimulation();
        setDashboardObserver(simulation);
    }

    /**
     * Définit l'observateur du tableau de bord.
     * 
     * @param observer L'observateur du tableau de bord à définir.
     */
    public void setDashboardObserver(DashboardObserver observer) {
        this.dashboardObserver = observer;
    }
    
    /**
     * Obtient le nombre de cours d'eau dans l'ensemble de cours.
     * 
     * @return Le nombre de cours d'eau.
     */
    public int getNbCours() {
        return ensembleCoursModel.getNbCours();
    }

    /**
     * Obtient un modèle de cours d'eau spécifique.
     * 
     * @param index L'index du cours d'eau.
     * @return Le modèle du cours d'eau.
     */
    public CoursModel getCours(int index) {
        return this.ensembleCoursModel.getCours(index);
    }

    /**
     * Met à jour le débit d'un cours d'eau spécifique.
     * 
     * @param index L'index du cours d'eau.
     * @param debit Le nouveau débit à attribuer au cours d'eau.
     */
    public void updateDebitCours(int index, int debit) {
        this.ensembleCoursModel.setDebit(index, debit);
    }

    /**
     * Met à jour l'état de la simulation.
     * 
     * @param etat Le nouvel état de la simulation.
     */
    public void updateEtatSimulation(EtatSimulation etat) {
        this.etatSimulation = etat;
        dashboardObserver.updateEtatsimulation(etat);
    }

    /**
     * Réinitialise la simulation en notifiant l'observateur.
     */
    public void reinitialiserSimulation() {
        dashboardObserver.reinitialiserSimulation();
    }

}
