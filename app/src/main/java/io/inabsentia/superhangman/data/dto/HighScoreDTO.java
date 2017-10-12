package io.inabsentia.superhangman.data.dto;

import java.io.Serializable;

public class HighScoreDTO implements Serializable {

    boolean isWon;
    private int rightGuessCount;
    private int wrongGuessCount;
    private int totalGuessCount;
    private double secondsElapsed;

    public HighScoreDTO(boolean isWon, int rightGuessCount, int wrongGuessCount, int totalGuessCount, double secondsElapsed) {
        this.isWon = isWon;
        this.rightGuessCount = rightGuessCount;
        this.wrongGuessCount = wrongGuessCount;
        this.totalGuessCount = totalGuessCount;
        this.secondsElapsed = secondsElapsed;
    }

    public boolean isWon() {
        return isWon;
    }

    public int getRightGuessCount() {
        return rightGuessCount;
    }

    public int getWrongGuessCount() {
        return wrongGuessCount;
    }

    public int getTotalGuessCount() {
        return totalGuessCount;
    }

    public double getSecondsElapsed() {
        return secondsElapsed;
    }

    @Override
    public String toString() {
        return "HighScoreDTO{" +
                "isWon=" + isWon +
                ", rightGuessCount=" + rightGuessCount +
                ", wrongGuessCount=" + wrongGuessCount +
                ", totalGuessCount=" + totalGuessCount +
                ", secondsElapsed=" + secondsElapsed +
                '}';
    }

}