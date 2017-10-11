package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.ScoreDAO;
import io.inabsentia.superhangman.data.dto.ScoreDTO;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private ScoreDAO scoreDAO = ScoreDAO.getInstance();

    private TextView tvTitle, tvScoreField;
    private Button btnMainMenu;

    private List<ScoreDTO> scoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tvTitle = (TextView) findViewById(R.id.score_title);
        tvScoreField = (TextView) findViewById(R.id.score_view);
        btnMainMenu = (Button) findViewById(R.id.btn_score_main_menu);

        scoresList = scoreDAO.getScores();

        if (scoresList.size() <= 0) {
            tvScoreField.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            tvScoreField.setText("Oops, nothing to see here!\nPlay a few rounds and come back!");
        } else {
            for (int i = 0; i < scoresList.size(); i++) {
                ScoreDTO dto = scoresList.get(i);

                String gameStatus = "Loss";
                if (dto.isWon()) gameStatus = "Win";

                tvScoreField.append("Game " + (i + 1) + ": " + gameStatus + ", " + dto.getRightGuessCount() + " right guesses, " + dto.getWrongGuessCount() + " wrong and " + dto.getTotalGuessCount() + " in total.\n\n");
            }
        }

        btnMainMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_score_main_menu:
                Intent intentMainMenu = new Intent(this, MainActivity.class);
                startActivity(intentMainMenu);
                break;
            default:
                break;
        }
    }

}