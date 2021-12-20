package com.example.testbaitap.fragment;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.activity.AccountActivity;
import com.example.testbaitap.activity.ListTCActivity;
import com.example.testbaitap.activity.MainActivity;
import com.example.testbaitap.activity.WaterActivity;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.TheTrang;
import com.example.testbaitap.reciver.NotificationReceiver;
import com.example.testbaitap.reciver.ReminderAddStatusBroadcast;
import com.example.testbaitap.reciver.ReminderAddWaterBroadcast;
import com.example.testbaitap.utils.Config;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Reminder extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreTime;
    SharedPreferences.Editor editorTime;
    TheTrang theTrang;
    SimpleAPI simpleAPI;
    ImageView butoon_plus, butoon_plus1, set_water, set_medicion;
    Calendar calendar;
    Calendar calendar2;
    AlarmManager alarmManager;
    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;
    AlarmManager alarmManager2;
    private int lastSelectedHour2 = -1;
    private int lastSelectedMinute2 = -1;
    private NotificationManagerCompat notificationManagerCompat;
    public Fragment_Reminder() {
        // Required empty public constructor
    }

    public static Fragment_Reminder newInstance(String param1, String param2) {
        Fragment_Reminder fragment = new Fragment_Reminder();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__reminder, container, false);

//        lastSelectedHour2 = sharedPreTime.getInt("hReminderS", -1);
//        lastSelectedMinute2 = sharedPreTime.getInt("mReminderS", -1);

        sharedPreferences = requireContext().getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sharedPreTime = requireContext().getSharedPreferences(Config.DATA_REMINDER, Context.MODE_PRIVATE);
        editorTime = sharedPreTime.edit();

        butoon_plus = view.findViewById(R.id.butoon_plus);
        butoon_plus1 = view.findViewById(R.id.butoon_plus1);
        set_water = view.findViewById(R.id.set_water);
        set_medicion = view.findViewById(R.id.set_medicion);

        theTrang = new TheTrang();
        simpleAPI = Constants.instance();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        simpleAPI.getTheTrangHVTheoNgay(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), currentDate).enqueue(new Callback<TheTrang>() {
            @Override
            public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                try {
                    theTrang = response.body();
                    Toast.makeText(requireContext(), theTrang.getMaHocVien(), Toast.LENGTH_SHORT).show();

                    if(theTrang.getBmi()>0){
                        //ẩn
                        butoon_plus1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(requireContext(), "Bạn đã thêm thể trạng rồi!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        set_medicion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(requireContext(), "Bạn đã thêm thể trạng rồi!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        //show
                        butoon_plus1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                diaLogBottomStatus().show();
                            }
                        });
                        set_medicion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(requireContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                    if(theTrang.getLuongNuoc()==3500){
                        //ẩn
                        butoon_plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(requireContext(), "Bạn đã thêm nước đủ rồi!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        set_water.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(requireContext(), "Bạn đã thêm nước đủ rồi!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        //show
                        butoon_plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                diaLogBottomWater().show();
                            }
                        });
                        set_water.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(requireContext(), WaterActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TheTrang> call, Throwable t) {
                Log.d("quan", t.toString());
                butoon_plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diaLogBottomWater().show();
                    }
                });
                butoon_plus1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        diaLogBottomStatus().show();
                    }
                });
            }
        });

        return view;
    }

//    public void showTimePicker() {
//        if(this.lastSelectedHour == -1)  {
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            this.lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
//            this.lastSelectedMinute = c.get(Calendar.MINUTE);
//        }
//        final boolean is24HView = true;
//        final boolean isSpinnerMode = false;
//
//        // Time Set Listener.
//        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                lastSelectedHour = hourOfDay;
//                lastSelectedMinute = minute;
//                calendar = Calendar.getInstance();
//                calendar.set(Calendar.HOUR_OF_DAY, lastSelectedHour);
//                calendar.set(Calendar.MINUTE, lastSelectedMinute);
//                calendar.set(Calendar.SECOND, 0);
//                calendar.set(Calendar.MILLISECOND, 0);
//                Intent intent = new Intent(requireContext(), ReminderAddWaterBroadcast.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0 , intent, 0);
//                alarmManager = (AlarmManager) requireContext().getSystemService(ALARM_SERVICE);
//                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);
//                Toast.makeText(requireContext(), "Tạo nhắc nhở thành công!", Toast.LENGTH_SHORT).show();
//
//            }
//        };
//
//        // Create TimePickerDialog:
//        TimePickerDialog timePickerDialog = null;
//
//        // TimePicker in Spinner Mode:
//        if(isSpinnerMode)  {
//            timePickerDialog = new TimePickerDialog(requireContext(),
//                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
//                    timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
//        }
//        // TimePicker in Clock Mode (Default):
//        else  {
//            timePickerDialog = new TimePickerDialog(requireContext(),
//                    timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
//        }
//
//        // Show
//        timePickerDialog.show();
//
//    }

//    private void createOnChannel()  {
//        String title = "aaaaaa";
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            NotificationChannel channel4 = new NotificationChannel(
//                    "chanel4",
//                    "Channel 4",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            channel4.setDescription("This is channel 4");
//
//            NotificationChannel channel3 = new NotificationChannel(
//                    "chanel3",
//                    "Channel 3",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            channel3.setDescription("This is channel 3");
//
//
//            NotificationManager manager = requireContext().getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel3);
//            manager.createNotificationChannel(channel4);
//        }
//    }

    public BottomSheetDialog diaLogBottomWater() {
        EditText editTextTime;
        AppCompatButton buttonTime;
        CheckBox checkBox_Repeat;
        Button btnUp;

        BottomSheetDialog sheetDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.add_water_reminder, null);
        sheetDialog.setContentView(viewDialog);
        editTextTime = (EditText) viewDialog.findViewById(R.id.editText_time);
        buttonTime = (AppCompatButton) viewDialog.findViewById(R.id.button_time);
        checkBox_Repeat = (CheckBox) viewDialog.findViewById(R.id.checkBox_Repeat);

        btnUp = viewDialog.findViewById(R.id.btnUpdate);
        btnUp.setText("Lời nhắc thêm nước");

        lastSelectedHour = sharedPreTime.getInt("hReminderW", -1);
        lastSelectedMinute = sharedPreTime.getInt("mReminderW", -1);
        int check = sharedPreTime.getInt("cReminderW", -1);
        if(lastSelectedHour != -1){
            editTextTime.setText(lastSelectedHour + ":" + lastSelectedMinute );
            if(check == 1){
                checkBox_Repeat.setChecked(true);
            }
            if(check == 0) {
                checkBox_Repeat.setChecked(false);
            }
        }

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(lastSelectedHour == -1)  {
                        // Get Current Time
                        final Calendar c = Calendar.getInstance();
                        lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
                        lastSelectedMinute = c.get(Calendar.MINUTE);
                    }
                    final boolean is24HView = true;
                    final boolean isSpinnerMode = false;
                    //final boolean isRepeat = checkBox_Repeat.isChecked();

                    // Time Set Listener.
                    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            editTextTime.setText(hourOfDay + ":" + minute );
                            lastSelectedHour = hourOfDay;
                            lastSelectedMinute = minute;

                            calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, lastSelectedHour);
                            calendar.set(Calendar.MINUTE, lastSelectedMinute);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            Intent intent = new Intent(requireContext(), ReminderAddWaterBroadcast.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(requireContext(), 0 , intent, 0);
                            viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(alarmManager != null){
                                        alarmManager.cancel(pendingIntent);
                                    }
                                    alarmManager = (AlarmManager) requireContext().getSystemService(ALARM_SERVICE);
                                    if(!checkBox_Repeat.isChecked())  {
                                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);
                                        editorTime.putInt("cReminderW", 0);
                                        Toast.makeText(requireContext(), "Tạo nhắc nhở thêm nước thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                    // TimePicker in Clock Mode (Default):
                                    else  {
                                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() ,AlarmManager.INTERVAL_DAY,  pendingIntent);
                                        editorTime.putInt("cReminderW", 1);
                                        Toast.makeText(requireContext(), "Tạo nhắc nhở thêm nước thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                    editorTime.putInt("hReminderW", lastSelectedHour);
                                    editorTime.putInt("mReminderW", lastSelectedMinute);
                                    editorTime.commit();
                                    sheetDialog.dismiss();
                                }
                            });

                        }
                    };

                    // Create TimePickerDialog:
                    TimePickerDialog timePickerDialog = null;

                    // TimePicker in Spinner Mode:
                    if(isSpinnerMode)  {
                        timePickerDialog = new TimePickerDialog(requireContext(),
                                android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                                timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
                    }
                    // TimePicker in Clock Mode (Default):
                    else  {
                        timePickerDialog = new TimePickerDialog(requireContext(),
                                timeSetListener, lastSelectedHour, lastSelectedMinute, is24HView);
                    }

                    timePickerDialog.show();
                }
        });
//        if(this.lastSelectedHour2 == -1)  {
//            // Get Current Time
//            final Calendar c = Calendar.getInstance();
//            this.lastSelectedHour2 = c.get(Calendar.HOUR_OF_DAY);
//            this.lastSelectedMinute2 = c.get(Calendar.MINUTE);
//        }
//        final boolean is24HView = true;
//        final boolean isSpinnerMode = false;
//
//        // Time Set Listener.
//        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
//
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                lastSelectedHour2 = hourOfDay;
//                lastSelectedMinute2 = minute;
//                calendar2 = Calendar.getInstance();
//                calendar2.set(Calendar.HOUR_OF_DAY, lastSelectedHour2);
//                calendar2.set(Calendar.MINUTE, lastSelectedMinute2);
//                calendar2.set(Calendar.SECOND, 0);
//                calendar2.set(Calendar.MILLISECOND, 0);
//                Intent intent2 = new Intent(requireContext(), ReminderAddStatusBroadcast.class);
//                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(requireContext(), 0 , intent2, 0);
//                alarmManager2 = (AlarmManager) requireContext().getSystemService(ALARM_SERVICE);
//                //alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 200 , pendingIntent2);
//                alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis() ,500*60,  pendingIntent2);
//
//                Toast.makeText(requireContext(), "Tạo nhắc nhở thành công 2!", Toast.LENGTH_SHORT).show();
//
//            }
//        };
//
//        // Create TimePickerDialog:
//        TimePickerDialog timePickerDialog = null;
//
//        // TimePicker in Spinner Mode:
//        if(isSpinnerMode)  {
//            timePickerDialog = new TimePickerDialog(requireContext(),
//                    android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
//                    timeSetListener, lastSelectedHour2, lastSelectedMinute2, is24HView);
//        }
//        // TimePicker in Clock Mode (Default):
//        else  {
//            timePickerDialog = new TimePickerDialog(requireContext(),
//                    timeSetListener, lastSelectedHour2, lastSelectedMinute2, is24HView);
//        }
//
//        // Show
//        timePickerDialog.show();
        return sheetDialog;
    }

    public BottomSheetDialog diaLogBottomStatus() {
        EditText editTextTime;
        AppCompatButton buttonTime;
        CheckBox checkBox_Repeat;
        Button btnUp;

        BottomSheetDialog sheetDialog = new BottomSheetDialog(requireContext(), R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.add_water_reminder, null);
        sheetDialog.setContentView(viewDialog);
        editTextTime = (EditText) viewDialog.findViewById(R.id.editText_time);
        buttonTime = (AppCompatButton) viewDialog.findViewById(R.id.button_time);
        checkBox_Repeat = (CheckBox) viewDialog.findViewById(R.id.checkBox_Repeat);

        btnUp = viewDialog.findViewById(R.id.btnUpdate);
        btnUp.setText("Lời nhắc thêm thể trạng");

        lastSelectedHour2 = sharedPreTime.getInt("hReminderS", -1);
        lastSelectedMinute2 = sharedPreTime.getInt("mReminderS", -1);
        int check = sharedPreTime.getInt("cReminderS", -1);
        if(lastSelectedHour2 != -1){
            editTextTime.setText(lastSelectedHour2 + ":" + lastSelectedMinute2 );
            if(check == 1){
                checkBox_Repeat.setChecked(true);
            }
            if(check == 0) {
                checkBox_Repeat.setChecked(false);
            }
        }

        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastSelectedHour2 == -1) {
                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    lastSelectedHour2 = c.get(Calendar.HOUR_OF_DAY);
                    lastSelectedMinute2 = c.get(Calendar.MINUTE);
                }
                final boolean is24HView = true;
                final boolean isSpinnerMode = false;
                //final boolean isRepeat = checkBox_Repeat.isChecked();

                // Time Set Listener.
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        editTextTime.setText(hourOfDay + ":" + minute);
                        lastSelectedHour2 = hourOfDay;
                        lastSelectedMinute2 = minute;

                        calendar2 = Calendar.getInstance();
                        calendar2.set(Calendar.HOUR_OF_DAY, lastSelectedHour2);
                        calendar2.set(Calendar.MINUTE, lastSelectedMinute2);
                        calendar2.set(Calendar.SECOND, 0);
                        calendar2.set(Calendar.MILLISECOND, 0);
                        Intent intent2 = new Intent(requireContext(), ReminderAddStatusBroadcast.class);
                        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(requireContext(), 0, intent2, 0);
                        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (alarmManager2 != null) {
                                    alarmManager2.cancel(pendingIntent2);
                                }
                                alarmManager2 = (AlarmManager) requireContext().getSystemService(ALARM_SERVICE);
                                if (!checkBox_Repeat.isChecked()) {
                                    alarmManager2.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent2);
                                    editorTime.putInt("cReminderS", 0);
                                    Toast.makeText(requireContext(), "Tạo nhắc nhở thể trạng thành công!", Toast.LENGTH_SHORT).show();
                                }
                                // TimePicker in Clock Mode (Default):
                                else {
                                    alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent2);
                                    editorTime.putInt("cReminderS", 1);
                                    Toast.makeText(requireContext(), "Tạo nhắc nhở thể trạng thành công!", Toast.LENGTH_SHORT).show();
                                }
                                editorTime.putInt("hReminderS", lastSelectedHour2);
                                editorTime.putInt("mReminderS", lastSelectedMinute2);
                                editorTime.commit();
                                sheetDialog.dismiss();
                            }
                        });

                    }
                };

                // Create TimePickerDialog:
                TimePickerDialog timePickerDialog = null;

                // TimePicker in Spinner Mode:
                timePickerDialog = new TimePickerDialog(requireContext(),
                        timeSetListener, lastSelectedHour2, lastSelectedMinute2, is24HView);

                timePickerDialog.show();
            }
        });
        return sheetDialog;
    }
}