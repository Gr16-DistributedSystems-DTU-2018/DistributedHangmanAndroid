package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.inabsentia.superhangman.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvWelcome, tvStatusMsg;
    private Button btnPlay, btnGuide;

    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = (TextView) findViewById(R.id.welcome_msg);
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnGuide = (Button) findViewById(R.id.btn_guide);

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        if (!mediaPlayer.isPlaying() && !isPlaying) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            isPlaying = true;
        }

        btnPlay.setOnClickListener(this);
        btnGuide.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                Intent intentGame = new Intent(this, GameActivity.class);
                startActivity(intentGame);
                break;
            case R.id.btn_scores:
                Intent intentScores = new Intent(this, GuideActivity.class);
                startActivity(intentScores);
                break;
            case R.id.btn_guide:
                Intent intentGuide = new Intent(this, GuideActivity.class);
                startActivity(intentGuide);
            default:
                break;
        }
    }

}