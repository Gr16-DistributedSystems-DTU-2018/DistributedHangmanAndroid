package io.inabsentia.superhangman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.helper.Utils;
import io.inabsentia.superhangman.logic.GameLogic;

public class FinishActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStatus, tvBodyStatus;
    private ImageView smileyImage;
    private Button btnContinue;
    private boolean isWon = false;

    private final Utils utils = Utils.getInstance();
    private final GameLogic logic = GameLogic.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_activity);

        /*
         * Instantiate objects.
         */
        tvStatus = (TextView) findViewById(R.id.status);
        tvBodyStatus = (TextView) findViewById(R.id.status_body);
        smileyImage = (ImageView) findViewById(R.id.smiley_image);
        btnContinue = (Button) findViewById(R.id.btn_continue);

        /*
         * Set I/O listeners.
         */
        btnContinue.setOnClickListener(this);

        /*
         * Get intent from GameActivity.
         */
        Intent intentFinish = getIntent();
        Bundle extras = intentFinish.getExtras();

        if (extras != null) {
            isWon = extras.getBoolean("game_status");
            String secretWord = extras.getString("secret_word");
            int totalGuessCount = extras.getInt("round_count");

            if (isWon) {
                smileyImage.setImageResource(R.drawable.happy_smiley);
                tvStatus.setText(R.string.status_label_won);

                String bodyStatus = getResources().getString(R.string.body_status_label_won, secretWord, totalGuessCount);
                tvBodyStatus.setText(bodyStatus);
            } else {
                smileyImage.setImageResource(R.drawable.sad_smiley);
                tvStatus.setText(R.string.status_label_lost);

                String bodyStatus = getResources().getString(R.string.body_status_label_loss, secretWord);
                tvBodyStatus.setText(bodyStatus);

                utils.createScoreAndReset(getApplicationContext());

                // Can't continue if you didn't win.
                btnContinue.setVisibility(View.INVISIBLE);
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                Intent intentPlay = new Intent(this, GameActivity.class);
                startActivity(intentPlay);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isWon) utils.createScoreAndReset(getApplicationContext());

        try {
            logic.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intentMenu = new Intent(this, MenuActivity.class);
        startActivity(intentMenu);
    }

}