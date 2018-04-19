package io.inabsentia.superhangman.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import brugerautorisation.data.Bruger;
import io.inabsentia.superhangman.item.LobbyItem;
import io.inabsentia.superhangman.retrofit.interfaces.BooleanCallback;
import io.inabsentia.superhangman.retrofit.interfaces.IntegerCallback;
import io.inabsentia.superhangman.retrofit.interfaces.ListLobbyItemCallback;
import io.inabsentia.superhangman.retrofit.interfaces.MapStringIntegerCallback;
import io.inabsentia.superhangman.retrofit.interfaces.RESTService;
import io.inabsentia.superhangman.retrofit.interfaces.StringCallback;
import io.inabsentia.superhangman.retrofit.interfaces.StringListCallback;
import io.inabsentia.superhangman.retrofit.interfaces.UserCallback;
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
        retrofit = new Retrofit.Builder().baseUrl("http://ubuntu4.saluton.dk:8080/web/rest/game/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    public void logIn(String username, String password, BooleanCallback callback) {
        RESTService restService = getRESTService();
        restService.logIn(username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (!isResponseNull(response)) {
                        if (response.body().string().equals(username + " logged in successfully."))
                            callback.onSuccess(true);
                        else
                            callback.onFailure();
                    } else {
                        callback.onFailure();
                    }
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

    public void logOut(String username, BooleanCallback callback) {
        RESTService restService = getRESTService();
        restService.logOut(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    callback.onSuccess(true);
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getAllCurrentUsernames(StringListCallback callback) {
        RESTService restService = getRESTService();
        restService.getAllCurrentUserNames().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (!isResponseNull(response)) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<String>>() {
                        }.getType();
                        List<String> usernames = gson.fromJson(response.body().string(), listType);
                        callback.onSuccess(usernames);
                    } else {
                        callback.onFailure();
                    }
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

    public void getCurrentUserAmount(IntegerCallback callback) {
        RESTService restService = getRESTService();
        restService.getCurrentUserAmount().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (!isResponseNull(response)) {
                        callback.onSuccess(Integer.parseInt(response.body().string()));
                    } else {
                        callback.onFailure();
                    }
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

    public void getLoggedInUser(String username, UserCallback callback) {
        RESTService restService = getRESTService();
        restService.getLoggedInUser(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (!isResponseNull(response)) {
                        Gson gson = new Gson();
                        Bruger user = gson.fromJson(response.body().string(), Bruger.class);
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure();
                    }
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

    public void isLoggedIn(String username, BooleanCallback callback) {
        RESTService restService = getRESTService();
        restService.isLoggedIn(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(Boolean.parseBoolean(response.body().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getUserWithHighestHighscore(UserCallback callback) {
        RESTService restService = getRESTService();
        restService.getUserWithHighestHighscore().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        Gson gson = new Gson();
                        Bruger user = gson.fromJson(response.body().string(), Bruger.class);
                        callback.onSuccess(user);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void setUserHighscore(String username, String highscore, BooleanCallback callback) {
        RESTService restService = getRESTService();
        restService.setUserHighscore(username, highscore).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    callback.onSuccess(true);
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getUserHighscore(String username, StringCallback callback) {
        RESTService restService = getRESTService();
        restService.getUserHighscore(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        String value = response.body().string();
                        callback.onSuccess(value);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getAllLoggedInUsersScore(ListLobbyItemCallback callback) {
        RESTService restService = getRESTService();
        restService.getAllLoggedInUsersScore().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<Map<String, Integer>>() {
                        }.getType();
                        Map<String, Integer> map = gson.fromJson(response.body().string(), listType);

                        List<LobbyItem> lobbyItemList = new ArrayList<>();

                        for (String username : map.keySet()) {
                            int score = map.get(username);
                            lobbyItemList.add(new LobbyItem(username, Integer.toString(score)));
                        }

                        callback.onSuccess(lobbyItemList);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getAllUsersHighscore(MapStringIntegerCallback callback) {
        RESTService restService = getRESTService();
        restService.getAllUsersHighscore().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<Map<String, Integer>>() {
                        }.getType();
                        Map<String, Integer> map = gson.fromJson(response.body().string(), listType);
                        callback.onSuccess(map);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void sendEmail(String username, String password, String subject, String msg, StringCallback callback) {
        RESTService restService = getRESTService();
        restService.sendEmail(username, password, subject, msg).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void sendForgotPasswordEmail(String username, String msg, StringCallback callback) {
        RESTService restService = getRESTService();
        restService.sendForgotPasswordEmail(username, msg).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void changeUserPassword(String username, String oldPassword, String newPassword, StringCallback callback) {
        RESTService restService = getRESTService();
        restService.changeUserPassword(username, oldPassword, newPassword).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void guess(String username, Character ch, BooleanCallback callback) {
        RESTService restService = getRESTService();
        restService.guess(username, ch).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        boolean wasGuessed = Boolean.parseBoolean(response.body().string());
                        callback.onSuccess(wasGuessed);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void resetScore(String username, StringCallback callback) {
        RESTService restService = getRESTService();
        restService.resetScore(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void resetGame(String username, StringCallback callback) {
        RESTService authService = getRESTService();
        authService.resetGame(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getGuessedChars(String username, StringCallback callback) {
        RESTService authService = getRESTService();
        authService.getGuessedChars(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getWordWord(String username, StringCallback callback) {
        RESTService authService = getRESTService();
        authService.getWord(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getLife(String username, IntegerCallback callback) {
        RESTService authService = getRESTService();
        authService.getLife(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(Integer.parseInt(response.body().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getScore(String username, IntegerCallback callback) {
        RESTService authService = getRESTService();
        authService.getScore(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(Integer.parseInt(response.body().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void isCharGuessed(String username, Character ch, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isCharGuessed(username, ch).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        callback.onSuccess(Boolean.parseBoolean(response.body().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void isGameWon(String username, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isGameWon(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        boolean result = Boolean.parseBoolean(response.body().string());
                        callback.onSuccess(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void isGameLost(String username, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isGameLost(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        boolean result = Boolean.parseBoolean(response.body().string());
                        callback.onSuccess(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void isHighscore(String username, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isHighscore(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (!isResponseNull(response)) {
                    try {
                        boolean result = Boolean.parseBoolean(response.body().string());
                        callback.onSuccess(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
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

    private boolean isResponseNull(Response<ResponseBody> response) {
        if (response == null || response.body() == null) {
            return true;
        } else {
            return false;
        }
    }

}