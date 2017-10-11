package io.inabsentia.superhangman.data.dto;

import java.io.Serializable;

public class ScoreDTO implements Serializable {

    boolean isWon;
    private int rightGuessCount;
    private int wrongGuessCount;
    private int totalGuessCount;

    public ScoreDTO(boolean isWon, int rightGuessCount, int wrongGuessCount, int totalGuessCount) {
        this.isWon = isWon;
        this.rightGuessCount = rightGuessCount;
        this.wrongGuessCount = wrongGuessCount;
        this.totalGuessCount = totalGuessCount;
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

    @Override
    public String toString() {
        return "ScoreDTO{" +
                "isWon=" + isWon +
                ", rightGuessCount=" + rightGuessCount +
                ", wrongGuessCount=" + wrongGuessCount +
                ", totalGuessCount=" + totalGuessCount +
                '}';
    }

}