package io.inabsentia.superhangman.retrofit.interfaces;

import java.util.Map;

public interface MapStringIntegerCallback {
    void onSuccess(Map<String, Integer> map);
    void onFailure();
}