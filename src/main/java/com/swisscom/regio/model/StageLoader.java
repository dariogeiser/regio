package com.swisscom.regio.model;

import com.sun.javafx.iio.ios.IosDescriptor;
import com.swisscom.regio.controller.EditParticipantController;
import com.swisscom.regio.controller.ParticipantController;
import com.swisscom.regio.controller.TournamentOverviewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class StageLoader {
    private final String TOURNAMENTLIST = "view/tournamentList.fxml";
    private final String CREATETOURNAMENT = "view/createTournament.fxml";
    private final String TOURNAMENTOVERVIEW = "view/tournamentOverview.fxml";
    private final String EDITPARTICIPANT = "view/editParticipant.fxml";

    private static Stage stage;


    public StageLoader() {
    }

    public StageLoader(Stage stage) {
        this.stage = stage;
    }

    public void loadCreateTournament() {
        loadStage(CREATETOURNAMENT, "Tournament List", 350, 200);
    }

    public void loadTournamentList() {
        loadStage(TOURNAMENTLIST, "Create Tournament", 500, 400);
    }

    public void loadTournamentOverview(Tournament tournament) {
        FXMLLoader loader = loadStage(TOURNAMENTOVERVIEW, "Tournament Overview", 600, 550);
        TournamentOverviewController tournamentOverviewController = loader.<TournamentOverviewController>getController();
        tournamentOverviewController.initialTournament(tournament);
    }


    public void loadAddParticipant(Tournament tournament) {
        FXMLLoader loader =  loadStage(EDITPARTICIPANT, "Add Participant", 350, 200);
        EditParticipantController editParticipantController = loader.<EditParticipantController>getController();
        editParticipantController.initEditParticipant( tournament, null );
    }

    public void loadEditParticipant(Tournament tournament, Participant participant) {
        FXMLLoader loader =  loadStage(EDITPARTICIPANT, "Edit Participant", 350, 200);
        EditParticipantController editParticipantController = loader.<EditParticipantController>getController();
        editParticipantController.initEditParticipant( tournament, participant );
    }

    private FXMLLoader loadStage(String stageName, String title, int width, int height) {
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getClassLoader().getResource(stageName));
            stage.setTitle(title);
            stage.setResizable(false);
            Pane pane = loader.load();
            Scene scene = new Scene(pane, width, height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader;
    }
}

