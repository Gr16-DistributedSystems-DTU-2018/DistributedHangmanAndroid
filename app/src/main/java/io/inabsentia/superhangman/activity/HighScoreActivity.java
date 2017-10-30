package io.inabsentia.superhangman.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.HighScoreDAO;
import io.inabsentia.superhangman.data.dao.IHighScoreDAO;
import io.inabsentia.superhangman.data.dto.HighScoreDTO;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private Button btnReset;

    private ListView highScoreListView;
    private ArrayAdapter listViewAdapter;

    private List<HighScoreDTO> highScoreList;

    private IHighScoreDAO highScoreDAO = HighScoreDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore_activity);

        /*
         * Get all the high scores.
         */
        try {
            highScoreList = highScoreDAO.getAll();
        } catch (IHighScoreDAO.DALException e) {
            e.printStackTrace();
        }

        /*
         * Instantiate objects.
         */
        tvTitle = (TextView) findViewById(R.id.score_title);
        btnReset = (Button) findViewById(R.id.btn_reset_high_scores);
        highScoreListView = (ListView) findViewById(R.id.high_score_list);

        listViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, highScoreList);
        highScoreListView.setAdapter(listViewAdapter);

        /*
         * Set I/O listeners.
         */
        btnReset.setOnClickListener(this);

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
                updateDisplay();
                break;
            default:
                break;
        }
    }

    private void updateDisplay() {
        try {
            highScoreList = highScoreDAO.getAll();
            highScoreListView.removeAllViewsInLayout();
        } catch (IHighScoreDAO.DALException e) {
            e.printStackTrace();
        }
    }

    private void resetHighScores() {
        try {
            highScoreDAO.removeAll();
            highScoreDAO.save(getApplicationContext());
        } catch (IHighScoreDAO.DALException e) {
            Toast.makeText(getApplicationContext(), R.string.score_reset_failed, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}