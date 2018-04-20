package io.inabsentia.superhangman.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.adapter.HighScoreAdapter;
import io.inabsentia.superhangman.item.HighScoreItem;
import io.inabsentia.superhangman.retrofit.RetrofitClient;
import io.inabsentia.superhangman.retrofit.interfaces.ListHighscoreItemCallback;
import io.inabsentia.superhangman.singleton.App;

public class HighScoreActivity extends AppCompatActivity {

    private ListView listView;
    private HighScoreAdapter adapter;
    private List<HighScoreItem> items;
    private RetrofitClient retrofitClient;
    private Context mContext;
    private TextView tvCustomTitle;

    private App app = App.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        retrofitClient = new RetrofitClient(this);
        this.mContext = getApplicationContext();

        tvCustomTitle = findViewById(R.id.action_bar_title);
        tvCustomTitle.setText("High Scores");

        retrofitClient.getAllUsersHighscore(new ListHighscoreItemCallback() {
            @Override
            public void onSuccess(List<HighScoreItem> list) {
                HighScoreActivity.this.items = list;
                listView = findViewById(R.id.highscore_list);
                adapter = new HighScoreAdapter(mContext, R.layout.high_score_item, items);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure() {
                Toast.makeText(mContext, "Failed to fetch highscore items!", Toast.LENGTH_LONG).show();
            }
        });

    }

}