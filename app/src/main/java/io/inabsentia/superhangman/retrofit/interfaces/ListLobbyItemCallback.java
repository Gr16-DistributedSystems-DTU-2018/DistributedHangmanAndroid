package io.inabsentia.superhangman.retrofit.interfaces;

import java.util.List;

import io.inabsentia.superhangman.item.LobbyItem;

public interface ListLobbyItemCallback {
    void onSuccess(List<LobbyItem> list);
    void onFailure();
}