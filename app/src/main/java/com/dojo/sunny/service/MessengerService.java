package com.dojo.sunny.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;


public class MessengerService extends Service {

    public static final int MSG_ANSWER_NAME = 1;
    public static final int MSG_QUESTION = 2;

    Messenger messenger = new Messenger(new IncomingHandler());

    private class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Message newMsg = Message.obtain();
            Bundle bundle = new Bundle();
            switch (msg.what) {
                case MSG_ANSWER_NAME:
                    Toast.makeText(MessengerService.this, "service: " + answerName(), Toast.LENGTH_SHORT).show();
                    newMsg.what = MSG_ANSWER_NAME;
                    bundle.putString("result", answerName());
                    newMsg.setData(bundle);
                    try {
                        msg.replyTo.send(newMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case MSG_QUESTION:
                    Toast.makeText(MessengerService.this, "service: " + question(), Toast.LENGTH_SHORT).show();
                    newMsg.what = MSG_QUESTION;
                    bundle.putString("result", question());
                    newMsg.setData(bundle);
                    try {
                        msg.replyTo.send(newMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    public String answerName() {
        return "Yaxin";
    }

    public String question() {
        return "What's your name?";
    }
}
