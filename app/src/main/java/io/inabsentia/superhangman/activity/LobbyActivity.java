package io.inabsentia.superhangman.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.adapter.LobbyAdapter;
import io.inabsentia.superhangman.item.LobbyItem;

public class LobbyActivity extends AppCompatActivity {

    private ListView listView;
    private LobbyAdapter adapter;
    private List<LobbyItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lobby_activity);

        // get the items here

        listView = findViewById(R.id.list);
        adapter = new LobbyAdapter(this, R.layout.lobby_item, items);
        listView.setAdapter(adapter);
    }

}