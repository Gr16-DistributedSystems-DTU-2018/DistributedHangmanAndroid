package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.inabsentia.superhangman.R;

public class FinishActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvStatus, tvBodyStatus;
    private ImageView smileyView;
    private Button btnMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        tvStatus = (TextView) findViewById(R.id.status);
        tvBodyStatus = (TextView) findViewById(R.id.status_body);
        smileyView = (ImageView) findViewById(R.id.smiley_image);
        btnMainMenu = (Button) findViewById(R.id.btn_main_menu);

        btnMainMenu.setOnClickListener(this);

        Intent intentFinish = getIntent();
        Bundle extras = intentFinish.getExtras();

        if (extras != null) {
            boolean isWon = extras.getBoolean("game_status");

            tvStatus.setText(extras.getString("status_msg"));
            tvBodyStatus.setText(extras.getString("status_body"));

            if (isWon) smileyView.setImageResource(R.drawable.happy_smiley);
            else smileyView.setImageResource(R.drawable.sad_smiley);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_main_menu:
                Intent intentMainMenu = new Intent(this, MainActivity.class);
                startActivity(intentMainMenu);
                break;
            default:
                break;
        }
    }

}