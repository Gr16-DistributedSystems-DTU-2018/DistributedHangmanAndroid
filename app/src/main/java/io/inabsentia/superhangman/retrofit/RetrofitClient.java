package io.inabsentia.superhangman.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.IOException;

import io.inabsentia.superhangman.retrofit.interfaces.BooleanCallback;
import io.inabsentia.superhangman.retrofit.interfaces.GameLogicCallback;
import io.inabsentia.superhangman.retrofit.interfaces.IntegerCallback;
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
        retrofit = new Retrofit.Builder().baseUrl("http://ubuntu4.javabog.dk:8083/web/rest/game/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create()).build();
    }

    public void logIn(String username, String password, BooleanCallback callback) {
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

    public void logOut(String username, BooleanCallback callback) {
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

    public void getGameLogic(String username, GameLogicCallback callback) {

    }

    public void getAllCurrentUsernames(StringListCallback callback) {

    }

    public void getCurrentUserAmount(IntegerCallback callback) {

    }

    public void getLoggedInUser(String username, UserCallback callback) {

    }

    public void isLoggedIn(String username, BooleanCallback callback) {

    }

    public void getUserWithHighestHighscore(UserCallback callback) {

    }

    public void setUserHighscore(String username, String highscore, BooleanCallback callback) {
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

    public void getUserHighscore(String username, StringCallback callback) {
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

    public void getAllLoggedInUsersScore(MapStringIntegerCallback callback) {

    }

    public void getAllUsersHighscore(MapStringIntegerCallback callback) {

    }

    public void sendEmail(String username, String password, String subject, String msg, StringCallback callback) {

    }

    public void sendForgotPasswordEmail(String username, String msg, StringCallback callback) {

    }

    public void changeUserPassword(String username, String oldPassword, String newPassword, StringCallback callback) {

    }

    public void guess(String username, Character ch, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.guess(username, ch).enqueue(new Callback<ResponseBody>() {
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

    public void resetScore(String username, StringCallback callback) {
        RESTService authService = getRESTService();
        authService.resetScore(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onSuccess(response.body().string());
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

    public void resetGame(String username, StringCallback callback) {
        RESTService authService = getRESTService();
        authService.resetGame(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    callback.onSuccess(response.body().string());
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

    public void getGuessedChars(String username, StringCallback callback) {
        RESTService authService = getRESTService();
        authService.getGuessedChars(username).enqueue(new Callback<ResponseBody>() {
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

    public void getWordWord(String username, StringCallback callback) {
        RESTService authService = getRESTService();
        authService.getWord(username).enqueue(new Callback<ResponseBody>() {
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

    public void getLife(String username, IntegerCallback callback) {
        RESTService authService = getRESTService();
        authService.getLife(username).enqueue(new Callback<ResponseBody>() {
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

    public void getScore(String username, IntegerCallback callback) {
        RESTService authService = getRESTService();
        authService.getScore(username).enqueue(new Callback<ResponseBody>() {
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

    public void isCharGuessed(String username, Character ch, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isCharGuessed(username, ch).enqueue(new Callback<ResponseBody>() {
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

    public void isGameWon(String username, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isGameWon(username).enqueue(new Callback<ResponseBody>() {
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

    public void isGameLost(String username, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isGameLost(username).enqueue(new Callback<ResponseBody>() {
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

    public void isHighscore(String username, BooleanCallback callback) {
        RESTService authService = getRESTService();
        authService.isHighscore(username).enqueue(new Callback<ResponseBody>() {
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