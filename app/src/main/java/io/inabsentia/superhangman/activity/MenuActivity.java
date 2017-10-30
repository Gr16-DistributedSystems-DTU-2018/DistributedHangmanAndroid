package io.inabsentia.superhangman.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.HighScoreDAO;
import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.helper.Utils;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvWelcome;
    private Button btnPlay, btnMatchHistory, btnHighScores, btnSettings, btnGuide;
    private ImageView welcomeImage;

    private static MediaPlayer mediaPlayer;
    private static Random random;

    private static final int MAXIMUM_IMAGE_ROT = 5000;
    private static boolean isPlaying = false;

    private final Utils utils = Utils.getInstance();
    private final IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        /*
         * Check to see if the intro should be ran or not.
         */
        introCheck();

        /*
         * Instantiate objects.
         */
        tvWelcome = (TextView) findViewById(R.id.welcome_msg);
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnMatchHistory = (Button) findViewById(R.id.btn_match_history);
        btnHighScores = (Button) findViewById(R.id.btn_high_scores);
        btnSettings = (Button) findViewById(R.id.btn_settings);
        btnGuide = (Button) findViewById(R.id.btn_guide);
        welcomeImage = (ImageView) findViewById(R.id.welcome_img);

        mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
        random = new Random();

        /*
         * Set I/O listeners.
         */
        btnPlay.setOnClickListener(this);
        btnMatchHistory.setOnClickListener(this);
        btnHighScores.setOnClickListener(this);
        btnSettings.setOnClickListener(this);
        btnGuide.setOnClickListener(this);
        welcomeImage.setOnClickListener(this);

        /*
         * Start playing sound track if MUSIC_ENABLED
         * is equal to true.
         */
        if (!mediaPlayer.isPlaying() && !isPlaying && utils.MUSIC_ENABLED) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            isPlaying = true;
        }

        /*
         * Load high scores from internal storage.
         */
        try {
            highScoreDAO.load(getApplicationContext());
            highScoreDAO.save(getApplicationContext());
        } catch (IHighScoreDAO.DALException e) {
            e.printStackTrace();
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
                Intent intentMatchHistory = new Intent(this, MatchHistoryActivity.class);
                startActivity(intentMatchHistory);
                break;
            case R.id.btn_high_scores:
                welcomeImage.setRotation(0);
                Intent intentScores = new Intent(this, HighScoreActivity.class);
                startActivity(intentScores);
                break;
            case R.id.btn_settings:
                welcomeImage.setRotation(0);
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentSettings);
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

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    private void introCheck() {
        String key = "firstStart" + R.string.app_name;

        /* Uncomment this line to get the intro back */
        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().remove(key).apply();

        /* Declare a new thread to do a preference check */
        Thread thread = new Thread(() -> {

            /* Initialize SharedPreferences */
            SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

            /* Create a new boolean and preference and set it to true */
            boolean isFirstStart = getPrefs.getBoolean(key, true);

            /* If the activity has never started before... */
            if (isFirstStart) {
                /* Launch app intro */
                final Intent intentIntro = new Intent(MenuActivity.this, IntroActivity.class);
                runOnUiThread(() -> startActivity(intentIntro));

                /* Make a new preferences editor */
                SharedPreferences.Editor e = getPrefs.edit();

                /* Edit preference to make it false because we don't want this to run again */
                e.putBoolean(key, false);

                /* Apply the changes */
                e.apply();
            }
        });

        /* Start the thread */
        thread.start();
    }

}