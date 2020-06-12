package com.swisscom.regio.controller;

import com.swisscom.regio.model.Tournament;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class StartMatchController {

    @FXML
    public VBox box;

    private Tournament tournament;

    @FXML
    public void startTournament(ActionEvent actionEvent) {
        box.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/matchStages.fxml"));
        try {
            box.getChildren().add(loader.load());
            MatchStagesController matchStagesController = loader.getController();
            matchStagesController.initMatchStages(tournament);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initStartMatch(Tournament tournament) {
        this.tournament = tournament;
    }
}
