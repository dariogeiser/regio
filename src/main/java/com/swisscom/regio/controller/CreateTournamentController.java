package com.swisscom.regio.controller;

import com.swisscom.regio.model.Game;
import com.swisscom.regio.model.StageLoader;
import com.swisscom.regio.model.Tournament;
import com.swisscom.regio.service.TournamentService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableArrayBase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class CreateTournamentController {


    @FXML
    public TextField inputTitle;
    @FXML
    public ComboBox inputGame;
    @FXML
    public TextField inputSize;
    @FXML
    public Label errorMessage;


    private TournamentService tournamentService;
    StageLoader stageLoader;

    public CreateTournamentController() {
        stageLoader = new StageLoader();
        tournamentService = TournamentService.getInstance();
    }

    @FXML
    public void initialize() {
        List<Game> games = tournamentService.getGames();
        ObservableList observableGames = FXCollections.observableArrayList();
        for (Game game : games) {
            observableGames.add(game.getName());
        }
        inputGame.setItems(observableGames);

    }
    @FXML
    public void createTournament(ActionEvent actionEvent) {
        boolean error = false;
        if (inputSize.getText().trim().isEmpty()) {
            errorMessage.setText("Please enter a valid Size");
        }

        if (Integer.parseInt(inputSize.getText()) < 2 || Integer.parseInt(inputSize.getText()) > 100) {
            error = true;
            errorMessage.setText("Please enter a valid Size");
        }

        if (inputGame.getSelectionModel().isEmpty()) {
            error = true;
            errorMessage.setText("Please select a Game");
        }

        if (inputTitle.getText().trim().isEmpty()) {
            error = true;
            errorMessage.setText("Please enter a valid Title");
        }

        if (!error) {
            tournamentService.createTournament(
                    inputTitle.getText().trim(),
                    inputGame.getSelectionModel().getSelectedItem().toString(),
                    Integer.parseInt(inputSize.getText())
            );
            stageLoader.loadTournamentList();

        }
    }
}
