package io.inabsentia.superhangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvWelcome, tvStatusMsg;
    private Button btnPlay, btnGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = (TextView) findViewById(R.id.welcome_msg);
        tvStatusMsg = (TextView) findViewById(R.id.status_msg);
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnGuide = (Button) findViewById(R.id.btn_guide);

        btnPlay.setOnClickListener(this);
        btnGuide.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            if (extras.getString("LOST_MESSAGE") == null) {
                tvWelcome.setText(extras.getString("WIN_MESSAGE"));
            } else {
                tvWelcome.setText(extras.getString("LOST_MESSAGE"));
            }
            tvStatusMsg.setText(extras.getString("STATUS"));
        }

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                Intent intentGame = new Intent(this, GameActivity.class);
                startActivity(intentGame);
                break;
            case R.id.btn_guide:
                Intent intentGuide = new Intent(this, GuideActivity.class);
                startActivity(intentGuide);
            default:
                break;
        }
    }

}