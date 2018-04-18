package io.inabsentia.superhangman.item;

import android.widget.Button;

public class LobbyItem {

    private final String username;
    private final String score;
    private final Button btnBattle;

    public LobbyItem(String username, String score, Button btnBattle) {
        this.username = username;
        this.score = score;
        this.btnBattle = btnBattle;
    }

    public String getUsername() {
        return username;
    }

    public String getScore() {
        return score;
    }

    public Button getBtnBattle() {
        return btnBattle;
    }

}