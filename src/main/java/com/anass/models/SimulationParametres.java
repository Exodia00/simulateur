package com.anass.models;

import java.util.ArrayList;

/**
 * Cette classe représente les paramètres de la simulation du barrage hydroélectrique.
 * Elle contient les valeurs pour le niveau du réservoir, la durée de la simulation,
 * le nombre de cours d'eau, les débits de conduite et les débits des cours d'eau.
 * 
 * @author Anass MEHDAOUI
 */
public class SimulationParametres {
    
    private int niveauReservoir, duree, nbCours;
    private ArrayList<Integer> debitsConduite, debitsCours;

    /**
     * Constructeur par défaut. Initialise les paramètres avec des valeurs par défaut.
     */
    public SimulationParametres(){
        this.niveauReservoir = 0;
        this.duree = 0;
        this.nbCours = 0;
        this.debitsConduite = new ArrayList<>();
        this.debitsCours = new ArrayList<>();
    }

    /**
     * Constructeur de copie. Crée une instance de SimulationParametres en copiant les valeurs d'un autre objet.
     * 
     * @param param Les paramètres de la simulation à copier.
     */
    public SimulationParametres(SimulationParametres param){
        this.niveauReservoir = param.getNiveauReservoir();
        this.duree = param.getDuree();
        this.nbCours = param.getNbCours();
        this.debitsConduite = param.getDebitsConduite();
        this.debitsCours = param.getDebitsCours();
    }

    // Getters / Setters

    public int getNiveauReservoir() {
        return niveauReservoir;
    }

    public void setNiveauReservoir(int niveauReservoir) {
        this.niveauReservoir = niveauReservoir;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNbCours() {
        return nbCours;
    }

    public void setNbCours(int nbCours) {
        this.nbCours = nbCours;
    }

    public ArrayList<Integer> getDebitsConduite() {
        return debitsConduite;
    }

    public void setDebitsConduite(ArrayList<Integer> debitsConduite) {
        this.debitsConduite = debitsConduite;
    }

    public ArrayList<Integer> getDebitsCours() {
        return debitsCours;
    }

    public void setDebitsCours(ArrayList<Integer> debitsCours) {
        this.debitsCours = debitsCours;
    }

    // Public Methods

    /**
     * Renvoie une chaîne de caractères représentant les paramètres de la simulation.
     * 
     * @return La représentation en chaîne de caractères des paramètres.
     */
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("----------------\nParametres de la simulation :\n");
        builder.append("Niveau du réservoir : ").append(this.niveauReservoir).append("\n");
        builder.append("Durée de la simulation : ").append(this.duree).append("\n");
        builder.append("-----\nDebits de la conduite-----\n");
        for(int i=0; i<24; i++){
            builder.append(String.format("%02d", i)).append(":00 H : ").append(this.debitsConduite.get(i)).append("m3/h ||");
        }
        builder.append("\n-----\n Nombre de cours d'eau : ").append(this.nbCours).append(" ------\n");
        for(int i=0; i<this.nbCours; i++){
            builder.append("Cours N° ").append(String.format("%02d", i)).append(" : ").append(this.debitsCours.get(i)).append("m3/h ||");
        }

        return builder.toString();
    }
}
