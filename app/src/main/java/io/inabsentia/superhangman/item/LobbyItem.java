package io.inabsentia.superhangman.item;

public class LobbyItem {

    private final String username;
    private final String score;

    public LobbyItem(String username, String score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public String getScore() {
        return score;
    }

}