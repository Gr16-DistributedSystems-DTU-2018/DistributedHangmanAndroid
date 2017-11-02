package io.inabsentia.superhangman.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.asynctask.AsyncDownloadWords;
import io.inabsentia.superhangman.data.dao.DALException;
import io.inabsentia.superhangman.data.dao.IMatchDAO;
import io.inabsentia.superhangman.data.dao.MatchDAO;
import io.inabsentia.superhangman.helper.Utils;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCustomTitle;
    private Button btnPlay, btnMatchHistory, btnHighScores, btnGuide;
    private ImageView welcomeImage;

    private static Random random = new Random();

    private static MediaPlayer mediaPlayer;

    private static final int MAXIMUM_IMAGE_ROT = 5000;
    private static boolean isPlaying = false;

    private final Utils utils = Utils.getInstance();
    private final IMatchDAO matchDAO = MatchDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        /* Execute AsyncTask for download of words */
        new AsyncDownloadWords().execute();

        /*
         * Check to see if the intro should be ran or not.
         */
        introCheck();

        /*
         * Instantiate objects.
         */
        tvCustomTitle = findViewById(R.id.action_bar_title);
        btnPlay = findViewById(R.id.btn_play);
        btnMatchHistory = findViewById(R.id.btn_match_history);
        btnHighScores = findViewById(R.id.btn_high_scores);
        btnGuide = findViewById(R.id.btn_guide);
        welcomeImage = findViewById(R.id.welcome_img);

        /* Set title of action bar */
        tvCustomTitle.setText(R.string.welcome_title);

        mediaPlayer = MediaPlayer.create(this, R.raw.game_music);

        /*
         * Set I/O listeners.
         */
        btnPlay.setOnClickListener(this);
        btnMatchHistory.setOnClickListener(this);
        btnHighScores.setOnClickListener(this);
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
            matchDAO.load(getApplicationContext());
            matchDAO.save(getApplicationContext());
        } catch (DALException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MenuActivity.this, SettingsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                welcomeImage.setRotation(0);
                startActivity(new Intent(this, PreGameActivity.class));
                break;
            case R.id.btn_match_history:
                welcomeImage.setRotation(0);
                startActivity(new Intent(this, MatchHistoryActivity.class));
                break;
            case R.id.btn_high_scores:
                welcomeImage.setRotation(0);
                startActivity(new Intent(this, HighScoreActivity.class));
                break;
            case R.id.btn_guide:
                welcomeImage.setRotation(0);
                startActivity(new Intent(this, GuideActivity.class));
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
        //PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().remove(key).apply();

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