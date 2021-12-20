package com.example.testbaitap.reciver;

import static com.example.testbaitap.reciver.NotificationReceiver.CHANNEL_1_ID;
import static com.example.testbaitap.reciver.NotificationReceiver.showNotifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.testbaitap.R;

public class ReminderAddStatusBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        showNotifi(context, CHANNEL_1_ID, "Hôm nay bạn chưa cập nhật thể trạng!");
    }
}
