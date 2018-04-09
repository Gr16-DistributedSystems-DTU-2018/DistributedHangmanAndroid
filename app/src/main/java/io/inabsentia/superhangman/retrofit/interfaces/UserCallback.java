package io.inabsentia.superhangman.retrofit.interfaces;

import brugerautorisation.data.Bruger;

public interface UserCallback {
    void onSuccess(Bruger user);
    void onFailure();
}