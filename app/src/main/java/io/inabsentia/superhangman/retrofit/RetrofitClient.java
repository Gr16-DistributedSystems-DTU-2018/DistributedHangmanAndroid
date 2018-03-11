package io.inabsentia.superhangman.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.IOException;

import brugerautorisation.data.Bruger;
import io.inabsentia.superhangman.retrofit.interfaces.GetCurrentUserCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GetUserFieldCallback;
import io.inabsentia.superhangman.retrofit.interfaces.RESTService;
import io.inabsentia.superhangman.retrofit.interfaces.IsLoggedInCallback;
import io.inabsentia.superhangman.retrofit.interfaces.LogOutCallback;
import io.inabsentia.superhangman.retrofit.interfaces.LoginCallback;
import io.inabsentia.superhangman.retrofit.interfaces.SetUserFieldCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public final class RetrofitClient {

    private Context mContext;
    private static Retrofit retrofit = null;

    public RetrofitClient(Context context) {
        this.mContext = context;
        retrofit = new Retrofit.Builder().baseUrl("http://ubuntu4.javabog.dk:8080/web/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    public void logIn(String username, String password, LoginCallback callback) {
        RESTService authService = getRESTService();
        authService.logIn(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.body().string().equals(username + " logged in successfully."))
                        callback.onSuccess(true);
                    else
                        callback.onFailure();
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void logOut(LogOutCallback callback) {
        RESTService authService = getRESTService();
        authService.logOut().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // fill in stuff here
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // fill in stuff here
            }
        });
    }

    public void setUserFied(String username, String password, String userFieldKey, String value, SetUserFieldCallback callback) {
        RESTService authService = getRESTService();
        authService.setUserField(username, password, userFieldKey, value).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // fill in stuff here
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // fill in stuff here
            }
        });
    }

    public void getUserField(String username, String password, String userFieldKey, GetUserFieldCallback callback) {
        RESTService authService = getRESTService();
        authService.getUserField(username, password, userFieldKey).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // fill in stuff here
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // fill in stuff here
            }
        });
    }

    public void getCurrentUser(GetCurrentUserCallback callback) {
        RESTService authService = getRESTService();
        authService.getCurrentUser().enqueue(new Callback<Bruger>() {
            @Override
            public void onResponse(@NonNull Call<Bruger> call, @NonNull Response<Bruger> response) {
                Bruger user = response.body();
                callback.onSuccess(user);
            }

            @Override
            public void onFailure(@NonNull Call<Bruger> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "Failed to get current user!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void isLoggedIn(IsLoggedInCallback callback) {
        RESTService authService = getRESTService();
        authService.logOut().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                // fill in stuff here
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                // fill in stuff here
            }
        });
    }

    public void test() {
        RESTService authService = getRESTService();
        authService.test().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    Toast.makeText(mContext, str + " ", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(mContext, "test failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private RESTService getRESTService() {
        return retrofit.create(RESTService.class);
    }

}