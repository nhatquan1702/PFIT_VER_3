package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testbaitap.R;

public class DetailExcerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_excercise);
        TextView textView = findViewById(R.id.textView);
        Intent intent = getIntent();
        String maBaiTap = intent.getStringExtra("maBaiTap");
        textView.setText(maBaiTap);
    }
}