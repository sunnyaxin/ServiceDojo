package com.dojo.sunny.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LogService extends Service {

    public static final String TAG = "LogService";

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "on create", Toast.LENGTH_SHORT).show();
        Log.e(TAG, ": on create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, ": on start command");
        Toast.makeText(this, "on start command", Toast.LENGTH_SHORT).show();
        String str = intent.getExtras().getString("service_string");
        Log.e(TAG, str);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "on bind", Toast.LENGTH_SHORT).show();
        Log.e(TAG, ": on bind");
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "on un bind", Toast.LENGTH_SHORT).show();
        Log.e(TAG, ": on un bind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "on destroy", Toast.LENGTH_SHORT).show();
        Log.e(TAG, ": on destroy");
    }
}
