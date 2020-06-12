package com.swisscom.regio;

import com.swisscom.regio.model.StageLoader;
import com.swisscom.regio.service.TournamentService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                TournamentService.getInstance().closeConnection();
                Platform.exit();
                System.exit(0);
            }
        });
        StageLoader stageLoader = new StageLoader(primaryStage);
        stageLoader.loadTournamentList();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
