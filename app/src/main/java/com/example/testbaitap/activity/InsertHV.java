package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.Status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertHV extends AppCompatActivity {
    private EditText edtTKED, edtMKED;
    private String maHV;
    private String maKT;
    RadioButton rNam, rNu, rOther;
    CardView cvDKTK;
    private SimpleAPI simpleAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_hv);

        edtTKED = findViewById(R.id.edtTKED);
        edtTKED.setEnabled(false);
        edtMKED = findViewById(R.id.edtMKED);
        rNam = findViewById(R.id.rNam);
        rNu = findViewById(R.id.rNu);
        rOther = findViewById(R.id.rOther);
        cvDKTK = findViewById(R.id.cvDKTK);

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null) {
            maHV = bundle.getString("mahocvien", "");
            maKT = bundle.getString("makhoatap", "");
        }
        edtTKED.setText(maHV);
        DangKy(maHV, maKT);
    }
    private void DangKy(String maHV, String maKT){
        cvDKTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true;
                String role;

                simpleAPI = Constants.instance();
                if(rNam.isChecked()){
                    role = "1";
                }
                else if(rNu.isChecked()){
                    role = "0";
                }
                else {
                    role="2";
                }

                if(edtMKED.getText().toString().trim().isEmpty() || Integer.parseInt(edtMKED.getText().toString().trim()) < 0 || Integer.parseInt(edtMKED.getText().toString().trim()) > 100){
                    edtMKED.setError("Vui lòng kiểm tra lại tuổi!");
                    edtMKED.requestFocus();
                    isValid = false;
                }

                if (isValid) {
                    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    HocVien hocVien = new HocVien(maHV, Integer.parseInt(edtMKED.getText().toString().trim()), Integer.parseInt(role), currentDate, 0, -1, maKT);
                    simpleAPI.insertHocVien(hocVien).enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            try {
                                Status status = response.body();
                                    if(status.getStatus()==0) {
                                        Toast.makeText(InsertHV.this, "Đã có lỗi xảy ra, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                                    }
                                    if(status.getStatus()==2) {
                                        Toast.makeText(InsertHV.this, "Học viên đã đăng ký!", Toast.LENGTH_SHORT).show();
                                    }
                                    if(status.getStatus()==1) {
                                        Intent intent;
                                        Toast.makeText(InsertHV.this, "Đăng ký học viên thành công!", Toast.LENGTH_SHORT).show();
                                        intent = new Intent(InsertHV.this, DetailTCActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivityIfNeeded(intent, 0);
                                        finish();
                                    }
                            }
                            catch (Exception e){
                                Log.d("quan1", e.toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            Log.d("quan", t.toString());
                        }
                    });
                };
            }});
    }
}