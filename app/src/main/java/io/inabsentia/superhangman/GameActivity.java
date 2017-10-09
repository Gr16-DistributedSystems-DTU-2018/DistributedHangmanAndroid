package io.inabsentia.superhangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnKeyListener {

    private ImageView hangmanImage;
    private TextView tvhiddenWord, tvUsedLetters, tvWrongGuess;
    private EditText etGuess;

    private final GameLogic logic = GameLogic.getInstance();

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
        tvWrongGuess = (TextView) findViewById(R.id.wrong_guess_msg);
        etGuess = (EditText) findViewById(R.id.guess);

        hangmanImage.setTag(6);

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

        /* Remove the title from the navigation bar */
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void updateDisplay() {
        /* Update the current hidden word */
        tvhiddenWord.setText(logic.getHiddenWord());

        /* Update the current used letters list */
        tvUsedLetters.setText(logic.getUsedLetters());

        /* Update the current wrong guess amount message */
        tvWrongGuess.setText(logic.getWrongGuessMsg());

        /* Update the image according to the amount of lives left */
        switch (logic.getLives()) {
            case 6:
                hangmanImage.setImageResource(R.drawable.galge);
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

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        switch (view.getId()) {
            case R.id.guess:
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    /* Make sure to only get the first character of the input */
                    char guess = etGuess.getText().toString().charAt(0);

                    /* Reset EditText current text for convenience */
                    etGuess.setText("");

                    /* Make the logic controller take a guess! */
                    logic.guess(guess);

                    /* Update the display, to see the next status after the guess */
                    updateDisplay();

                    if (logic.isDead()) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("LOST_MESSAGE", "You have lost! Try again!");
                        startActivity(intent);
                    }

                    if (logic.isWon()) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("WIN_MESSAGE", "Congratulations, you won!");
                        startActivity(intent);
                    }

                    return true;
                }
                return false;
            default:
                return false;
        }
    }

}