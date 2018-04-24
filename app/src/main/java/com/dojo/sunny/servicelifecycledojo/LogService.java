package com.example.twcn.servicelifecycledojo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LogService extends Service {

    public static final String TAG = "LogService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, ": on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, ": on start command");
        String str = intent.getExtras().getString("service_string");
        Log.e(TAG, str);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, ": on bind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, ": on un bind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, ": on destroy");
    }
}
