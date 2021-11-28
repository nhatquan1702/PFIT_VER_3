package com.example.testbaitap.reciver;

import static android.os.Build.VERSION_CODES.O;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.testbaitap.R;


public class NotificationReceiver extends Application {
    public static final  String CHANNEL_1_ID = "channel1";
    public static final  String CHANNEL_2_ID = "channel2";
    @Override
    public void onCreate() {
        super.onCreate();
        this.createNotificationChannels();
    }
    private void createNotificationChannels()  {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("This is channel 2");


            NotificationManager manager = this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
