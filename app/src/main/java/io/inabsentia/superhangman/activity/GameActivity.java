package io.inabsentia.superhangman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.helper.Utils;
import io.inabsentia.superhangman.logic.GameLogic;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvHiddenWord, tvScoreLabel, tvHighScore, tvCustomTitle;
    private Chronometer chronoMeter;
    private ImageView hangmanImage;

    private final int BTN_AMOUNT = 28;

    private final Button[] btnArray = new Button[BTN_AMOUNT];
    private final int[] btnIdArray = {R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d, R.id.btn_e, R.id.btn_f, R.id.btn_g, R.id.btn_h, R.id.btn_i, R.id.btn_j, R.id.btn_k, R.id.btn_l, R.id.btn_m, R.id.btn_n, R.id.btn_o, R.id.btn_p, R.id.btn_q, R.id.btn_r, R.id.btn_s, R.id.btn_t, R.id.btn_u, R.id.btn_v, R.id.btn_w, R.id.btn_x, R.id.btn_y, R.id.btn_z, R.id.btn_hint_1, R.id.btn_hint_2};

    private final Utils utils = Utils.getInstance();
    private final GameLogic logic = GameLogic.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        /*
         * Instantiate objects.
         */
        tvHiddenWord = findViewById(R.id.hidden_word);
        tvScoreLabel = findViewById(R.id.high_score_label);
        tvHighScore = findViewById(R.id.high_score);
        tvCustomTitle = findViewById(R.id.action_bar_title);
        chronoMeter = findViewById(R.id.time);
        hangmanImage = findViewById(R.id.game_image);

        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i] = findViewById(btnIdArray[i]);
            btnArray[i].setOnClickListener(this);
        }

        /* Set title of action bar */
        tvCustomTitle.setText(R.string.app_name);

        /* Set title color */
        tvCustomTitle.setTextColor(getResources().getColor(R.color.textColor));

        /*
         * Initialize the chronometer.
         */
        chronoMeter.setBase(SystemClock.elapsedRealtime());
        chronoMeter.start();

        /*
         * We have to initialize the logic controller
         * before using it's functionality.
         */
        try {
            logic.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * If the logic controller initializing has gone well,
         * the display is updated with new values and text.
         */
        updateDisplay();

        /*
         * Remove Keyboard
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void updateDisplay() {
        /* Update the current hidden word */
        tvHiddenWord.setText(logic.getHiddenWord());

        /* Update the current score */
        tvHighScore.setText(String.valueOf(logic.getScore()));

        /* Update the image according to the amount of lives left */
        switch (logic.getLife()) {
            case 7:
                hangmanImage.setImageResource(R.drawable.wrong_0);
                break;
            case 6:
                hangmanImage.setImageResource(R.drawable.wrong_1);
                break;
            case 5:
                hangmanImage.setImageResource(R.drawable.wrong_2);
                break;
            case 4:
                hangmanImage.setImageResource(R.drawable.wrong_3);
                break;
            case 3:
                hangmanImage.setImageResource(R.drawable.wrong_4);
                break;
            case 2:
                hangmanImage.setImageResource(R.drawable.wrong_5);
                break;
            case 1:
                hangmanImage.setImageResource(R.drawable.wrong_6);
                break;
            default:
                break;
        }

    }

    private void guess(char guess) {
        /* Make the logic controller take a guess! */
        logic.guess(guess);

        /* Update the display, to see the next status after the guess */
        updateDisplay();

        /* Check whether the game is lost or not */
        if (logic.isLost()) fireFinishActivity(false);

        /* Check whether the game is won or not */
        if (logic.isWon()) fireFinishActivity(true);
    }

    private void fireFinishActivity(boolean isWon) {
        calculateTimeUsed();
        Intent intentFinish = new Intent(this, EndGameActivity.class);

        intentFinish.putExtra("game_status", isWon);
        intentFinish.putExtra("secret_word", logic.getSecretWord());
        intentFinish.putExtra("round_count", logic.getRounds());

        startActivity(intentFinish);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_hint_1 || id == R.id.btn_hint_2) {
            giveHint(view);
        } else {
            Log.d("btnClick", ((Button) view).getText().toString().toLowerCase());
            guess(((Button) view).getText().toString().toLowerCase().charAt(0));
            view.setVisibility(View.INVISIBLE);
        }
    }

    private void calculateTimeUsed() {
        double secondsElapsed = (SystemClock.elapsedRealtime() - chronoMeter.getBase()) / 1000.0;
        logic.setTimeUsed(secondsElapsed + logic.getTimeUsed());
    }

    private void giveHint(View view) {
        logic.giveHint();

        updateDisplay();

        /* Check whether the game is lost or not */
        if (logic.isLost()) fireFinishActivity(false);

        /* Check whether the game is won or not */
        if (logic.isWon()) fireFinishActivity(true);

        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        calculateTimeUsed();
        utils.createMatchAndReset(getApplicationContext());

        Intent intentMenu = new Intent(this, MenuActivity.class);
        startActivity(intentMenu);
    }

}
