package io.inabsentia.superhangman.data.dto;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

public class HighScoreDTO implements Serializable, Comparable<HighScoreDTO> {

    private static int id = 0;
    private String name;
    private String lostWord;
    private int score;
    private int winCount;
    private double avgRoundTime;

    public HighScoreDTO(String name, String lostWord, int score, int winCount, double avgRoundTime) {
        HighScoreDTO.id = id++;
        this.name = name;
        this.lostWord = lostWord;
        this.score = score;
        this.winCount = winCount;
        this.avgRoundTime = avgRoundTime;
    }

    @Override
    public int compareTo(@NonNull HighScoreDTO highScoreDTO) {
        if (this.score == highScoreDTO.getScore())
            return 0;
        else if (this.score < highScoreDTO.getScore())
            return 1;
        else
            return -1;
    }

    public int getId() {
        return id;
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