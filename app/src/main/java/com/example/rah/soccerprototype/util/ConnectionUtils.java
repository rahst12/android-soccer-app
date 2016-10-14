package com.example.rah.soccerprototype.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ryana on 10/13/2016.
 */

public class ConnectionUtils {
    public static boolean isConnected(ConnectivityManager connMgr){
        //TODO: Throw exception in the future here if connMgr is null
        if (connMgr != null) {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            } else {
                return false;
                //textView.setText("No network connection available.");
            }
        } else {
            return false;
        }
    }
}
