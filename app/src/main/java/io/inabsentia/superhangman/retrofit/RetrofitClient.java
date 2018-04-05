package io.inabsentia.superhangman.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.IOException;

import io.inabsentia.superhangman.retrofit.interfaces.GetAllCurrentUserNamesCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GetGameLogicCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GetGuessedCharsCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GetGuessedWordCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GetLifeCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GetScoreCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GetUserFieldCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GuessCallback;
import io.inabsentia.superhangman.retrofit.interfaces.IsCharGuessedCallback;
import io.inabsentia.superhangman.retrofit.interfaces.IsGameLostCallback;
import io.inabsentia.superhangman.retrofit.interfaces.IsGameWonCallback;
import io.inabsentia.superhangman.retrofit.interfaces.IsHighscoreCallback;
import io.inabsentia.superhangman.retrofit.interfaces.IsLoggedInCallback;
import io.inabsentia.superhangman.retrofit.interfaces.LogOutCallback;
import io.inabsentia.superhangman.retrofit.interfaces.LoginCallback;
import io.inabsentia.superhangman.retrofit.interfaces.RESTService;
import io.inabsentia.superhangman.retrofit.interfaces.ResetGameCallback;
import io.inabsentia.superhangman.retrofit.interfaces.ResetScoreCallback;
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
        retrofit = new Retrofit.Builder().baseUrl("http://ubuntu4.javabog.dk:8083/web/rest/game/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    public void logIn(String username, String password, LoginCallback callback) {
        RESTService restService = getRESTService();
        restService.logIn(username, password).enqueue(new Callback<ResponseBody>() {
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

    public void logOut(String username, LogOutCallback callback) {
        RESTService authService = getRESTService();
        authService.logOut(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                callback.onSuccess(true);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getGameLogic(String username, GetGameLogicCallback callback) {

    }

    public void getAllCurrentUsernames(GetAllCurrentUserNamesCallback callback) {

    }

    public void getCurrentUserAmount(GetCurrentUserAmountCallback callback) {

    }

    public void getLoggedInUser(String username, GetLoggedInUserCallback callback) {

    }

    public void isLoggedIn(String username, IsLoggedInCallback callback) {

    }

    public void getUserWithHighestHighscore(GetUserWithHighestHighScoreCallback callback) {

    }

    public void setUserHighscore(String username, String highscore, SetUserFieldCallback callback) {
        RESTService authService = getRESTService();
        authService.setUserHighscore(username, highscore).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                callback.onSuccess(true);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getUserHighscore(String username, GetUserFieldCallback callback) {
        RESTService authService = getRESTService();
        authService.getUserHighscore(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    String value = response.body().string();
                    callback.onSuccess(value);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getAllLoggedInUsersScore() {

    }

    public void getAllUsersHighscore() {

    }

    public void sendEmail(String username, String password, String subject, String msg) {

    }

    public void sendForgotPasswordEmail(String username, String msg) {

    }

    public void changeUserPassword(String username, String oldPassword, String newPassword) {

    }









    public void guess(Character ch, GuessCallback callback) {
        RESTService authService = getRESTService();
        authService.guess(ch).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    boolean wasGuessed = Boolean.parseBoolean(response.body().string());
                    callback.onSuccess(wasGuessed);
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

    public void resetScore(ResetScoreCallback callback) {
        RESTService authService = getRESTService();
        authService.resetScore().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void resetGame(ResetGameCallback callback) {
        RESTService authService = getRESTService();
        authService.resetGame().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getGuessedChars(GetGuessedCharsCallback callback) {
        RESTService authService = getRESTService();
        authService.getGuessedChars().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onSuccess(response.body().string());
                } catch (IOException e) {
                    callback.onFailure();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getGuessedWord(GetGuessedWordCallback callback) {
        RESTService authService = getRESTService();
        authService.getGuessedWord().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onSuccess(response.body().string());
                } catch (IOException e) {
                    callback.onFailure();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getLife(GetLifeCallback callback) {
        RESTService authService = getRESTService();
        authService.getLife().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onSuccess(Integer.parseInt(response.body().string()));
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

    public void getScore(GetScoreCallback callback) {
        RESTService authService = getRESTService();
        authService.getScore().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onSuccess(Integer.parseInt(response.body().string()));
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

    public void isCharGuessed(Character ch, IsCharGuessedCallback callback) {
        RESTService authService = getRESTService();
        authService.isCharGuessed(ch).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onSuccess(Boolean.parseBoolean(response.body().string()));
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

    public void isGameWon(IsGameWonCallback callback) {
        RESTService authService = getRESTService();
        authService.isGameWon().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    boolean result = Boolean.parseBoolean(response.body().string());
                    callback.onSuccess(result);
                } catch (IOException e) {
                    callback.onFailure();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void isGameLost(IsGameLostCallback callback) {
        RESTService authService = getRESTService();
        authService.isGameLost().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    boolean result = Boolean.parseBoolean(response.body().string());
                    callback.onSuccess(result);
                } catch (IOException e) {
                    callback.onFailure();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void isHighscore(IsHighscoreCallback callback) {
        RESTService authService = getRESTService();
        authService.isHighscore().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    boolean result = Boolean.parseBoolean(response.body().string());
                    callback.onSuccess(result);
                } catch (IOException e) {
                    callback.onFailure();
                    e.printStackTrace();
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

}