package com.b.m.josh.keithdrewpool;

public class Player {
    private int score;
    private int currentBall;
    private int id;

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getCurrentBall() {
        return currentBall;
    }
    public void setCurrentBall(int currentBall) {
        this.currentBall = currentBall;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public Player(int score, int currentBall, int id) {
        this.score = score;
        this.currentBall = currentBall;
        this.id = id;
    }
}
