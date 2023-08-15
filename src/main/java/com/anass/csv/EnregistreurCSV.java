package com.anass.csv;

import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.anass.barrage.TempsOperations;
import com.anass.models.SimulationModel;

public class EnregistreurCSV {
    
    private static final String tempPath = "temp.csv";

    private CSVPrinter printer;

    public EnregistreurCSV() throws IOException{
        printer = new CSVPrinter(new FileWriter(tempPath), CSVFormat.DEFAULT);
        printer.printRecord("Temps", "Volume de la retenue", "Debit conduite", "Debit entrant à la retenue", "Etat du turbo-alternateur");
    }

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
