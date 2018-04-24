package com.dojo.sunny.servicelifecycledojo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.twcn.myapplication.R;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener {

    public static final String TAG = "LogService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.start_service);
        Button bind = findViewById(R.id.bind_service);
        Button unbind = findViewById(R.id.unbind_service);
        Button stop = findViewById(R.id.stop_service);
        Button jump = findViewById(R.id.jump_next_page);

        start.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        stop.setOnClickListener(this);
        jump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, LogService.class);
                startIntent.putExtra("service_string", "start_service_string");
                startService(startIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, LogService.class);
//                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                bindService(bindIntent, connection, BIND_ABOVE_CLIENT);
                break;
            case R.id.unbind_service:
                unbindService(connection);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, LogService.class);
                stopService(stopIntent);
                break;
            case R.id.jump_next_page:
                Intent intent = new Intent(this, HelloActivity.class);
                intent.putExtra("fromMain", true);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, ": on service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, ": on service disconnected");
        }
    };
}
