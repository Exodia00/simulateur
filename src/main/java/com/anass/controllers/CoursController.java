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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class CoursController implements Initializable {

    @FXML private TextField debitFld;
    @FXML private Label label;

    private final String FXML_PATH = "/com/anass/fxml/cours.fxml";

    private CoursModel cours;

    public CoursController(CoursModel cours){
        this.cours = cours;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.label.setText("Cours N " + String.format("%02d", this.cours.getId()));
        this.debitFld.setText(Integer.toString(this.cours.getDebit()));
        this.debitFld.textProperty().addListener(DebitFldChangeListener);
    }

    public HBox getView() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        HBox box = (HBox) loader.load(getClass().getResourceAsStream(FXML_PATH));

        return box;
    }

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
