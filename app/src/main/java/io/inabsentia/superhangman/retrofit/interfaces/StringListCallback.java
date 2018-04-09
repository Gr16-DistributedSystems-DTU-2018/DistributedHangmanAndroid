package io.inabsentia.superhangman.retrofit.interfaces;

import java.util.List;

public interface StringListCallback {
    void onSuccess(List<String> stringList);
    void onFailure();
}