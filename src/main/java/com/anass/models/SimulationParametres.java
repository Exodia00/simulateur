package com.anass.models;

import java.util.ArrayList;

public class SimulationParametres {
    
    private int niveauReservoir, duree, nbCours;
    private ArrayList<Integer> debitsConduite, debitsCours;

    public SimulationParametres(){
        this.niveauReservoir = 0;
        this.duree = 0;
        this.nbCours = 0;
        this.debitsConduite = new ArrayList<>();
        this.debitsCours = new ArrayList<>();
    }

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
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        builder.append("----------------\nParametres de la simulation :\n");
        builder.append("Niveau du reservoir : ").append(this.niveauReservoir).append("\n");
        builder.append("Durée de la simulation : ").append(this.duree).append("\n");
        builder.append("-----\nDebits de la conduite-----\n");
        for(int i=0; i<24; i++){
            builder.append(String.format("%02d", i)).append(":00 H : ").append(this.debitsConduite.get(i)).append("m2/s ||");
        }
        builder.append("\n-----\n Nombre de coures : ").append(this.nbCours).append(" ------\n");
        for(int i=0; i<this.nbCours; i++){
            builder.append("Cours N :").append(String.format("%02d", i)).append(" : ").append(this.debitsCours.get(i)).append("m2/s ||");
        }

        return builder.toString();
    }

    

}
