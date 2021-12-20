package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testbaitap.R;

public class AddReminderWaterActivity extends AppCompatActivity {
    Button button;
    TextView tvChonTG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder_water);

        button = findViewById(R.id.button);
        tvChonTG = findViewById(R.id.tvChonTG);
//        createNotificationChannels();
//        tvChonTG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePicker();
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AddReminderWaterActivity.this, String.valueOf(calendar.getTimeInMillis()), Toast.LENGTH_SHORT).show();
//                Notifi notifi = new Notifi(Config.TOKEN, "Hôm nay bạn chưa thêm nước!", "Nhắc nhở");
//                simpleAPI = Constants.instance();
//                simpleAPI.pushNotifi(notifi).enqueue(new Callback<Status>() {
//                    @Override
//                    public void onResponse(Call<Status> call, Response<Status> response) {
//                        try {
//                            Status status = response.body();
//                            if(status.getStatus() == 1){
//                                Toast.makeText(AddReminderWaterActivity.this, "Tạo nhắc nhở thành công!", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(AddReminderWaterActivity.this, "Tạo nhắc nhở không thành công!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Status> call, Throwable t) {
//                        Toast.makeText(AddReminderWaterActivity.this, "Tạo nhắc nhở không thành công!", Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });
    }




}