package com.swisscom.regio.controller;

import com.swisscom.regio.model.Match;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class MatchController {

    @FXML
    public Label player2;
    @FXML
    public HBox player2Box;
    @FXML
    public Label player1;
    @FXML
    public HBox player1Box;

    private Match match;
    private MatchStageController matchStageController;


    public void initMatch(Match match, MatchStageController matchStageController) {
        this.match = match;
        this.matchStageController = matchStageController;
        if (match.getParticipant2() != null) {
            player1.setText(match.getParticipant1().getName());
            player2.setText(match.getParticipant2().getName());
        } else {
            player1.setText(match.getParticipant1().getName());
            player2.setText("");
            player1Box.getChildren().clear();
            player2Box.getChildren().clear();
            Label label1 = new Label("W");
            label1.setTextFill(Color.GREEN);
            player1Box.getChildren().add(label1);
            matchStageController.newWinner(match);
        }
    }

    @FXML
    public void player1Win(ActionEvent actionEvent) {
        match.setWinner(match.getParticipant1());
        player1Box.getChildren().clear();
        player2Box.getChildren().clear();
        Label label1 = new Label("W");
        label1.setTextFill(Color.GREEN);
        player1Box.getChildren().add(label1);
        Label label2 = new Label("L");
        label2.setTextFill(Color.RED);
        player2Box.getChildren().add(label2);
        matchStageController.newWinner(match);
    }

    @FXML
    public void player1Lose(ActionEvent actionEvent) {
        match.setWinner(match.getParticipant2());
        player1Box.getChildren().clear();
        player2Box.getChildren().clear();
        Label label1 = new Label("L");
        label1.setTextFill(Color.RED);
        player1Box.getChildren().add(label1);
        Label label2 = new Label("W");
        label2.setTextFill(Color.GREEN);
        player2Box.getChildren().add(label2);
        matchStageController.newWinner(match);
    }

    @FXML
    public void player2Win(ActionEvent actionEvent) {
        match.setWinner(match.getParticipant2());
        player1Box.getChildren().clear();
        player2Box.getChildren().clear();
        Label label1 = new Label("L");
        label1.setTextFill(Color.RED);
        player1Box.getChildren().add(label1);
        Label label2 = new Label("W");
        label2.setTextFill(Color.GREEN);
        player2Box.getChildren().add(label2);
        matchStageController.newWinner(match);
    }

    @FXML
    public void player2Lose(ActionEvent actionEvent) {
        match.setWinner(match.getParticipant1());
        player1Box.getChildren().clear();
        player2Box.getChildren().clear();
        Label label1 = new Label("W");
        label1.setTextFill(Color.GREEN);
        player1Box.getChildren().add(label1);
        Label label2 = new Label("L");
        label2.setTextFill(Color.RED);
        player2Box.getChildren().add(label2);
        matchStageController.newWinner(match);
    }

}
