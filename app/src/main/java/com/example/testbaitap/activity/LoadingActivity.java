package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testbaitap.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;

public class LoadingActivity extends AppCompatActivity {
    TextView tvPFIT, tvTT;
    Animation txtAnimation,txtAnimation2;
    ProgressBar progressBarLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        progressBarLoad = findViewById(R.id.spinkit);
        txtAnimation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tranlation_x);
        txtAnimation2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.tranx);
        tvPFIT=findViewById(R.id.textView3);
        tvTT=findViewById(R.id.textView2);
        tvTT.setVisibility(View.INVISIBLE);
        tvPFIT.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvPFIT.setVisibility(View.VISIBLE);
                        tvTT.setVisibility(View.VISIBLE);
                        tvPFIT.setAnimation(txtAnimation);
                        tvTT.setAnimation(txtAnimation2);
                    }
                },900);
            }
        },100);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                startActivity(intent);
                onBackPressed();
            }
        },6000);
    }
}