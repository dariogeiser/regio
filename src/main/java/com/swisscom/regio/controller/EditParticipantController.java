package com.swisscom.regio.controller;

import com.swisscom.regio.model.Participant;
import com.swisscom.regio.model.StageLoader;
import com.swisscom.regio.model.Tournament;
import com.swisscom.regio.service.TournamentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditParticipantController {
    @FXML
    public TextField inputName;
    @FXML
    public RadioButton RBPlayer;
    @FXML
    public ToggleGroup group1;
    @FXML
    public RadioButton RBTeam;
    @FXML
    public CheckBox CBTemporary;
    @FXML
    public Label errorMessage;

    private StageLoader stageLoader;
    private TournamentService tournamentService;
    private Tournament tournament;
    private Participant participant;

    public EditParticipantController() {
        this.stageLoader = new StageLoader();
        this.tournamentService = TournamentService.getInstance();
    }

    public void initEditParticipant(Tournament tournament, Participant participant){
        this.participant = participant;
        this.tournament = tournament;
        if(participant != null) {
            inputName.setText(participant.getName());
            CBTemporary.setSelected(participant.isTemporary());
            if(participant.isTeam()) {
                RBTeam.setSelected(true);
            } else {
                RBPlayer.setSelected(true);
            }
        }
    }

    @FXML
    public void safe(ActionEvent actionEvent) {
        Participant participant = new Participant(inputName.getText(), RBTeam.isSelected(), CBTemporary.isSelected());
        tournamentService.createParticipant(participant, tournament.getId());
        stageLoader.loadTournamentOverview(tournament);
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        stageLoader.loadTournamentOverview(tournament);
    }

}
