package com.anass.models;

import com.anass.barrage.BarrageOperations;
import com.anass.models.EtatSimulation.Etat;

/**
 * Cette class représente le modèle de la simulation
 * 
 * <p>
 * Cette class encapsule la totalité des modéles des composants de la simulation, et contient des imformations
 * sur les cours d'eau, le réservoir, le turbo-alternateur, l'état de la simulation, et sa durée.
 * </p>
 * 
 * @author Anass MEHDAOUI
 */
public class SimulationModel {
    
    /** L'ensemble des modèles des cours d'eau*/
    private EnsembleCoursModel ensembleCoursModel;
    /** Le modèle du réservoir  */
    private ReservoirModel reservoirModel;
    /** Modèle du turbo-altérnateur */
    private TurbinModel turbinModel;
    /** Modèle du résultat de la simulation */
    private SimulationRslt simulationRslt;
    /** Modèle de l'état de la simulation */
    private EtatSimulation etatSimulation;
    /** Durée de la simulation */
    private int duree;

    /**
     * Constructeur à partir des paramètres de la simulation.
     * 
     * @param param SimulationParameters
     */
    public SimulationModel(SimulationParametres param){
        this.ensembleCoursModel = new EnsembleCoursModel((param.getDebitsCours()));
        this.reservoirModel = new ReservoirModel(param.getNiveauReservoir());
        this.turbinModel = new TurbinModel(param.getDebitsConduite());
        this.simulationRslt = new SimulationRslt();
        this.etatSimulation = new EtatSimulation(EtatSimulation.PAUSE);
        this.duree = param.getDuree();
    } 

    /**
     * Génère une représentation textuelle de la simulation.
     *
     * @return Une chaîne de caractères décrivant la simulation.
     */
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


    /**
     * Obtient l'ensemble des modèles des cours d'eau
     * 
     * @return ensembleCoursModel EnsembleCoursModel
     */
    public EnsembleCoursModel getEnsembleCoursModel() {
        return ensembleCoursModel;
    }

    /**
     * Renvoie l'état de la simulation
     * 
     * @return etatSimulation
     */
    public EtatSimulation getEtatSimulation() {
        return etatSimulation;
    }

    /**
     * Obtient le modèle du turbo-alternateur
     * 
     * @return turbinModel
     */
    public TurbinModel getTurbinModel(){
        return this.turbinModel;
    }

    /**
     * Modifie l'état de la simulation
     * <p>
     * Cette méthod n'instancie pas une nouvelle instance de EtatSimulation, mais utilise EtatSimulation.setEtat().
     * 
     * @param etat Etat
     */
    public void setEtatSimulation(EtatSimulation etat){
        this.etatSimulation.setEtat(etat.getEtat());
    }

    /**
     * Renvoie le modèle du réservoir
     * 
     * @return reservoirModel
     */
    public ReservoirModel getReservoirModel(){
        return this.reservoirModel;
    }

    /**
     * Renvoie la durée de la simulation
     * 
     * @return durée
     */
    public int getDuree(){
        return this.duree;
    }

    /**
     * Renvoie l'état du barrage à un instant donnée.
     * 
     * @param heure
     * 
     * @return etat
     */
    public Etat getEtatTurbo(int heure){
        return BarrageOperations.calcEtatTurbo(reservoirModel, ensembleCoursModel, turbinModel.getDebit(heure));
    }

    /**
     * Renvoie une représentation textuelle de l'état du turbo-altérnateur
     * 
     * @param heure
     * @return une chaine de caractère décrivant l'état du turbo-altérnateur
     */
    public String getEtatTurboStr(int heure){
        Etat etat = getEtatTurbo(heure);

        if (etat==Etat.ACTIVE){ return "A marche";}
        if (etat==Etat.PAUSE){  return "Arret";}
        if (etat==Etat.FIN){    return "Fin";}

        return "";
    }




}
