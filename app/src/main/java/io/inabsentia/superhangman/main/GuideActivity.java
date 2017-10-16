package io.inabsentia.superhangman.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.inabsentia.superhangman.R;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        btnMenu = (Button) findViewById(R.id.btn_guide_menu);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_guide_menu:
                Intent intentMainMenu = new Intent(this, MenuActivity.class);
                startActivity(intentMainMenu);
                break;
            default:
                break;
        }
    }

    /*
     * Disables back button.
     */
    @Override
    public void onBackPressed() {

    }

}