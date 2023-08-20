package com.anass.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.anass.models.CoursModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Contrôle l'affichage d'un flux d'eau dans le tableau de bord de simulation
 * 
 * @author Anass MEHDAOUI
 */
public class CoursController implements Initializable {

    /**
     * Le champ de texte du flux
     */
    @FXML private TextField debitFld;
    /**
     * l'étiquette du débit du cours d'eau
     */
    @FXML private Label label;
    /**
     * Chemin vers le fichier fxml
     */
    private final String FXML_PATH = "/com/anass/fxml/cours.fxml";
    /**
     * L'objet representant le cours d'eau
     */
    private CoursModel cours;

    /**
     * Constructeur à partir d'un modèle de cours d'eau
     * 
     * @param cours CoursModel
     */
    public CoursController(CoursModel cours){
        this.cours = cours;
    }

    /**
     * Initialize la vue, et invoqué automatiquement lors de l'initialisation de la vue
     * <p>
     * Elle fixe l'étiquette à l'identifiant du cours d'eau et associe la propriété text du champ
     * de texte à un écouteur.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.label.setText("Cours N " + String.format("%02d", this.cours.getId()));
        this.debitFld.setText(Integer.toString(this.cours.getDebit()));
        this.debitFld.textProperty().addListener(DebitFldChangeListener);
    }

    /**
     * Charge et renvoie la vue
     * <p>
     * La vue renvoyé possede l'instance qui fait appel à la méthode comme constructeur
     * 
     * @return HBox hbox qui contient l'étiquette et le champs de text.
     * 
     */
    public HBox getView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        HBox box = (HBox) loader.load(getClass().getResourceAsStream(FXML_PATH));

        return box;
    }

    /**
     * Ecouteur pour la valeur textuelle du champs de text
     * <p>
     * Lorsque la valeur du champs change, la nouvelle valeur est validé d'abord
     * <ul>
     *  <li> si la valeur est valide, la modification est appliqué au modèle de la simulation</li>
     *  <li> Si la valeur n'est pas valide, la modification du champs de text est rejetée.</li>
     * </ul>
     */
    ChangeListener<String> DebitFldChangeListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<?extends String> observable, String ancien, String nouveau){
            try{
                cours.setDebit(Integer.parseInt(nouveau));
            }catch (NumberFormatException e){
                debitFld.setText(ancien);
            }
        }
    };


}
