package com.frankpi.videole2;

import com.frankpi.utils.CrashHandler;
import com.frankpi.utils.Log2;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        Intent localIntent = new Intent();
        localIntent.setClass(getApplicationContext(), MyService.class);
        startService(localIntent);
    }
}
