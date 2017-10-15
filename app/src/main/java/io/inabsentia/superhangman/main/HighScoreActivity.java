package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.data.dao.PHighScoreDAO;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private IHighScoreDAO highScoreDAO = PHighScoreDAO.getInstance();

    private TextView tvTitle, tvScoreField;
    private Button btnMainMenu;

    private List<HighScoreDTO> highScoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        tvTitle = (TextView) findViewById(R.id.score_title);
        tvScoreField = (TextView) findViewById(R.id.high_score_view);
        btnMainMenu = (Button) findViewById(R.id.btn_score_main_menu);

        try {
            highScoreList = highScoreDAO.getAll();
        } catch (IHighScoreDAO.DALException e) {
            e.printStackTrace();
        }

        if (highScoreList.size() <= 0) {
            tvScoreField.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            tvScoreField.setText("Oops, nothing to see here!\nPlay a game and come back!");
        } else {
            for (int i = 0; i < highScoreList.size(); i++) {
                HighScoreDTO dto = highScoreList.get(i);
                String avgRoundTime = String.format(Locale.ENGLISH, "%.2f", (double) Math.round(dto.getAvgRoundTime()));

                tvScoreField.append("Score: " + dto.getScore() + ", Wins: " + dto.getWinCount() + ", Round time: " + avgRoundTime + "s, Last word: " + dto.getLostWord());
                if (i != highScoreList.size() - 1) tvScoreField.append("\n\n●\n\n");
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