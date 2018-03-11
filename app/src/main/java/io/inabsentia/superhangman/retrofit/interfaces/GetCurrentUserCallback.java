package io.inabsentia.superhangman.retrofit.interfaces;

import brugerautorisation.data.Bruger;

public interface GetCurrentUserCallback {
    void onSuccess(Bruger user);
    void onFailure();
}