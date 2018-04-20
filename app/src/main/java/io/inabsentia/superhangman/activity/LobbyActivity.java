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
import io.inabsentia.superhangman.adapter.LobbyAdapter;
import io.inabsentia.superhangman.item.LobbyItem;
import io.inabsentia.superhangman.retrofit.RetrofitClient;
import io.inabsentia.superhangman.retrofit.interfaces.ListLobbyItemCallback;
import io.inabsentia.superhangman.singleton.App;

public class LobbyActivity extends AppCompatActivity {

    private ListView listView;
    private LobbyAdapter adapter;
    private List<LobbyItem> items;
    private RetrofitClient retrofitClient;
    private Context mContext;
    private TextView tvCustomTitle;

    private App app = App.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_activity);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        retrofitClient = new RetrofitClient(this);
        this.mContext = getApplicationContext();

        tvCustomTitle = findViewById(R.id.action_bar_title);
        tvCustomTitle.setText("Lobby");

        retrofitClient.getAllLoggedInUsersScore(new ListLobbyItemCallback() {
            @Override
            public void onSuccess(List<LobbyItem> list) {
                LobbyActivity.this.items = list;
                listView = findViewById(R.id.list_lobby);
                adapter = new LobbyAdapter(getApplicationContext(), R.layout.lobby_item, items);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure() {
                Toast.makeText(mContext, "Failed to fetch lobby items!", Toast.LENGTH_LONG).show();
            }
        });

    }

}