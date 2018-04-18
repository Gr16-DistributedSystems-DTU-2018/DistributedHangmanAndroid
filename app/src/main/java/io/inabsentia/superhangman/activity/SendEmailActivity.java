package io.inabsentia.superhangman.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.inabsentia.superhangman.R;
import io.inabsentia.superhangman.retrofit.RetrofitClient;
import io.inabsentia.superhangman.retrofit.interfaces.StringCallback;

public class SendEmailActivity extends AppCompatActivity implements View.OnClickListener{

    private Button send_email;
    private EditText receivers_username, receivers_password, email_subject, message;

    private RetrofitClient retro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_email_activity);

        retro = new RetrofitClient(getApplicationContext());

        /* Instantiate*/
        send_email = findViewById(R.id.send_email);
        receivers_username = findViewById(R.id.receivers_username);
        receivers_password = findViewById(R.id.receivers_password);
        email_subject = findViewById(R.id.email_subject);
        message = findViewById(R.id.message);

        /* Set on click listener */
        send_email.setOnClickListener(this);
        receivers_username.setOnClickListener(this);
        receivers_password.setOnClickListener(this);
        email_subject.setOnClickListener(this);
        message.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.send_email) {
           //Retro fit, send email
           retro.sendEmail(receivers_username.getText().toString(), receivers_password.getText().toString(),email_subject.getText().toString(), message.getText().toString() + "\n\nSendt via - Gruppe 16 - DistributedHangman - Android", new StringCallback() {

               @Override
               public void onSuccess(String value) {
                   Toast.makeText(getApplicationContext(), "Email sent to " + receivers_username.getText().toString() + ".", Toast.LENGTH_LONG).show();
                   Intent initGame = new Intent(getApplicationContext(), MenuActivity.class);
                   startActivity(initGame);
               }

               @Override
               public void onFailure() {
                    Toast.makeText(getApplicationContext(), "Failed to send email, please try again.", Toast.LENGTH_LONG).show();
               }
           });
       }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent initGame = new Intent(this, MenuActivity.class);
        startActivity(initGame);
        System.out.println("Trying to start MenuActivity.");
    }
}
