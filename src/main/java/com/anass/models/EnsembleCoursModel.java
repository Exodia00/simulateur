package com.anass.models;

import java.util.ArrayList;
import java.util.List;

public class EnsembleCoursModel {
    private ArrayList<CoursModel> coursList;
    private int nbCours = 0;

    public EnsembleCoursModel(ArrayList<CoursModel> cours){
        this.coursList = cours;
        this.nbCours = coursList.size();
    }

    public EnsembleCoursModel(List<Integer> debits){
        coursList = new ArrayList<>();
        for (int i=0; i<debits.size(); i++){
            this.coursList.add(new CoursModel(i, debits.get(i)));
            nbCours++;
        }
    }

    // Public Methods
    public CoursModel getCours(int index){
        return this.coursList.get(index);
    }

    public int getDebit(int index){
        return coursList.get(index).getDebit();
    }

    public void setDebit(int index, int debit){
        coursList.get(index).setDebit(debit);
    }

    public int getNbCours(){
        return this.nbCours;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("-------- Ensemble de Cours : \n");
        builder.append("===== [Nombre de Cours] : ").append(nbCours).append("\n");
        for(int i=0; i<nbCours; i++){
            builder.append(String.format("Cours N : %02d : ", nbCours)).append(this.coursList.get(i).getDebit()).append("\n");
        }
        
        return builder.toString();
    }

}
