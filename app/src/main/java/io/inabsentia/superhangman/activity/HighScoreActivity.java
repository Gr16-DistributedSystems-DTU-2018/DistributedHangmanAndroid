package io.inabsentia.superhangman.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.data.dao.IMatchDAO;
import io.inabsentia.superhangman.data.dao.MatchDAO;
import io.inabsentia.superhangman.data.dto.MatchDTO;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCustomTitle;
    private Button btnReset;

    private ListView highScoreListView;
    private ArrayAdapter listViewAdapter;

    private List<MatchDTO> highScoreList;

    private IMatchDAO matchDAO = MatchDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_history_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        /*
         * Get all the high scores.
         */
        try {
            highScoreList = matchDAO.getAll();
        } catch (IMatchDAO.DALException e) {
            e.printStackTrace();
        }

        /*
         * Instantiate objects.
         */
        tvCustomTitle = (TextView) findViewById(R.id.action_bar_title);
        btnReset = (Button) findViewById(R.id.btn_reset_match_history);
        highScoreListView = (ListView) findViewById(R.id.match_history_list);

        listViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, highScoreList);
        highScoreListView.setAdapter(listViewAdapter);

        /* Set title of action bar */
        tvCustomTitle.setText(R.string.high_score_title_label);

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
            case R.id.btn_reset_match_history:
                resetHistory();
                updateDisplay();
                break;
            default:
                break;
        }
    }

    private void updateDisplay() {
        try {
            highScoreList = matchDAO.getAll();
            highScoreListView.removeAllViewsInLayout();
        } catch (IMatchDAO.DALException e) {
            e.printStackTrace();
        }
    }

    private void resetHistory() {
        try {
            matchDAO.removeAll();
            matchDAO.save(getApplicationContext());
        } catch (IMatchDAO.DALException e) {
            Toast.makeText(getApplicationContext(), R.string.match_history_reset_failed, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}