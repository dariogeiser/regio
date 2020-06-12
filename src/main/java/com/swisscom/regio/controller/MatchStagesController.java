package com.swisscom.regio.controller;

import com.swisscom.regio.model.Match;
import com.swisscom.regio.model.Participant;
import com.swisscom.regio.model.Tournament;
import com.swisscom.regio.service.TournamentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchStagesController {

    @FXML
    public TabPane stageTabPane;

    private Tournament tournament;


    private static int counter = 1;

    private TournamentService tournamentService;
    private List<Participant> participants;

    public MatchStagesController() {
        this.tournamentService = TournamentService.getInstance();
        participants = new ArrayList<>();
    }


    @FXML
    public void initialize() {

    }

    public void initMatchStages(Tournament tournament) {
        this.tournament = tournament;
        participants = this.tournamentService.getParticipants(tournament);
        loadNewTab(participants);
    }

    public void loadNewTab(List<Participant> participants) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/matchStage.fxml"));
        try {
            Tab tab = new Tab("Stage " + counter);
            tab.setContent(loader.load());
            stageTabPane.getTabs().add(tab);
            counter++;
            MatchStageController matchStageController = loader.getController();
            matchStageController.initMatchStage(participants, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newWinners(List<Match> matches) {
        List<String> winners = new ArrayList<>();
        for (Match match : matches) {
            winners.add(match.getWinner().getName());
        }
        for (int i = 0; i < participants.size(); i++) {
            if (!winners.contains(participants.get(i).getName())) {
                participants.remove(participants.get(i));
                i--;
            }
        }
        loadNewTab(participants);
    }
}
