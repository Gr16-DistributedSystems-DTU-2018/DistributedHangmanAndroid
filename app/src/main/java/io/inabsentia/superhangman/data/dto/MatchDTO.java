package io.inabsentia.superhangman.data.dto;

import java.io.Serializable;
import java.util.Locale;

public class MatchDTO implements Serializable {

    private static int _id = 0;
    private String name;
    private String lostWord;
    private int score;
    private int winCount;
    private double avgRoundTime;

    public MatchDTO(String name, String lostWord, int score, int winCount, double avgRoundTime) {
        MatchDTO._id = _id++;
        this.name = name;
        this.lostWord = lostWord;
        this.score = score;
        this.winCount = winCount;
        this.avgRoundTime = avgRoundTime;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getLastWord() {
        return lostWord;
    }

    public int getScore() {
        return score;
    }

    public int getWinCount() {
        return winCount;
    }

    public double getAvgRoundTime() {
        return avgRoundTime;
    }

    @Override
    public String toString() {
        return "Score: " + score +
                ", Wins: " + winCount +
                ", Time: " + String.format(Locale.ENGLISH, "%.2f", (double) Math.round(avgRoundTime)) +
                "\nLost on: " + lostWord;
    }

}