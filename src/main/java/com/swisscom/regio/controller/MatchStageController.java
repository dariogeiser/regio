package com.swisscom.regio.controller;

import com.swisscom.regio.model.Match;
import com.swisscom.regio.model.Participant;
import com.swisscom.regio.model.Tournament;
import com.swisscom.regio.service.TournamentService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MatchStageController {

    @FXML
    public VBox box;

    private MatchStagesController matchStagesController;

    private int numberOfMatches = 0;
    private int counter = 0;
    private List<Match> matches = new ArrayList<>();

    public void initMatchStage(List<Participant> participants, MatchStagesController matchStagesController) {
        this.matchStagesController = matchStagesController;
        numberOfMatches = 0;
        Collections.shuffle(participants);
        if (participants.size() % 2 == 0) {
            for (int i = 0; i < participants.size(); i += 2) {
                numberOfMatches++;
                loadMatch(new Match(participants.get(i), participants.get(i + 1)));
            }
        } else {
            if (participants.size() != 1) {
                for (int i = 1; i < participants.size(); i += 2) {
                    numberOfMatches++;
                    loadMatch(new Match(participants.get(i), participants.get(i + 1)));
                }
                numberOfMatches++;
                loadMatch(new Match(participants.get(0)));
            } else {
                loadMatch(new Match(participants.get(0)));
            }
        }
    }

    public void loadMatch(Match match) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/match.fxml"));
        try {
            box.getChildren().add(loader.load());
            box.setSpacing(5);
            MatchController matchController = loader.getController();
            matchController.initMatch(match, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newWinner(Match match) {
        counter++;
        System.out.println("numberOfMatches: " + numberOfMatches);
        System.out.println("counter: " + counter);
        matches.add(match);
        if (counter == numberOfMatches) {
            counter = 0;
            matchStagesController.newWinners(matches);
        }
    }

}
