package io.inabsentia.superhangman.retrofit.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RESTService {

    @POST("login")
    Call<ResponseBody> logIn(@Query("username") String username, @Query("password") String password);

    @POST("logout")
    Call<ResponseBody> logOut(@Query("username") String username);

    @GET("get_logic")
    Call<ResponseBody> getGameLogicInstance(@Query("username") String username);

    @GET("get_all_current_usernames")
    Call<ResponseBody> getAllCurrentUserNames();

    @GET("get_current_user_amount")
    Call<ResponseBody> getCurrentUserAmount();

    @GET("get_logged_in_user")
    Call<ResponseBody> getLoggedInUser(@Query("username") String username);

    @GET("is_logged_in")
    Call<ResponseBody> isLoggedIn(@Query("username") String username);

    @GET("get_user_with_highest_highscore")
    Call<ResponseBody> getUserWithHighestHighscore();

    @POST("set_user_highscore")
    Call<ResponseBody> setUserHighscore(@Query("username") String username, @Query("highscore") String highscore);

    @GET("get_user_highscore")
    Call<ResponseBody> getUserHighscore(@Query("username") String username);

    @GET("get_all_logged_in_users_score")
    Call<ResponseBody> getAllLoggedInUsersScore();

    @GET("get_all_users_highscore")
    Call<ResponseBody> getAllUsersHighscore();

    @GET("send_email")
    Call<ResponseBody> sendEmail(@Query("username") String username, @Query("password") String password, @Query("subject") String subject, @Query("msg") String msg);

    @GET("send_forgot_password_email")
    Call<ResponseBody> sendForgotPasswordEmail(@Query("username") String username, @Query("msg") String msg);

    @POST("change_user_password")
    Call<ResponseBody> changeUserPassword(@Query("username") String username, @Query("oldPassword") String oldPassword, @Query("newPassword") String newPassword);

    /*
     * Hangman
     */
    @POST("guess")
    Call<ResponseBody> guess(@Query("username") String username, @Query("ch") Character ch);

    @POST("reset_score")
    Call<ResponseBody> resetScore(@Query("username") String username);

    @POST("reset_game")
    Call<ResponseBody> resetGame(@Query("username") String username);

    @GET("get_guessed_chars")
    Call<ResponseBody> getGuessedChars(@Query("username") String username);

    @GET("get_word")
    Call<ResponseBody> getWord(@Query("username") String username);

    @GET("get_life")
    Call<ResponseBody> getLife(@Query("username") String username);

    @GET("get_score")
    Call<ResponseBody> getScore(@Query("username") String username);

    @GET("is_char_guessed")
    Call<ResponseBody> isCharGuessed(@Query("username") String username, @Query("ch") Character ch);

    @GET("is_game_won")
    Call<ResponseBody> isGameWon(@Query("username") String username);

    @GET("is_game_lost")
    Call<ResponseBody> isGameLost(@Query("username") String username);

    @GET("is_highscore")
    Call<ResponseBody> isHighscore(@Query("username") String username);

    /*
     * Test
     */
    @GET("test")
    Call<ResponseBody> test();
}