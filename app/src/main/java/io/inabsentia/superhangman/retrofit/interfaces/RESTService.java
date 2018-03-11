package io.inabsentia.superhangman.retrofit.interfaces;

import brugerautorisation.data.Bruger;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RESTService {
    @POST("auth/login")
    Call<ResponseBody> logIn(@Query("username") String username, @Query("password") String password);

    @POST("auth/logout")
    Call<ResponseBody> logOut();

    @POST("auth/set_user_field")
    Call<ResponseBody> setUserField(@Query("username") String username, @Query("password") String password, @Query("userFieldKey") String userFieldKey, @Query("value") String value);

    @GET("auth/get_user_field")
    Call<ResponseBody> getUserField(@Query("username") String username, @Query("password") String password, @Query("userFieldKey") String userFieldKey);

    @GET("auth/get_current_user")
    Call<Bruger> getCurrentUser();

    @GET("auth/is_logged_in")
    Call<ResponseBody> isLoggedIn();

    @GET("auth/test")
    Call<ResponseBody> test();


    @POST("logic/guess")
    Call<ResponseBody> guess(@Query("ch") Character ch);

    @POST("logic/reset_score")
    Call<ResponseBody> resetScore();

    @POST("logic/reset_game")
    Call<ResponseBody> resetGame();

    @GET("logic/get_guessed_chars")
    Call<ResponseBody> getGuessedChars();

    @GET("logic/get_guessed_word")
    Call<ResponseBody> getGuessedWord();

    @GET("logic/get_life")
    Call<ResponseBody> getLife();

    @GET("logic/get_score")
    Call<ResponseBody> getScore();

    @GET("logic/is_char_guessed")
    Call<ResponseBody> isCharGuessed(@Query("ch") Character ch);

    @GET("logic/is_game_won")
    Call<ResponseBody> isGameWon();

    @GET("logic/is_game_lost")
    Call<ResponseBody> isGameLost();

    @GET("logic/is_highscore")
    Call<ResponseBody> isHighscore();
}