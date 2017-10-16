package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.HighScoreDAO;
import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle, tvScore;
    private Button btnReset, btnMenu;

    private List<HighScoreDTO> highScoreList;

    private IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        /*
         * Instantiate objects.
         */
        tvTitle = (TextView) findViewById(R.id.score_title);
        tvScore = (TextView) findViewById(R.id.high_score_view);
        btnReset = (Button) findViewById(R.id.btn_reset_high_scores);
        btnMenu = (Button) findViewById(R.id.btn_high_score_menu);

        /*
         * Set I/O listeners.
         */
        btnReset.setOnClickListener(this);
        btnMenu.setOnClickListener(this);

        /*
         * Get all the high scores.
         */
        try {
            highScoreList = highScoreDAO.getAll();
        } catch (IHighScoreDAO.DALException e) {
            e.printStackTrace();
        }

        /*
         * Update the display.
         */
        updateDisplay();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_high_scores:
                resetHighScores();
                break;
            case R.id.btn_high_score_menu:
                Intent intentMenu = new Intent(this, MenuActivity.class);
                startActivity(intentMenu);
                break;
            default:
                break;
        }
    }

    private void updateDisplay() {
        try {
            highScoreList = highScoreDAO.getAll();
        } catch (IHighScoreDAO.DALException e) {
            e.printStackTrace();
        }

        if (highScoreList.size() <= 0) {
            tvScore.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            tvScore.setText(R.string.score_body_label_tmp);
        } else {
            for (int i = 0; i < highScoreList.size(); i++) {
                HighScoreDTO dto = highScoreList.get(i);
                String avgTimeSpent = String.format(Locale.ENGLISH, "%.2f", (double) Math.round(dto.getAvgRoundTime()));

                String scoreString = getResources().getString(R.string.score_object_label, dto.getScore(), dto.getWinCount(), avgTimeSpent, dto.getLastWord());
                tvScore.append(scoreString);

                if (i != highScoreList.size() - 1)
                    tvScore.append("\n\n" + getResources().getString(R.string.score_delimiter) + "\n\n");
            }
        }
    }

    private void resetHighScores() {
        try {
            highScoreDAO.removeAll();
            highScoreDAO.save(getApplicationContext());
            updateDisplay();
        } catch (IHighScoreDAO.DALException e) {
            Toast.makeText(getApplicationContext(), R.string.score_reset_failed, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /*
     * Disables back button.
     */
    @Override
    public void onBackPressed() {

    }

}