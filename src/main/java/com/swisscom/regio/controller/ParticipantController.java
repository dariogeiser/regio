package com.swisscom.regio.controller;

import com.swisscom.regio.model.Participant;
import com.swisscom.regio.model.StageLoader;
import com.swisscom.regio.model.Tournament;
import com.swisscom.regio.service.TournamentService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class ParticipantController {
    @FXML
    public Label title;
    @FXML
    public Label participants;
    @FXML
    public Label size;
    @FXML
    public ListView participantsListView;
    @FXML
    public Button fillParticipantButton;
    @FXML
    public Button addParticipantButton;


    private StageLoader stageLoader;
    private TournamentService tournamentService;
    private Tournament tournament;
    private static int counter = 0;
    private List<Participant> participantsList;

    public ParticipantController() {
        this.stageLoader = new StageLoader();
        this.tournamentService = TournamentService.getInstance();
    }

    public void initialParticipants(Tournament tournament) {
        this.tournament = tournament;
        title.setText(tournament.getTitle());
        size.setText(String.valueOf(tournament.getSize()));
        participants.setText("0");
        loadParticipants();
    }

    @FXML
    public void addParticipant(ActionEvent actionEvent) {
        stageLoader.loadAddParticipant(tournament);
    }

    @FXML
    public void fillRandom(ActionEvent actionEvent) {
        tournamentService.createParticipant(new Participant("Participant# " + counter, true, true), tournament.getId());
        counter++;
        loadParticipants();
    }

    @FXML
    public void editParticipant(ActionEvent actionEvent) {
        if (participantsListView.getSelectionModel().getSelectedItem() != null) {
            Participant participant = tournamentService.getParticipant((String) participantsListView.getSelectionModel().getSelectedItem());
            tournamentService.deletePlayer(participant.getId());
            stageLoader.loadEditParticipant(tournament, participant);
        }
    }

    @FXML
    public void removeParticipant(ActionEvent actionEvent) {
        if (participantsListView.getSelectionModel().getSelectedItem() != null) {
            Participant participant = tournamentService.getParticipant((String) participantsListView.getSelectionModel().getSelectedItem());
            tournamentService.deletePlayer(participant.getId());
        }
        loadParticipants();
    }

    private void loadParticipants() {
        participantsList = tournamentService.getParticipants(tournament);
        List<String> names = new ArrayList();
        for (Participant participant : participantsList) {
            names.add(participant.getName());
        }
        participantsListView.setItems(FXCollections.observableArrayList(names));
        participants.setText(String.valueOf(participantsListView.getItems().size()));
        if (Integer.parseInt(participants.getText()) == Integer.parseInt(size.getText())) {
            fillParticipantButton.setDisable(true);
            addParticipantButton.setDisable(true);
        } else {
            fillParticipantButton.setDisable(false);
            addParticipantButton.setDisable(false);
        }
    }
}
