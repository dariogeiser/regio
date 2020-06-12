package com.swisscom.regio.controller;

import com.swisscom.regio.model.StageLoader;
import com.swisscom.regio.model.Tournament;
import com.swisscom.regio.service.TournamentService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class TournamentListController {

    @FXML
    TableView tournamentTable;

    StageLoader stageLoader;
    TournamentService tournamentService;


    public TournamentListController(){
        stageLoader = new StageLoader();
        tournamentService = TournamentService.getInstance();
    }

    @FXML
    public void initialize() {
        List<Tournament> tournaments = tournamentService.getTournaments();
        TableColumn<Tournament, String> column1 = new TableColumn<>("Title");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        column1.setMinWidth(250);

        TableColumn<Tournament, String> column2 = new TableColumn<>("Game");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournament, String>,
                        ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tournament, String> data){
                return new SimpleStringProperty(data.getValue().getGame().getName());
            }
        });
        column2.setMinWidth(125);

        TableColumn<Tournament, String> column3 = new TableColumn<>("Winner");
        column3.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tournament, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tournament, String> data){
                if(data.getValue().getState() != 2) {
                    return new SimpleStringProperty("undecided");
                }
                return new SimpleStringProperty(data.getValue().getWinnerParticipant().getName());
            }
        });
        column3.setMinWidth(125);

        tournamentTable.getColumns().add(column1);
        tournamentTable.getColumns().add(column2);
        tournamentTable.getColumns().add(column3);

        for(Tournament tournament: tournaments) {
            tournamentTable.getItems().add(tournament);
        }
    }
    @FXML
    public void createTournament(ActionEvent actionEvent) throws IOException {
       stageLoader.loadCreateTournament();
    }
    @FXML
    public void openOverview(ActionEvent actionEvent) {
        Tournament tournament = (Tournament) tournamentTable.getSelectionModel().getSelectedItem();
        stageLoader.loadTournamentOverview(tournament);
    }
}
