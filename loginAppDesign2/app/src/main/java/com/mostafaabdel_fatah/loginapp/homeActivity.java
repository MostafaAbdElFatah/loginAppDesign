package com.mostafaabdel_fatah.loginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class homeActivity extends AppCompatActivity {

    TextView welcometext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        welcometext = (TextView) findViewById(R.id.welcome);
        String message = getIntent().getStringExtra("message");
        welcometext.setText(message);
    }
}
