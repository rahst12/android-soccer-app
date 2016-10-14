package com.example.rah.soccerprototype.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.rah.soccerprototype.primaryview.MainActivity;
import com.example.rah.soccerprototype.R;
import com.example.rah.soccerprototype.util.ConnectionUtils;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClick_HomeScreen(View view){

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (ConnectionUtils.isConnected(connMgr))
        {
            //Do something
            Log.d("soccerprototype", "open home screen");
            Intent homeScreenIntent = new Intent(this, MainActivity.class);
            homeScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeScreenIntent);
            finish();
        } else {
            //Do something else
            Log.d("soccerprototype", "NOT connected to the network");
            Context context = getApplicationContext();
            CharSequence text = "Please connect to a network to login.";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();
        }
    }
}
