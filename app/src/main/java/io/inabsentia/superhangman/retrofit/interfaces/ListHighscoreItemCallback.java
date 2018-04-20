package io.inabsentia.superhangman.retrofit.interfaces;

import java.util.List;

import io.inabsentia.superhangman.item.HighScoreItem;

public interface ListHighscoreItemCallback {
    void onSuccess(List<HighScoreItem> list);
    void onFailure();
}