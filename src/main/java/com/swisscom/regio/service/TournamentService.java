package com.swisscom.regio.service;

import com.swisscom.regio.model.Game;
import com.swisscom.regio.model.Participant;
import com.swisscom.regio.model.Tournament;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentService {
    private static TournamentService tournamentService = null;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/tournament";
    private Connection conn = null;
    private Statement stmt = null;
    private PreparedStatement prepartedStmt = null;

    private TournamentService() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TournamentService getInstance() {
        if (tournamentService == null)
            tournamentService = new TournamentService();
        return tournamentService;
    }

    public List<Game> getGames() {
        String sql = "SELECT name FROM game";
        List<Game> games = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                games.add(new Game(rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    public boolean createTournament(String title, String game, int Size) {
        String sqlGameId = "SELECT id FROM tournament.game where name = \"" + game + "\"";
        String sqlCreateTournament = "INSERT INTO tournament (title, game_id, size, winner_participant_id, tournament_state) Values(" +
                "\"" + title + "\"," +
                "?," +
                Size + "," +
                null + "," +
                0 + ") ";
        int gameId;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlGameId);
            rs.next();
            gameId = rs.getInt("id");
            prepartedStmt = conn.prepareStatement(sqlCreateTournament);
            prepartedStmt.setInt(1, gameId);
            prepartedStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Tournament> getTournaments() {
        String sql = "select t.id, t.title, g.name as gameName, t.size, p.name as participantName, t.tournament_state\n" +
                "From tournament as t\n" +
                "LEFT JOIN game as g on t.game_id = g.id\n" +
                "LEFT JOIN participant as p on t.winner_participant_id = p.id";
        List<Tournament> tournaments = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tournaments.add(new Tournament(
                        rs.getInt("id"),
                        rs.getString("title"),
                        new Game(rs.getString("gameName")),
                        rs.getInt("size"),
                        new Participant("participantName"),
                        rs.getInt("tournament_state")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournaments;
    }

    public boolean createParticipant(Participant participant, int tournementId) {
        String sqlCreateParticipant = "INSERT INTO participant (name, is_temporary, is_team) VALUES(" +
                "\"" + participant.getName() + "\"," +
                participant.isTemporary() + "," +
                participant.isTeam() + ")";
        String sqlParticipantId = "Select id from participant ORDER BY ID DESC LIMIT 1";
        String sqlParticipantTournament = "INSERT INTO participant_tournament(tournament_id, participant_id) VALUES(" +
                tournementId + "," +
                "?" + ")";
        int participantId = 0;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlCreateParticipant);
            ResultSet rs = stmt.executeQuery(sqlParticipantId);
            if (rs.next()) {
                participantId = rs.getInt("id");
            }
            prepartedStmt = conn.prepareStatement(sqlParticipantTournament);
            prepartedStmt.setInt(1, participantId);
            prepartedStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Participant getParticipant(String name) {
        String sql = "SELECT * FROM participant " +
        "WHERE name = \"" + name + "\"";
        Participant participant = new Participant();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                participant = new Participant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("is_temporary"),
                        rs.getBoolean("is_team")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participant;
    }

    public List<Participant> getParticipants(Tournament tournament) {
        String sql = "SELECT * FROM participant " +
                "LEFT JOIN participant_tournament on participant.id = participant_tournament.participant_id " +
                "WHERE participant_tournament.tournament_id = " + tournament.getId();
        List<Participant> participants = new ArrayList<>();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                participants.add(new Participant(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("is_temporary"),
                        rs.getBoolean("is_team")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    public boolean deletePlayer(int id) {
        String sql = "DELETE FROM participant "+
                "WHERE id = " + id;
        String sql2 = "DELETE FROM participant_tournament "+
                "WHERE participant_id = " + id;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    public void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
