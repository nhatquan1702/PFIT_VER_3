package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TaiKhoan;
import com.example.testbaitap.utils.Config;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private SimpleAPI simpleAPI;

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
        imgButtonQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });
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
                    SharedPreferences sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    simpleAPI =Constants.instance();
                    simpleAPI.dangNhap(email, pass).enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            Status status = response.body();
                            try {
                                if(status.getStatus()==1){
                                    //Toast.makeText(LoginActivity.this, String.valueOf(status.getStatus()), Toast.LENGTH_SHORT).show();
                                    simpleAPI = Constants.instance();
                                    simpleAPI.getThongTinTaiKhoan(email).enqueue(new Callback<TaiKhoan>() {
                                        @Override
                                        public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                                            TaiKhoan taiKhoan = response.body();
                                            try {
//                                               Intent intent;
//                                               editor.putString(Config.DATA_LOGIN_USERNAME, taiKhoan.getTaiKhoan().trim());
//                                               editor.putString(Config.DATA_LOGIN_PASS, taiKhoan.getMatKhau().trim());
//                                               editor.putString(Config.DATA_LOGIN_ROLE, String.valueOf(taiKhoan.getQuyen()));
//                                               editor.commit();
//                                               Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                                               intent = new Intent(LoginActivity.this, MainActivity.class);
//                                               startActivity(intent);
                                               if(taiKhoan.getQuyen()==3){
                                                   Intent intent;
                                                   editor.putString(Config.DATA_LOGIN_USERNAME, taiKhoan.getTaiKhoan().trim());
                                                   editor.putString(Config.DATA_LOGIN_PASS, taiKhoan.getMatKhau().trim());
                                                   editor.putString(Config.DATA_LOGIN_ROLE, String.valueOf(taiKhoan.getQuyen()));
                                                   editor.commit();
                                                   Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                   intent = new Intent(LoginActivity.this, ManageTCActivity.class);
                                                   startActivity(intent);
                                                   finish();
                                               }
                                                if(taiKhoan.getQuyen()==2){
                                                    Intent intent;
                                                    editor.putString(Config.DATA_LOGIN_USERNAME, taiKhoan.getTaiKhoan().trim());
                                                    editor.putString(Config.DATA_LOGIN_PASS, taiKhoan.getMatKhau().trim());
                                                    editor.putString(Config.DATA_LOGIN_ROLE, String.valueOf(taiKhoan.getQuyen()));
                                                    editor.commit();
                                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                    intent = new Intent(LoginActivity.this, SalesActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                if(taiKhoan.getQuyen()==1){
                                                    Intent intent;
                                                    editor.putString(Config.DATA_LOGIN_USERNAME, taiKhoan.getTaiKhoan().trim());
                                                    editor.putString(Config.DATA_LOGIN_PASS, taiKhoan.getMatKhau().trim());
                                                    editor.putString(Config.DATA_LOGIN_ROLE, String.valueOf(taiKhoan.getQuyen()));
                                                    editor.commit();
                                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                                    intent = new Intent(LoginActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                            }
                                           catch (Exception e){
                                               Log.d("quan", e.toString());
                                           }
                                        }

                                        @Override
                                        public void onFailure(Call<TaiKhoan> call, Throwable t) {
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception e){
                                Log.d("quan", e.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            Log.d("quan", t.toString());
                        }
                    });
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