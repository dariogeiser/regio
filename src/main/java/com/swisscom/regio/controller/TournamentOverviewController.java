package com.swisscom.regio.controller;

import com.swisscom.regio.model.Tournament;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TournamentOverviewController {

    @FXML
    public Label game;
    @FXML
    public Label title;
    @FXML
    public ParticipantController participantTabController;
    @FXML
    public StartMatchController startMatchController;

    private Tournament tournament;

    public void initialTournament(Tournament tournament) {
        this.tournament = tournament;
        game.setText(tournament.getGame().getName());
        title.setText(tournament.getTitle());
        participantTabController.initialParticipants(tournament);
        startMatchController.initStartMatch(tournament);
    }


    @FXML
    public void exportData(ActionEvent actionEvent) {
    }
}
