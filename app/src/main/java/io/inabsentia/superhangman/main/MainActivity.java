package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import io.inabsentia.superhangman.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvWelcome;
    private Button btnPlay, btnMatchHistory, btnHighScores, btnGuide;
    private ImageView welcomeImage;

    private static MediaPlayer mediaPlayer;
    private static Random random;

    private static final int MAXIMUM_IMAGE_ROT = 5000;
    private static boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = (TextView) findViewById(R.id.welcome_msg);
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnMatchHistory = (Button) findViewById(R.id.btn_match_history);
        btnHighScores = (Button) findViewById(R.id.btn_high_scores);
        btnGuide = (Button) findViewById(R.id.btn_guide);
        welcomeImage = (ImageView) findViewById(R.id.welcome_img);

        btnPlay.setOnClickListener(this);
        btnMatchHistory.setOnClickListener(this);
        btnHighScores.setOnClickListener(this);
        btnGuide.setOnClickListener(this);
        welcomeImage.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        random = new Random();

        if (!mediaPlayer.isPlaying() && !isPlaying) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                welcomeImage.setRotation(0);
                Intent intentGame = new Intent(this, GameActivity.class);
                startActivity(intentGame);
                break;
            case R.id.btn_match_history:
                welcomeImage.setRotation(0);
                // Not yet implemented!
                Toast.makeText(view.getContext(), "This feature is not yet implemented!", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_high_scores:
                welcomeImage.setRotation(0);
                Intent intentScores = new Intent(this, HighScoresActivity.class);
                startActivity(intentScores);
                break;
            case R.id.btn_guide:
                welcomeImage.setRotation(0);
                Intent intentGuide = new Intent(this, GuideActivity.class);
                startActivity(intentGuide);
                break;
            case R.id.welcome_img:
                welcomeImage.setRotation(random.nextFloat() * MAXIMUM_IMAGE_ROT);
                break;
            default:
                break;
        }
    }

}