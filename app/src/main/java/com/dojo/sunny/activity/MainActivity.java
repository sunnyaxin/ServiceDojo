package com.dojo.sunny.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.dojo.sunny.R;
import com.dojo.sunny.service.LocalService;

public class MainActivity extends Activity implements Button.OnClickListener {

    private boolean bound = false;

    private TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bind = findViewById(R.id.bind_service);
        Button unbind = findViewById(R.id.unbind_service);
        question = findViewById(R.id.question);

        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bind_service:
                Intent bindIntent = new Intent(MainActivity.this, LocalService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this, "bind service", Toast.LENGTH_SHORT).show();
                break;
            case R.id.unbind_service:
                if (bound) {
                    unbindService(connection);
                    question.setText("what's your name?");
                    Toast.makeText(MainActivity.this, "unbind service", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "service not bind", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            bound = true;
            LocalService.LocalBinder binder = (LocalService.LocalBinder) iBinder;
            LocalService localService = binder.getService();
            Toast.makeText(MainActivity.this, "connect service", Toast.LENGTH_SHORT).show();
            question.setText(localService.answerName());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            Toast.makeText(MainActivity.this, "disconnect service", Toast.LENGTH_SHORT).show();
        }
    };
}
