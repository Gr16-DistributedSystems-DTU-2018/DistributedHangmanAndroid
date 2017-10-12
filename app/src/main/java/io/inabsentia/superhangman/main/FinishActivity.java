package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.inabsentia.superhangman.R;

public class FinishActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStatus, tvBodyStatus;
    private ImageView smileyView;
    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        tvStatus = (TextView) findViewById(R.id.status);
        tvBodyStatus = (TextView) findViewById(R.id.status_body);
        smileyView = (ImageView) findViewById(R.id.smiley_image);
        btnMenu = (Button) findViewById(R.id.btn_finish_menu);

        btnMenu.setOnClickListener(this);

        Intent intentFinish = getIntent();
        Bundle extras = intentFinish.getExtras();

        if (extras != null) {
            boolean isWon = extras.getBoolean("game_status");
            String secretWord = extras.getString("secret_word");
            int totalGuessCount = extras.getInt("total_guess_count");

            if (isWon) {
                smileyView.setImageResource(R.drawable.happy_smiley);
                tvStatus.setText(R.string.won_title_status_msg);

                String bodyStatus = getResources().getString(R.string.won_status_body_msg, secretWord, totalGuessCount);
                tvBodyStatus.setText(bodyStatus);
            } else {
                smileyView.setImageResource(R.drawable.sad_smiley);
                tvStatus.setText(R.string.loss_title_status_msg);

                String bodyStatus = getResources().getString(R.string.loss_status_body_msg, secretWord);
                tvBodyStatus.setText(bodyStatus);
            }

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_finish_menu:
                Intent intentMainMenu = new Intent(this, MainActivity.class);
                startActivity(intentMainMenu);
                break;
            default:
                break;
        }
    }

}