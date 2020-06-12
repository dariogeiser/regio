package com.swisscom.regio.model;

public class Tournament {
    private int id;
    private String title;
    private Game game;
    private int size;
    private Participant winnerParticipant;
    private int state;

    public Tournament() {};

    public Tournament(int id, String title, Game game, int size, Participant winnerParticipant, int state) {
        this.id = id;
        this.title = title;
        this.game = game;
        this.size = size;
        this.winnerParticipant = winnerParticipant;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Participant getWinnerParticipant() {
        return winnerParticipant;
    }

    public void setWinnerParticipant(Participant winnerParticipant) {
        this.winnerParticipant = winnerParticipant;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
