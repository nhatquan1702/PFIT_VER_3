package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TaiKhoan;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcceptUserRegister extends AppCompatActivity {
    private Status status;
    private String maHV;
    private SimpleAPI simpleAPI;
    private TaiKhoan taiKhoan;
    private HocVien hocVien;
    private TextView tvHoTenHD, tvKhoaTapHD, tvTongTTHD, tvHinhThucTTHD, tvDiaChi, tvKDuyet;
    private CardView btnCapNhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_user_register);

        tvHoTenHD = findViewById(R.id.tvHoTenHD);
        tvKhoaTapHD = findViewById(R.id.tvKhoaTapHD);
        tvTongTTHD = findViewById(R.id.tvTongTTHD);
        tvHinhThucTTHD = findViewById(R.id.tvHinhThucTTHD);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvKDuyet = findViewById(R.id.tvKDuyet);
        btnCapNhat = findViewById(R.id.btnCapNhat);

        status = new Status();

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null) {
            maHV = bundle.getString("mahocvien", "");
        }
        LoadTTHocVien(maHV);

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DuyetHocVien(maHV, 1);
            }
        });

        tvKDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DuyetHocVien(maHV, -1);
            }
        });
    }

    public void DuyetHocVien(String maHV, int tt){
        simpleAPI = Constants.instance();
        simpleAPI.updateTrangThaiHocVien(maHV, tt).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                try {
                    status = response.body();
                    if(status.getStatus()==1){
                        Toast.makeText(AcceptUserRegister.this, "Duyệt thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AcceptUserRegister.this, ListAccountRegisterTC.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(intent, 0);
                    }
                    else {
                        Toast.makeText(AcceptUserRegister.this, "Duyệt không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }

    public void LoadTTHocVien(String maHV){
        simpleAPI = Constants.instance();
        simpleAPI.getThongTinTaiKhoan(maHV).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                try {
                    taiKhoan = response.body();
                    tvHoTenHD.setText(taiKhoan.getHoTen());
                    tvDiaChi.setText(taiKhoan.getDiaChi());
                    tvHinhThucTTHD.setText(taiKhoan.getSoDienThoai());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });

        simpleAPI = Constants.instance();
        simpleAPI.getKhachHang(maHV).enqueue(new Callback<HocVien>() {
            @Override
            public void onResponse(Call<HocVien> call, Response<HocVien> response) {
                try {
                    hocVien = response.body();
                    tvTongTTHD.setText(String.valueOf(hocVien.getTuoi()));
                    if(hocVien.getGioiTinh()==1){
                        tvKhoaTapHD.setText("Nam");
                    }
                    if(hocVien.getGioiTinh()==0){
                        tvKhoaTapHD.setText("Nữ");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HocVien> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }
}