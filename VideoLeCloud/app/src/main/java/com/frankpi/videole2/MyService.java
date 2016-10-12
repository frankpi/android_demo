package com.frankpi.videole2;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by frankpi on 16-6-28.
 */
public class MyService extends Service {
    private static final String TAG = "gameassist";
    private Timer leTimer;

    @Override
    public void onCreate() {
        Log.i(TAG, "ExampleService-onCreate");
        super.onCreate();
    }

    public boolean isForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(TAG, "前台" + appProcess.processName);
                    return true;
                } else {
                    Log.i(TAG, "后台" + appProcess.processName);
                    onStartActivity();
                    return false;
                }
            }
        }
        return false;
    }

    private void onStartActivity() {
        Log.i(TAG, "onStartActivity");
//        Intent intentActivity = new Intent();
//        intentActivity.setClass(this, MainActivity.class); // 销毁时重新启动Service
//        startActivity(intentActivity);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 执行文件的下载或者播放等操作
        Log.i(TAG, "ExampleService-onStartCommand");
        if (leTimer == null) {
            Log.i(TAG, "ExampleService-onStartCommand");
            leTimer = new Timer();
            leTimer.schedule(new TimerTask() {
                @Override
                public void run() {//定时更新
                    // TODO Auto-generated method stub
                    isForeground(getApplicationContext());
                }

            }, 1000 * 3, 1000 * 3600);
        }
        /*
         * 这里返回状态有三个值，分别是:
		 * 1、START_STICKY：当服务进程在运行时被杀死，系统将会把它置为started状态，但是不保存其传递的Intent对象
		 * ，之后，系统会尝试重新创建服务;
		 * 2、START_NOT_STICKY：当服务进程在运行时被杀死，并且没有新的Intent对象传递过来的话，
		 * 系统将会把它置为started状态， 但是系统不会重新创建服务，直到startService(Intent intent)方法再次被调用;
		 * 3、START_REDELIVER_INTENT：当服务进程在运行时被杀死，它将会在隔一段时间后自动创建，
		 * 并且最后一个传递的Intent对象将会再次传递过来。
		 */
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "ExampleService-onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "ExampleService-onDestroy");
//        unregisterReceiver(mTickReceiver);
        Intent localIntent = new Intent();
        localIntent.setClass(this, MyService.class); // 销毁时重新启动Service
        startService(localIntent);
        super.onDestroy();
    }

}