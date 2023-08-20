package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.anass.models.SimulationParametres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Cette class agit comme controlleur de la vue de préparation de la simulation.
 * <p>
 * Ce controlleur est chargée de récuperer les differents paramètres de la simulation.
 * 
 * @author Anass MEHDAOUI
 */
public class SetupController implements Initializable{

    // FXML 
    @FXML private FlowPane conduiteBox;
    @FXML private FlowPane debitBox;
    @FXML private TextField dureeFld;
    @FXML private Button lancerBtn;
    @FXML private TextField nbCoursFld;
    @FXML private TextField niveauReservoirFld;
    @FXML private Label alert;
    
    /**
     * La fenètre 
     */
    private Stage stage;
    /**
     * Le chemin vers le fichier FXML
     */
    private String FXML_PATH = "/com/anass/fxml/setup.fxml";

    /**
     * Liste qui contient les champs des texts des débits de la conduites pendant la journée
     */
    ObservableList<TextField> conduiteFlds = FXCollections.observableArrayList();
    
    /**
     * Liste que contient les débits de chaque cours d'eau
     */
    ObservableList<HBox> debitHboxes = FXCollections.observableArrayList();

    /**
     * Initialise la vue en créant 24 champs de texte pour les différentes valeurs des débits de la conduite pendant une journée
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        for(int i=0; i<24; i++){
            HBox conduiteIBox = creerConduiteIBox(i);          
            this.conduiteBox.getChildren().add(conduiteIBox);  
            this.conduiteFlds.add((TextField)conduiteIBox.getChildren().get(1));  
        }
    } 

    /**
     * Récepteur d'action de click sur le bouton de démarrage.
     */
    @FXML public void lancerSimulation(){

        try{
            lancerSimulationStage(this.getSimulationParametres());
        }catch (IllegalArgumentException e){ return;}
    }  

    /**
     * Récepteur d'action de saisie dans le champs de texte de nombre de cours d'eau
     * <p>
     * Si la valeur saise n'est pas valide (n'est pas une valeur numérique), un message d'erreur est affiché
     */
    @FXML public void handleNbCoursChanged(){
        // Dynamically create debit input boxes
        try{
            int nbCours = Integer.parseInt(nbCoursFld.getText());
            alerter("");    // Reset any potential error message
            updateDebitBox(nbCours);
        }catch (NumberFormatException e){
            alerter("Le nombre de cours n'est pas valide");
        }
    }

    /**
     * charge la vue, et spécifie cette instance comme controlleur.
     * 
     * @throws IOException
     */
    public void start() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(this.FXML_PATH));
        this.stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Nouvelle Simulation");
        stage.show();
    }

    /**
     * Lance la simulation, en affichant la fenêtre de la simulation
     * 
     * @param params SimulationParameters, les paramètres de la simulation que l'utilisateur à spécifier
     */
    private void lancerSimulationStage(SimulationParametres params){
        SimulationController simulationController = new SimulationController(params);
        try {
            simulationController.start();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cette méthode met à jour le nombre de champs des cours d'eau selon la valeur saisie par l'utilisateur
     * <p>
     * cette méthode ajoute ou supprime des éléments de contrôle jusqu'à ce qu'ils correspondent au nombre choisi par l'utilisateur
     * 
     * @param nbCours : Le nombre de cours d'eau
     */
    private void updateDebitBox(int nbCours){
        int nbCoursActu = debitHboxes.size();
        int diff = nbCoursActu - nbCours;

        if( diff==0 ) return;

        if( diff < 0 ){
            for( int i=0; i<-diff; i++){
                HBox box = creerDebitIBox();
                debitBox.getChildren().add(box);
                debitHboxes.add(box);
            }
        }else if(diff>0){
            for( int j=0; j<diff; j++){
                debitHboxes.remove(nbCours);
                debitBox.getChildren().remove(nbCours);
            }
        }
    }

    /**
     * cette méthode crée le groupe de contrôle pour le débit de la conduite dans la ième heure dans la journée
     * 
     * @param i indice de l'heure
     * @return HBox Hbox qui contient une étiquete et un champs de texte.
     */
    private HBox creerConduiteIBox(int i){
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        Label label = new Label(String.format("%02d", i) + ":00H");
        TextField txtFld = new TextField();
        txtFld.setPrefWidth(100);



        box.getChildren().addAll(label, txtFld);        

        return box;
    }

    /**
     * cette méthode crée un groupe de contrôle pour le débit d'un cours d'eau    
     * 
     * @return HBox box contenant une étiquette et un champs de texte.
     */
    private HBox creerDebitIBox(){
        HBox box = new HBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        int position = this.debitHboxes.size() + 1;

        Label label = new Label("Debit N" + String.format("%02d", position) + " : ");
        TextField txtFld = new TextField();
        txtFld.setPrefWidth(100);

        box.getChildren().addAll(label, txtFld);        

        return box;
    }

    /**
     * Cette méthode permet d'afficher un message d'erreur à l'utilisateur
     * 
     * @param message String, si le message est une chaine de caractère vide, rien n'est affiché.
     */
    private void alerter(String message){
        this.alert.setText(message);
    }

    /**
     * Cette méthode permet de récuperer les débits de la conduite pendant la journée à partir de la vue.
     * 
     * @return debits : ArrayList : liste des débits pendant la journée
     * @throws NumberFormatException : Si la valeure saisie dans l'un des champs de texte n'est pas une valeur numérique
     */
    private ArrayList<Integer> getDebitsConduite() throws NumberFormatException{
        ArrayList<Integer> debitsConduite = new ArrayList<Integer>();
        for (TextField tf : conduiteFlds){
                debitsConduite.add(Integer.parseInt(tf.getText()));
        }
        return debitsConduite;
    }

     /**
     * Cette méthode permet de récuperer les débits des cours d'eau
     * 
     * @return debits : ArrayList : liste des débits
     * @throws NumberFormatException : Si la valeure saisie dans l'un des champs de texte n'est pas une valeur numérique
     */
    private ArrayList<Integer> getDebitsCours(){
        ArrayList<Integer> debitsCours = new ArrayList<Integer>();
        for (HBox box : this.debitHboxes){
            debitsCours.add(Integer.parseInt(((TextField) box.getChildren().get(1)).getText()));
        }
        return debitsCours;
    }

    /**
     * Cette méthode permet de convertir les différent paramètres saisies par l'utilisateur en un objet de type SimulationParameters
     * <p>
     * Si l'une des valeurs saisis n'est pas valide, un méssage d'erreur correspendant est affiché.
     * 
     * @returns parameters SimulationParameters : L'objet représentant les paramètres de la simulation
     * @throws IllegalArgumentException : Si l'une des valeurs n'est pas valide
     */
    private SimulationParametres getSimulationParametres() throws IllegalArgumentException{
        SimulationParametres parametres = new SimulationParametres();
        // nb Cours :
        parametres.setNbCours(Integer.parseInt(this.nbCoursFld.getText()));
        // debits conduite
        try{
            parametres.setDebitsConduite(getDebitsConduite());
        }catch(NumberFormatException e){
            alerter("Valeurs des debits de la conduites pendant la journée ne sont pas valides.");
            throw new IllegalArgumentException();
        }
        // Debit cours :
        try{
            parametres.setDebitsCours(getDebitsCours());
        }catch(NumberFormatException e){
            alerter("Valeurs des debits des cours ne sont pas valides.");
            throw new IllegalArgumentException();
        }
        // Niveau du reservoir
        try{
            parametres.setNiveauReservoir(Integer.parseInt(this.niveauReservoirFld.getText()));
        }catch(NumberFormatException e){
            alerter("Valeur du niveau du reservoir n'est pas valide");
            throw new IllegalArgumentException();
        }
        // Duree de la simulation :
        try{
            parametres.setDuree(Integer.parseInt(this.dureeFld.getText()));
        }catch(NumberFormatException e){
            alerter("Valeur de la duree de la simulation n'est pas valide");
            throw new IllegalArgumentException();
        }
        return parametres;
    }
}
