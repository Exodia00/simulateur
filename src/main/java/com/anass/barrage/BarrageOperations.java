package com.anass.barrage;

import com.anass.models.CoursModel;
import com.anass.models.EnsembleCoursModel;
import com.anass.models.ReservoirModel;
import com.anass.models.TurbinModel;
import com.anass.models.EtatSimulation.Etat;
import com.anass.simulation.Simulation;

public class BarrageOperations {
    
    public static Etat calcEtatTurbo(ReservoirModel reservoir, EnsembleCoursModel cours, int debitMin){
        int niveau = reservoir.getVolume();
        int volumeEntree = cours.getDebitTotal() * 60*60;
        int volumeSortie = debitMin*60*60;
        return (niveau + volumeEntree > volumeSortie ? Etat.ACTIVE : Etat.PAUSE); 
    }

    public static int calculerVolume(CoursModel cours){
        return cours.getDebit() * 60 * 15 ;
    }

    public static int calculerVolume(TurbinModel turbin){
        return turbin.getDebit(Simulation.getHeure()) * 60 * 60 ;
    }
}
