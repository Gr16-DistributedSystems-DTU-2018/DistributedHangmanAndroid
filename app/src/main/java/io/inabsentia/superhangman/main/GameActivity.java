package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.data.dao.PHighScoreDAO;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;
import io.inabsentia.superhangman.logic.GameLogic;
import io.inabsentia.superhangman.utils.Utils;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView hangmanImage;
    private TextView tvHiddenWord, tvScore;
    private Button btnMenu;
    private Chronometer chronoMeter;

    private final int BTN_AMOUNT = 28;

    private final Button[] btnArray = new Button[BTN_AMOUNT];
    private final Integer[] btnIdArray = {R.id.btn_a, R.id.btn_b, R.id.btn_c, R.id.btn_d, R.id.btn_e, R.id.btn_f, R.id.btn_g, R.id.btn_h, R.id.btn_i, R.id.btn_j, R.id.btn_k, R.id.btn_l, R.id.btn_m, R.id.btn_n, R.id.btn_o, R.id.btn_p, R.id.btn_q, R.id.btn_r, R.id.btn_s, R.id.btn_t, R.id.btn_u, R.id.btn_v, R.id.btn_w, R.id.btn_x, R.id.btn_y, R.id.btn_z, R.id.btn_tmp_1, R.id.btn_tmp_2};

    private final Utils utils = Utils.getInstance();
    private final GameLogic logic = GameLogic.getInstance();
    private final IHighScoreDAO highScoreDAO = PHighScoreDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /*
         * Initialize UI objects.
         */
        hangmanImage = (ImageView) findViewById(R.id.start_img);
        tvHiddenWord = (TextView) findViewById(R.id.hidden_word);
        tvScore = (TextView) findViewById(R.id.high_score);
        btnMenu = (Button) findViewById(R.id.btn_game_menu);
        chronoMeter = (Chronometer) findViewById(R.id.time);

        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i] = (Button) findViewById(btnIdArray[i]);
            btnArray[i].setOnClickListener(this);
        }

        btnMenu.setOnClickListener(this);

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
         * the display is updated with new values.
         */
        updateDisplay();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void updateDisplay() {
        /* Update the current hidden word */
        tvHiddenWord.setText(logic.getHiddenWord());

        /* Update the current score */
        tvScore.setText("Score\n" + Integer.toString(logic.getScore()));

        /* Update the image according to the amount of lives left */
        switch (logic.getLife()) {
            case 6:
                hangmanImage.setImageResource(R.drawable.hangman);
                break;
            case 5:
                hangmanImage.setImageResource(R.drawable.wrong_1);
                break;
            case 4:
                hangmanImage.setImageResource(R.drawable.wrong_2);
                break;
            case 3:
                hangmanImage.setImageResource(R.drawable.wrong_3);
                break;
            case 2:
                hangmanImage.setImageResource(R.drawable.wrong_4);
                break;
            case 1:
                hangmanImage.setImageResource(R.drawable.wrong_5);
                break;
            case 0:
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
        double secondsElapsed = (SystemClock.elapsedRealtime() - chronoMeter.getBase()) / 1000.0;
        logic.setTimeUsed(secondsElapsed + logic.getTimeUsed());

        Intent intentFinish = new Intent(this, FinishActivity.class);

        intentFinish.putExtra("game_status", isWon);
        intentFinish.putExtra("secret_word", logic.getSecretWord());
        intentFinish.putExtra("round_count", logic.getRounds());

        if (!isWon) {
            double avgRoundTime = logic.getTimeUsed() / logic.getTotalGames();
            HighScoreDTO highScoreDTO = new HighScoreDTO("", logic.getSecretWord(), logic.getScore(), logic.getWinCount(), avgRoundTime);

            try {
                highScoreDAO.add(highScoreDTO);
            } catch (IHighScoreDAO.DALException e) {
                e.printStackTrace();
            }
        }

        startActivity(intentFinish);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_game_menu) {
            double secondsElapsed = (SystemClock.elapsedRealtime() - chronoMeter.getBase()) / 1000.0;
            logic.setTimeUsed(secondsElapsed + logic.getTimeUsed());
            utils.createScoreAndReset();
            Intent intentMenu = new Intent(this, MainActivity.class);
            startActivity(intentMenu);
        } else {
            Log.d("btnClick", ((Button) view).getText().toString().toLowerCase());
            guess(((Button) view).getText().toString().toLowerCase().charAt(0));
            view.setVisibility(View.INVISIBLE);
        }
    }

}
