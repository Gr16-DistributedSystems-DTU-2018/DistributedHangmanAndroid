package io.inabsentia.superhangman.retrofit.interfaces;

public interface GetGuessedCharsCallback {
    void onSuccess(String value);
    void onFailure();
}