package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TaiKhoan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edtTKED, edtMKED, edtXNMKED, edtHTED, edtSDTED, edtDCED;
    CheckBox checkboxHienMK;
    private static final Pattern SDT_PATTERN = Pattern.compile("(09|01|02|03|04|05|06|07|08)+([0-9]{7,11})\\b", Pattern.CASE_INSENSITIVE);

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", Pattern.CASE_INSENSITIVE);// cấu trúc 1 email thông thường

    private static final String PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
    //            ^ represents starting character of the string.
//            (?=.*[0-9]) represents a digit must occur at least once.
//            (?=.*[a-z]) represents a lower case alphabet must occur at least once.
//            (?=.*[A-Z]) represents an upper case alphabet that must occur at least once.
//            (?=.*[@#$%^&-+=()] represents a special character that must occur at least once.
//            (?=\\S+$) white spaces don’t allowed in the entire string.
//            .{8, 20} represents at least 8 characters and at most 20 characters.
//            $ represents the end of the string.
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = EMAIL_PATTERN.matcher(emailStr);
        return matcher.matches();
    }

    public static boolean validateSDT(String SDT) {
        Matcher matcher = SDT_PATTERN.matcher(SDT);
        return matcher.matches();
    }

    public static boolean validatePass(String pass) {
        return Pattern.matches(PASS_PATTERN,pass);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtTKED = findViewById(R.id.edtTKED);
        edtMKED = findViewById(R.id.edtMKED);
        edtXNMKED = findViewById(R.id.edtXNMKED);
        edtHTED = findViewById(R.id.edtHTED);
        edtSDTED = findViewById(R.id.edtSDTED);
        edtDCED = findViewById(R.id.edtDCED);
        checkboxHienMK = findViewById(R.id.checkboxHienMK);

        checkboxHienMK.setOnClickListener(v -> {
            if(checkboxHienMK.isChecked())    {
                edtMKED.setInputType(InputType.TYPE_CLASS_TEXT);
                edtXNMKED.setInputType(InputType.TYPE_CLASS_TEXT);
            } else {
                edtMKED.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                edtXNMKED.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            edtMKED.setSelection(edtMKED.getText().length());
            edtXNMKED.setSelection(edtXNMKED.getText().length());
        });


        CardView button = findViewById(R.id.cvDKTK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tk = edtTKED.getText().toString().trim();
                String mk = edtMKED.getText().toString().trim();
                String xnmk = edtXNMKED.getText().toString().trim();
                String ht = edtHTED.getText().toString().trim();
                String sdt = edtSDTED.getText().toString().trim();
                String dc = edtDCED.getText().toString().trim();
                TaiKhoan taiKhoan = new TaiKhoan(tk, xnmk, ht, sdt, dc, "", 0, 1);
                boolean isValid = true;
                if(dc.isEmpty()){
                    edtSDTED.setError("Vui lòng kiểm tra lại số điạ chỉ!");
                    edtSDTED.requestFocus();
                    isValid = false;
                }
                if(ht.isEmpty()){
                    edtSDTED.setError("Vui lòng kiểm tra lại họ tên!");
                    edtSDTED.requestFocus();
                    isValid = false;
                }
                if(tk.isEmpty()){
                    edtTKED.setError("Vui lòng kiểm tra lại tài khoản!");
                    edtTKED.requestFocus();
                    isValid = false;
                }

                if(mk.isEmpty()){
                    edtMKED.setError("Vui lòng kiểm tra lại mật khẩu!");
                    edtMKED.requestFocus();
                    isValid = false;
                }

                if(!validatePass(mk)){
                    edtMKED.setError("Mật khẩu không đúng định dạng!");
                    edtMKED.requestFocus();
                    isValid = false;
                }

                if(sdt.isEmpty()){
                    edtSDTED.setError("Vui lòng kiểm tra lại số điện thoại!");
                    edtSDTED.requestFocus();
                    isValid = false;
                }

                if(!validateSDT(sdt)){
                    edtSDTED.setError("Số điện thoại không đúng định dạng!");
                    edtSDTED.requestFocus();
                    isValid = false;
                }

//                if(!validateEmail(tk)){
//                    edtTKED.setError("Tài khoản không hợp lệ!");
//                    edtTKED.requestFocus();
//                    isValid = false;
//                }

                if(xnmk.isEmpty()){
                    edtXNMKED.setError("Vui lòng kiểm tra lại mật khẩu!");
                    edtXNMKED.requestFocus();
                    isValid = false;
                }
                if(!mk.equals(xnmk) || !xnmk.equals(mk)){
                    edtXNMKED.setError("Mật khẩu chưa trùng khớp!");
                    isValid = false;
                }
                if(isValid){
                    ThemTaiKhoan(taiKhoan);
                }

            }
        });


        TextView tvDaCoTK = (TextView) findViewById(R.id.tvDaCoTK);
        tvDaCoTK.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    public void ThemTaiKhoan(TaiKhoan taiKhoan){
        SimpleAPI simpleAPI = Constants.instance();
        simpleAPI.insertTaiKhoan(taiKhoan).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                try {
                    Status status = response.body();
                    if(status.getStatus()==1){
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    if(status.getStatus()==2){
                        Toast.makeText(RegisterActivity.this, "Tài khoản này đã tồn tại!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Tạo tài khoản không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}