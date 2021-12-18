package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TheTrang;
import com.example.testbaitap.reciver.NotificationReceiver;
import com.example.testbaitap.utils.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import params.com.stepprogressview.StepProgressView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaterActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private SimpleAPI simpleAPI;
    private LinearLayout op50ml, op100ml, op150ml, op200ml, op250ml, opCustom;
    private ConstraintLayout main_activity_water;
    private FloatingActionButton fabAdd, fabNotifi, fabStas;
    private Float luongNuocTam ;
    private boolean checkClick;
    private TextView tvIntook;
    private StepProgressView intakeProgress;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        LoadTheTrang(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), currentDate);

        TypedValue outValue = new TypedValue();
        WaterActivity.this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

        //tvIntook.setText(sharedPreferences.getString("lnHT", "0"));
        main_activity_water = findViewById(R.id.main_activity_water);
        op50ml = findViewById(R.id.op50ml);
        op100ml = findViewById(R.id.op100ml);
        op150ml = findViewById(R.id.op150ml);
        op200ml = findViewById(R.id.op200ml);
        op250ml = findViewById(R.id.op250ml);
        opCustom = findViewById(R.id.opCustom);

        tvIntook = findViewById(R.id.tvIntook);
        intakeProgress = findViewById(R.id.intakeProgress);

        fabAdd = findViewById(R.id.fabAdd);
        fabNotifi = findViewById(R.id.fabNotifi);
        fabStas = findViewById(R.id.fabStas);
        checkClick = false;

        op50ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op50ml.setBackgroundResource(R.drawable.option_select_bg);
                op100ml.setBackgroundResource(outValue.resourceId);
                op150ml.setBackgroundResource(outValue.resourceId);
                op200ml.setBackgroundResource(outValue.resourceId);
                op250ml.setBackgroundResource(outValue.resourceId);
                opCustom.setBackgroundResource(outValue.resourceId);
                luongNuocTam = 50F;
                checkClick = true;
            }
        });

        op100ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op50ml.setBackgroundResource(outValue.resourceId);
                op100ml.setBackgroundResource(R.drawable.option_select_bg);
                op150ml.setBackgroundResource(outValue.resourceId);
                op200ml.setBackgroundResource(outValue.resourceId);
                op250ml.setBackgroundResource(outValue.resourceId);
                opCustom.setBackgroundResource(outValue.resourceId);
                luongNuocTam = 100F;
                checkClick = true;
            }
        });

        op150ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op50ml.setBackgroundResource(outValue.resourceId);
                op100ml.setBackgroundResource(outValue.resourceId);
                op150ml.setBackgroundResource(R.drawable.option_select_bg);
                op200ml.setBackgroundResource(outValue.resourceId);
                op250ml.setBackgroundResource(outValue.resourceId);
                opCustom.setBackgroundResource(outValue.resourceId);
                luongNuocTam = 150F;
                checkClick = true;
            }
        });

        op200ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op50ml.setBackgroundResource(outValue.resourceId);
                op100ml.setBackgroundResource(outValue.resourceId);
                op150ml.setBackgroundResource(outValue.resourceId);
                op200ml.setBackgroundResource(R.drawable.option_select_bg);
                op250ml.setBackgroundResource(outValue.resourceId);
                opCustom.setBackgroundResource(outValue.resourceId);
                luongNuocTam = 200F;
                checkClick = true;
            }
        });

        opCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op50ml.setBackgroundResource(outValue.resourceId);
                op100ml.setBackgroundResource(outValue.resourceId);
                op150ml.setBackgroundResource(outValue.resourceId);
                op200ml.setBackgroundResource(outValue.resourceId);
                op250ml.setBackgroundResource(outValue.resourceId);
                opCustom.setBackgroundResource(R.drawable.option_select_bg);
                luongNuocTam=0F;
                AlertDialog.Builder builder = new AlertDialog.Builder(WaterActivity.this);
                builder.setTitle("Nhập lượng nước cần thêm");
                final EditText input = new EditText(WaterActivity.this);
                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean check = true;
                        String dm_Text = input.getText().toString();
                        if(Integer.parseInt(dm_Text)>1000){
                            Snackbar snackbar = Snackbar.make(main_activity_water, "Lượng nước thêm vào quá lớn cho 1 lần!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            check = false;
                        }
                        if(dm_Text.isEmpty()){
                            Snackbar snackbar = Snackbar.make(main_activity_water, "Bạn chưa nhập lượng nước!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            check = false;
                        }
                        if(check) {
                            luongNuocTam = Float.valueOf(dm_Text);
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                checkClick = true;
            }
        });

        op250ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op50ml.setBackgroundResource(outValue.resourceId);
                op100ml.setBackgroundResource(outValue.resourceId);
                op150ml.setBackgroundResource(outValue.resourceId);
                op200ml.setBackgroundResource(outValue.resourceId);
                op250ml.setBackgroundResource(R.drawable.option_select_bg);
                opCustom.setBackgroundResource(outValue.resourceId);
                luongNuocTam = 250F;
                checkClick = true;
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((checkClick == true)){
                    if(luongNuocTam>0){
                        UpDateLuongNuoc(currentDate,sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""),luongNuocTam);
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(main_activity_water, "Thêm nước không thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                else {
                    Snackbar snackbar = Snackbar.make(main_activity_water, "Vui lòng chọn lượng nước cần thêm!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

        fabNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendOnChannel1();
            }
        });
        this.notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    public void LoadTheTrang(String maHV, String ngay){
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVTheoNgay(maHV, ngay).enqueue(new Callback<TheTrang>() {
            @Override
            public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                TheTrang theTrang = response.body();
                try {
                    String strLN = String.format("%.0f", theTrang.getLuongNuoc());
                    tvIntook.setText(strLN);
                    if(strLN.equals("0")){
                        sendOnChannel1();
                    }
                    int pt = Integer.parseInt(strLN)*100/3500;
                    intakeProgress.setCurrentProgress(pt);
                }
                catch (Exception e){
                    tvIntook.setText("0");
                    intakeProgress.setCurrentProgress(0);
                }
            }

            @Override
            public void onFailure(Call<TheTrang> call, Throwable t) {
                tvIntook.setText("0");
                intakeProgress.setCurrentProgress(0);
                sendOnChannel2();
                //Toast.makeText(WaterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UpDateLuongNuoc(String ngay, String mahocvien, Float luongNuoc){
        simpleAPI = Constants.instance();
        simpleAPI.updateLuongNuoc(ngay, mahocvien, luongNuoc).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if(status.getStatus() == 2){
                        Snackbar snackbar = Snackbar.make(main_activity_water, "Lượng nước đã đạt mục tiêu trong ngày!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus() == 3){
                        Snackbar snackbar = Snackbar.make(main_activity_water, "Lượng nước không hợp lệ!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus() == 4){
                        Snackbar snackbar = Snackbar.make(main_activity_water, "Hôm nay bạn chưa cập nhật thể trạng!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus() == 1){
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        LoadTheTrang(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), currentDate);
                        Snackbar snackbar = Snackbar.make(main_activity_water, "Thêm nước thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus() == 0){
                        Snackbar snackbar = Snackbar.make(main_activity_water, "Thêm nước không thành công", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                catch (Exception e){
                    Snackbar snackbar = Snackbar.make(main_activity_water, "Thêm nước không thành công", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("quan", t.toString());
                //Toast.makeText(WaterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void sendOnChannel1()  {
        String title = "Nhắc nhở";
        String message = "Hôm nay bạn chưa thêm nước";

        Notification notification = new NotificationCompat.Builder(this, NotificationReceiver.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }

    private void sendOnChannel2()  {
        String title = "Nhắc nhở";
        String message = "Bạn chưa cập nhật thể trạng hôm nay!";

//        Notification notification = new NotificationCompat.Builder(this, NotificationReceiver.CHANNEL_2_ID)
//                .setSmallIcon(R.drawable.ic_bell)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_LOW)
//                .setCategory(NotificationCompat.CATEGORY_PROMO) // Promotion.
//                .build();
//
//        int notificationId = 2;
//        this.notificationManagerCompat.notify(notificationId, notification);

        Notification notification = new NotificationCompat.Builder(this, NotificationReceiver.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        int notificationId = 1;
        this.notificationManagerCompat.notify(notificationId, notification);
    }
}