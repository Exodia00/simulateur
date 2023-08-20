package com.anass.csv;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.anass.barrage.TempsOperations;
import com.anass.models.SimulationModel;

/**
 * Cette class est responsable de la journalisation des données de la simulation et de son enregistrement dans un fichier csv
 * 
 * @author Anass MEHDAOUI
 */
public class EnregistreurCSV {
    
    /**
     * Chemin vers le fichier temporaire de l'enregistrement
     */
    private static final String tempPath = "temp.csv";

    /**
     * Instance de CSVPrinter, permet de créer et d'enregistrer les données dans un fichier sous format csv
     */
    private CSVPrinter printer;

    /**
     * Constructeur sans argument
     * 
     * <p>
     * 
     * Créer un nouveau fichier temporaire et rensigne la première ligne
     * 
     * @throws IOException
     */
    public EnregistreurCSV() throws IOException{
        printer = new CSVPrinter(new FileWriter(tempPath), CSVFormat.DEFAULT);
        printer.printRecord("Temps", "Volume de la retenue", "Debit conduite", "Debit entrant à la retenue", "Etat du turbo-alternateur");
    }

    /**
     * Cette méthode permet d'ajouter une ligne dans le fichier d'enregistrement temporaire.
     * 
     * @param temps Integer[] : l'instant de l'enregistrement
     * @parma model SimulationModel : le modèle de la simulation à l'instant de l'enregistrement
     */
    public void ajouterRecord(Integer[] temps, SimulationModel model) throws IOException{
        printer.printRecord(
            TempsOperations.toString(temps),
            model.getReservoirModel().getVolume(),
            model.getTurbinModel().getDebit(temps[0]),
            model.getEnsembleCoursModel().getDebitTotal(),
            model.getEtatTurbo(temps[0]));
    }

    public void fermer(){
        try {
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
