package io.inabsentia.superhangman.retrofit.interfaces;

public interface GetGuessedWordCallback {
    void onSuccess(String value);
    void onFailure();
}