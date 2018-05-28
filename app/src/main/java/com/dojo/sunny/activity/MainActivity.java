package com.dojo.sunny.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dojo.sunny.R;
import com.dojo.sunny.service.MessengerService;

public class MainActivity extends Activity implements Button.OnClickListener {

    private boolean bound = false;

    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bind = findViewById(R.id.bind_service);
        Button unbind = findViewById(R.id.unbind_service);

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
                Intent bindIntent = new Intent(MainActivity.this, MessengerService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                if (bound) {
                    unbindService(connection);
                    answerQuestion(MessengerService.MSG_QUESTION);
                    bound = false;
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
            messenger = new Messenger(iBinder);
            answerQuestion(MessengerService.MSG_ANSWER_NAME);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            messenger = null;
        }
    };

    private void answerQuestion(int what) {
        Message message = Message.obtain(null, what, 0, 0);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
