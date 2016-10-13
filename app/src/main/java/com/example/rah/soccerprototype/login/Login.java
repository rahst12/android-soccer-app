package com.example.rah.soccerprototype.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rah.soccerprototype.primaryview.MainActivity;
import com.example.rah.soccerprototype.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick_HomeScreen(View view){
        Log.d("soccerprototype", "open home screen");
        Intent homeScreenIntent = new Intent(this, MainActivity.class);
        homeScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeScreenIntent);
        finish();
    }
}
