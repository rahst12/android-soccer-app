package com.example.rah.soccerprototype.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rah.soccerprototype.R;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void onClick_login(View view) {
        Log.d("soccerprototype", "open login activity");
        Intent loginIntent = new Intent(this, Login.class);
        startActivity(loginIntent);
    }

    public void onClick_Register(View view) {
        Log.d("soccerprototype", "open register activity");
        Intent registerIntent = new Intent(this, Register.class);
        startActivity(registerIntent);
    }
}
