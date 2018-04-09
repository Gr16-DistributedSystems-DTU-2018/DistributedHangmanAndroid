package io.inabsentia.superhangman.retrofit.interfaces;

import server.logic.rmi.IGameLogic;

public interface GameLogicCallback{
    void onSuccess(IGameLogic logic);
    void onFailure();
}