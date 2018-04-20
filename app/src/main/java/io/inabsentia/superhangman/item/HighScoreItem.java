package io.inabsentia.superhangman.item;

public class HighScoreItem {

    private final String username;
    private final String highscore;

    public HighScoreItem(String username, String highscore) {
        this.username = username;
        this.highscore = highscore;
    }

    public String getUsername() {
        return username;
    }

    public String getHighscore() {
        return highscore;
    }

}