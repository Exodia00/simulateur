package com.anass.barrage;

import com.anass.models.CoursModel;
import com.anass.models.EnsembleCoursModel;
import com.anass.models.ReservoirModel;
import com.anass.models.SimulationModel;
import com.anass.models.TurbinModel;
import com.anass.models.EtatSimulation.Etat;
import com.anass.simulation.Simulation;

/**
 * Contient des opérations logiques de barrage 
 * 
 * @author: Anass MEHDAOUI
 */
public class BarrageOperations {
    
    /**
     * Permet de déterminer l'état du turboalternateur
     * <p>
     * Prend en compte le volume d'eau dans le réservoir du barrage, le débit total des 
     * cours d'eau et le débit minimum requis par le turbo-alternateur.
     * <p>
     * Le debit et converti en volume total pendant une heure
     * 
     * 
     * @param reservoir Objet du type ReservoirModel representant le reservoir du barrage hydro-electrique.
     * @param cours EnsembleCoursModel l'ensemble des cours d'eau remplissant le resrvoir
     * @param debitMin Integer le debit requis par le turbo-alternateur
     * @return etat Etat l'etat du turbo-alternateur, 
     * 
     */
    public static Etat calcEtatTurbo(ReservoirModel reservoir, EnsembleCoursModel cours, int debitMin){
        int niveau = reservoir.getVolume();
        int volumeEntree = cours.getDebitTotal() * 60*60;
        int volumeSortie = debitMin*60*60;
        return (niveau + volumeEntree > volumeSortie ? Etat.ACTIVE : Etat.PAUSE); 
    }

    /**
     * Permet de calculer le volume total d'eau fourni par un objet de type CoursModel pendant une quart d'heure
     * 
     * @param cours CoursModel : L'objet representant le cours d'eau
     * @return volume Integer : Le volume d'eau pendant un quart d'heure
     */
    public static int calculerVolume(CoursModel cours){
        return cours.getDebit() * 60 * 15 ;
    }

    /**
     * détermine le volume d'eau nécessaire pour que le turbo-alternateur fonctionne pendant un intervalle de temps 
     * d'une heure
     * 
     * @param turbin TurbinModel : L'objet representant le turbo-alternateur du barrage 
     * @param h Integer : indice de l'heure du calcule sur 24
     * @return volume Integer : Le volume d'eau requis 
     */
    public static int calculerVolume(TurbinModel turbin, int h){
        return turbin.getDebit(h) * 60 * 60 ;
    }

    /**
     * Permet de calculer le niveau d'eau dans le reservoir on se basant sur le volume maximal, l'hauteur maximale 
     * et le volume d'eau dans le reservoir
     * @param model SimulationModel : L'objet representant l'ensemble des donees de la simulations.
     * @param hauteurMax Integer, l'hauteur maximale du réservoir
     * @return hauteur Double : L'hauteur de l'eau dans le reservoir
     * 
     */
    public static double calculerHauteurReservoir(SimulationModel model, int hauteurMax){

        double maxVolume = model.getReservoirModel().getMaxVolume();
        double volume = model.getReservoirModel().getVolume();
        return (volume / maxVolume) * hauteurMax;             

    }
}
