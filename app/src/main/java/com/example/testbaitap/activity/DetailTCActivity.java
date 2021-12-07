package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien_KhoaTap;
import com.example.testbaitap.entity.HuanLuyenVien;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTCActivity extends AppCompatActivity {
    private TextView tvLamLai, tvCapNhat, edtTenKT, edtGiaKT, tvDoiTuong, tvTTHLVDT;
    private SimpleAPI simpleAPI;
    private String encoded = null; // encoded img bitmap to base64
    private String imgReceive = null;
    private String hlv, nv;
    private int  trangThai, giaKT;
    SharedPreferences sharedPreferences;
    private ImageView imgHinhKT;
    private CardView  btnCapNhat;

    private static final int PERMISSION_CODE =1;
    private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khoa_tap);
        edtTenKT = findViewById(R.id.edtTenKTDT);
        edtGiaKT = findViewById(R.id.edtGiaKTTheoThangDT);
        imgHinhKT = findViewById(R.id.imgKTDT);
        btnCapNhat = findViewById(R.id.btnCapNhatDT);
        tvDoiTuong = findViewById(R.id.DoiTuongDT);
        tvLamLai = findViewById(R.id.tvLamLaiDT);
        tvCapNhat = findViewById(R.id.tvCapNhatDT);
        tvTTHLVDT = findViewById(R.id.tvTTHLVDT);
        tvCapNhat.setText("Đăng ký");
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null) {
            String maKT = bundle.getString("maKT", "");
            KiemTraDangKyKT("quan", maKT);
            edtTenKT.setText(bundle.getString("tenKT", ""));
            imgReceive = bundle.getString("hinhKT", "");
            hlv = bundle.getString("hlvKT", "");
            LoadHLV(hlv);
            trangThai = bundle.getInt("ttKT", 0);
            int choDT = bundle.getInt("dtKT", 2);
            if(choDT == 1){
                tvDoiTuong.setText("Nam");
            }
            else if(choDT == 0){
                tvDoiTuong.setText("Nữ");
            }
            else {
                tvDoiTuong.setText("Cả nam và nữ");
            }
            giaKT = bundle.getInt("giaKT", 0);
            nv = bundle.getString("nvKT", "");
            edtGiaKT.setText(String.valueOf(giaKT));
            Picasso.get()
                    .load(imgReceive)
                    .placeholder(R.mipmap.logo1)
                    .error(R.mipmap.logo1)
                    .into(imgHinhKT);
        }
    }
    public void KiemTraDangKyKT(String maHocVien, String maKT){
        simpleAPI = Constants.instance();
        simpleAPI.getMaKhoaTapTheoMaHocVien(maHocVien).enqueue(new Callback<HocVien_KhoaTap>() {
            @Override
            public void onResponse(Call<HocVien_KhoaTap> call, Response<HocVien_KhoaTap> response) {
                HocVien_KhoaTap hocVien_khoaTap = response.body();
                try {
                    if(hocVien_khoaTap.getMaKhoaTap().equals(maKT)){
                        tvCapNhat.setText("Hủy đăng ký");
                        tvLamLai.setVisibility(View.VISIBLE);
                        tvLamLai.setText("Hoàn thành khóa tập");
                        //call api huy dk
                    }
                    else {
                        tvCapNhat.setText("Đăng ký");
                        tvLamLai.setVisibility(View.GONE);

                        btnCapNhat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(hocVien_khoaTap.getMaHocVien()!=null){
                                    Toast.makeText(DetailTCActivity.this, "Bạn đã than gia khóa tập trước đó!", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent intent = new Intent(DetailTCActivity.this, RegisterTCActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("maKT", hocVien_khoaTap.getMaKhoaTap());
                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivityIfNeeded(intent, 0);
                                }

                            }
                        });
                    }
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                }
            }

            @Override
            public void onFailure(Call<HocVien_KhoaTap> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }
    public void LoadHLV(String maHLV){
        simpleAPI = Constants.instance();
        simpleAPI.getHLV(maHLV).enqueue(new Callback<HuanLuyenVien>() {
            @Override
            public void onResponse(Call<HuanLuyenVien> call, Response<HuanLuyenVien> response) {
                HuanLuyenVien huanLuyenVien = response.body();
                try {
                    tvTTHLVDT.setText("Họ tên: "+ huanLuyenVien.getHoTen()+"\n\n"+
                            "Số điện thoại: "+ huanLuyenVien.getSoDienThoai()+"\n\n"+
                            "Địa chỉ: "+huanLuyenVien.getDiaChi());
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                    tvTTHLVDT.setText("Chưa có thông tin về HLV này!");
                }
            }

            @Override
            public void onFailure(Call<HuanLuyenVien> call, Throwable t) {
                Log.d("quan", t.toString());
                tvTTHLVDT.setText("Chưa có thông tin về HLV này!");
            }
        });
    }
}