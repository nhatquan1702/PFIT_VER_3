package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RelativeLayout rLayoutDN = findViewById(R.id.rLayoutDN);
        RelativeLayout rLayoutAdd = findViewById(R.id.rLayoutAdd);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.uptodowndiagonal);
        rLayoutDN.setAnimation(animation);
        Animation animationx = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tranlation_x);
        Animation animationy = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tranlation_y);
        ImageButton imgButtonQR = findViewById(R.id.imgButtonQR);
        imgButtonQR.setAnimation(animationy);
        EditText edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtEmailLogin.setAnimation(animationx);
        TextView tvEmailDN = findViewById(R.id.tvEmailDN);
        tvEmailDN.setAnimation(animationx);
        EditText edtPassLogin = findViewById(R.id.edtPassLogin);
        edtPassLogin.setAnimation(animationx);
        TextView tvPassDN = findViewById(R.id.tvPassDN);
        tvPassDN.setAnimation(animationx);
        CheckBox checkBoxShowPass = findViewById(R.id.checkShowPass);
        checkBoxShowPass.setAnimation(animationx);
        checkBoxShowPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxShowPass.isChecked())    {
                    edtPassLogin.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    edtPassLogin.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                edtPassLogin.setSelection(edtPassLogin.getText().length());
            }
        });
        TextView tvQuenMK = findViewById(R.id.tvQuenMK);
        tvQuenMK.setAnimation(animationx);
        rLayoutAdd.setAnimation(animationy);
        CardView cvDNLogin = findViewById(R.id.cvDNLogin);
        cvDNLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailLogin.getText().toString().trim();
                String pass = edtPassLogin.getText().toString().trim();
                boolean isValid = true;
                if(email.isEmpty()){
                    edtEmailLogin.setError("Vui lòng kiểm tra lại tài khoản!");
                    edtEmailLogin.requestFocus();
                    isValid = false;
                }
                if(pass.isEmpty()){
                    edtPassLogin.setError("Vui lòng kiểm tra lại mật khẩu!");
                    edtPassLogin.requestFocus();
                    isValid = false;
                }

                if(isValid){
                    SharedPreferences sharedPreferences = getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                                Intent intent;
                                editor.putString("email", email);
                                editor.putString("pass", pass);
                                if(email.equals("quan"))
                                {
                                    editor.putString("role", String.valueOf(1));
                                }
                                else {
                                    editor.putString("role", String.valueOf(0));
                                }
                                editor.commit();
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                }
            }

        });

        ImageView imgDKTK = findViewById(R.id.imgbtnDKTK);
        imgDKTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}