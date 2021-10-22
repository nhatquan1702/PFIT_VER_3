package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;

public class AccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Toast.makeText(AccountActivity.this, sharedPreferences.getString("email", "username"), Toast.LENGTH_SHORT).show();

        CardView button = findViewById(R.id.cvDKTK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.remove("email");
                editor.remove("password");
                editor.remove("role");

                editor.commit();
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}