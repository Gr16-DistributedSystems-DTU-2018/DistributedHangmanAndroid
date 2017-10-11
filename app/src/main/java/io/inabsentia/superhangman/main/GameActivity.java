package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.ScoreDAO;
import io.inabsentia.superhangman.data.dto.ScoreDTO;
import io.inabsentia.superhangman.logic.GameLogic;

public class GameActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private ImageView hangmanImage;
    private TextView tvhiddenWord, tvUsedLetters, tvLife, tvWinCount;
    private EditText etGuess;
    private Button btnGuess;
    private Chronometer time;

    private final GameLogic logic = GameLogic.getInstance();
    private final ScoreDAO scoreDAO = ScoreDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /*
         * Initialize UI objects.
         */
        hangmanImage = (ImageView) findViewById(R.id.hangman_image);
        tvhiddenWord = (TextView) findViewById(R.id.hidden_word);
        tvUsedLetters = (TextView) findViewById(R.id.used_letters);
        tvLife = (TextView) findViewById(R.id.life);
        tvWinCount = (TextView) findViewById(R.id.loss_count);
        etGuess = (EditText) findViewById(R.id.guess);
        btnGuess = (Button) findViewById(R.id.btn_guess);
        time = (Chronometer) findViewById(R.id.time);

        btnGuess.setOnClickListener(this);

        time.setBase(SystemClock.elapsedRealtime());
        time.start();

        /*
         * We have to initialize the logic controller
         * before using it's functionality.
         */
        try {
            logic.init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        etGuess.setOnKeyListener(this);

        /*
         * If the logic controller initializing has gone well,
         * the display is updated with new values.
         */
        updateDisplay();

    }

    private void updateDisplay() {
        /* Update the current hidden word */
        tvhiddenWord.setText(logic.getHiddenWord());

        /* Update the current used letters list */
        tvUsedLetters.setText(logic.getUsedLetters());

        /* Update the current life amount message */
        tvLife.setText("Life: " + logic.getLife());

        /* Update the current score */
        tvWinCount.setText("Wins: " + logic.getWinCount());

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

    private void guess() {
        if (etGuess.getText().length() <= 0) return;

        /* Make sure to only get the first character of the input */
        char guess = etGuess.getText().toString().toLowerCase().charAt(0);

        /* Reset EditText current text for convenience */
        etGuess.setText("");

        /* Make the logic controller take a guess! */
        logic.guess(guess);

        /* Update the display, to see the next status after the guess */
        updateDisplay();

        /* Check whether the game is lost or not */
        if (logic.isLost()) {
            ScoreDTO scoreDTO = new ScoreDTO(false, logic.getRightGuessCount(), logic.getWrongGuessCount(), logic.getTotalGuessCount());
            scoreDAO.addScore(scoreDTO);
            time.stop();
            fireFinishActivity(false);
        }

        /* Check whether the game is won or not */
        if (logic.isWon()) {
            ScoreDTO scoreDTO = new ScoreDTO(true, logic.getRightGuessCount(), logic.getWrongGuessCount(), logic.getTotalGuessCount());
            scoreDAO.addScore(scoreDTO);
            time.stop();
            fireFinishActivity(true);
        }

    }

    private void fireFinishActivity(boolean isWon) {
        Intent intentFinish = new Intent(this, FinishActivity.class);

        if (isWon) {
            intentFinish.putExtra("status_msg", "Congratulations, you won!");
            intentFinish.putExtra("status_body", "You guessed '" + logic.getSecretWord() + "' in just " + logic.getTotalGuessCount() + " rounds! Dope!\n\nFeeling for another round? Fear not! Go to the main menu and press play once again!");
        } else {
            intentFinish.putExtra("status_msg", "Oh dear, you lost!");
            intentFinish.putExtra("status_body", "But don't worry! You can always play again.\n\nThe word was '" + logic.getSecretWord() + "'. Better luck next time!");
        }

        intentFinish.putExtra("game_status", isWon);
        intentFinish.putExtra("lives_left", logic.getLife());
        intentFinish.putExtra("secret_word", logic.getSecretWord());
        startActivity(intentFinish);
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        switch (view.getId()) {
            case R.id.guess:
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    guess();
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_guess:
                guess();
                break;
            default:
                break;
        }
    }

}