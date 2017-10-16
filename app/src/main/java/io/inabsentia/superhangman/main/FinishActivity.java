package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.utils.Utils;

public class FinishActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStatus, tvBodyStatus;
    private ImageView smileyImage;
    private Button btnContinue, btnMenu;

    private final Utils utils = Utils.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        /*
         * Instantiate objects.
         */
        tvStatus = (TextView) findViewById(R.id.status);
        tvBodyStatus = (TextView) findViewById(R.id.status_body);
        smileyImage = (ImageView) findViewById(R.id.smiley_image);
        btnContinue = (Button) findViewById(R.id.btn_continue);
        btnMenu = (Button) findViewById(R.id.btn_finish_menu);

        /*
         * Set I/O listeners.
         */
        btnContinue.setOnClickListener(this);
        btnMenu.setOnClickListener(this);

        /*
         * Get intent from GameActivity.
         */
        Intent intentFinish = getIntent();
        Bundle extras = intentFinish.getExtras();

        if (extras != null) {
            boolean isWon = extras.getBoolean("game_status");
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
            case R.id.btn_finish_menu:
                utils.createScoreAndReset(getApplicationContext());
                Intent intentMenu = new Intent(this, MenuActivity.class);
                startActivity(intentMenu);
                break;
            default:
                break;
        }
    }

    /*
     * Disables back button.
     */
    @Override
    public void onBackPressed() {

    }

}