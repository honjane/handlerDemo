package com.honjane.handlerdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.honjane.handlerdemo.lib.Handler;
import com.honjane.handlerdemo.lib.Looper;
import com.honjane.handlerdemo.lib.Message;

import java.util.UUID;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test();
    }

    private void test() {
        Looper.prepare();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Log.i(TAG, "main thread recv message------" + message.obj.toString());
            }
        };

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                        Message msg = new Message();
                        synchronized (UUID.class) {
                            msg.obj = UUID.randomUUID().toString();
                        }
                        Log.i(TAG, "sup thread " + Thread.currentThread().getName() + ": send message------" + msg.obj);
                        handler.sendMessage(msg);
                }
            }).start();
        }
        Looper.loop();
    }
}
