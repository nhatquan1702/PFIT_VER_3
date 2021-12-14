package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.TrainingCourseAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.HocVien_KhoaTap;
import com.example.testbaitap.entity.HuanLuyenVien;
import com.example.testbaitap.entity.Status;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.Random;

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
    private ConstraintLayout clEditKTDT;

    private static final int PERMISSION_CODE =1;
    private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khoa_tap);

        clEditKTDT = findViewById(R.id.clEditKTDT);
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
        simpleAPI.getKhachHang(maHocVien).enqueue(new Callback<HocVien>() {
            @Override
            public void onResponse(Call<HocVien> call, Response<HocVien> response) {
                try {
                    HocVien hocVien = response.body();
                    // trang thai = 0 user dang tham gia khoa tap,
                    // = 2 hoan thanh khoa tap
                    // = 3 khong tham gia khoa tap nao
                    if(hocVien.getMaKhoaTap().equals(maKT)){ //khóa tập đang tham gia
                        if(hocVien.getTrangThai()==0 || hocVien.getTrangThai()==1){ // tk đang tham gia
                            tvCapNhat.setText("Hủy đăng ký");
                            tvLamLai.setVisibility(View.GONE);
                            //call api huy dk
                            tvCapNhat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailTCActivity.this);
                                    builder.setTitle("Xác nhận");
                                    builder.setMessage("Bạn có thực sự muốn hủy đăng ký khóa tập này?");
                                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            UpdateTrangThaiHV(hocVien.getMaHocVien(), 3);
                                            Snackbar snackbar = Snackbar.make(clEditKTDT, "Hủy đăng ký khóa tập thành công!", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    });
                                    builder.setNegativeButton("Hủy thao tác", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            Snackbar snackbar = Snackbar.make(clEditKTDT, "Đã hủy thao tác", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    });
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            });
                        }
                        if(hocVien.getTrangThai()==2) { //hoàn thành khóa tập này
                            tvCapNhat.setVisibility(View.GONE);
                            tvLamLai.setVisibility(View.VISIBLE);
                            tvLamLai.setText("Bạn đã hoàn thành khóa tập này");
                            btnCapNhat.setVisibility(View.GONE);
                        }
                        if(hocVien.getTrangThai()==3) { //hoàn thành khóa tập này
                            tvCapNhat.setVisibility(View.VISIBLE);
                            tvCapNhat.setText("Đăng ký lại");
                            tvLamLai.setVisibility(View.VISIBLE);
                            tvLamLai.setText("Bạn đã hủy khóa tập này trước đó!");
                            btnCapNhat.setVisibility(View.VISIBLE);
                            btnCapNhat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(hocVien.getMaHocVien().equals("quan")||hocVien.getMaHocVien().equals("huy")){
                                        UpdateTrangThaiHV(hocVien.getMaHocVien(), 0);
                                    }
                                    else {
                                        UpdateTrangThaiHV(hocVien.getMaHocVien(), 1);
                                    }
                                }
                            });
                        }
                        if(hocVien.getTrangThai()==4){ // chờ thanh toán, thanh toán xong trạng thái về 0
                            tvCapNhat.setVisibility(View.VISIBLE);
                            tvCapNhat.setText("Thanh toán");
                            tvLamLai.setVisibility(View.VISIBLE);
                            tvLamLai.setText("Bạn cần thanh toán!");
                            btnCapNhat.setVisibility(View.VISIBLE);
                            btnCapNhat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(DetailTCActivity.this, PayMentsActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivityIfNeeded(intent, 0);
                                }
                            });
                        }
                    }
                    else { //khóa tập chưa tham gia
                        if(hocVien.getTrangThai()==3){
                            tvCapNhat.setText("Đăng ký");
                            tvLamLai.setVisibility(View.GONE);
                            btnCapNhat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //đăng ký khóa tập này
                                    try {
                                        UpdateTrangThaiHV(hocVien.getMaHocVien(), 1);
                                        UpdateKhoaTapCHoHocVien(hocVien.getMaHocVien(), maKT);
                                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Đăng ký khóa tập thành công!", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                    catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                        else {
                            tvCapNhat.setText("Đăng ký");
                            tvLamLai.setVisibility(View.GONE);
                            btnCapNhat.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar snackbar = Snackbar.make(clEditKTDT, "Bạn đã tham gia khóa tập trước đó!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            });
                        }
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

//        simpleAPI = Constants.instance();
//        simpleAPI.getMaKhoaTapTheoMaHocVien(maHocVien).enqueue(new Callback<HocVien_KhoaTap>() {
//            @Override
//            public void onResponse(Call<HocVien_KhoaTap> call, Response<HocVien_KhoaTap> response) {
//                HocVien_KhoaTap hocVien_khoaTap = response.body();
//                try {
//                    if(hocVien_khoaTap.getMaKhoaTap().equals(maKT)){
//
//                    }
//                    else {
//                        tvCapNhat.setText("Đăng ký");
//                        tvLamLai.setVisibility(View.GONE);
//
//                        btnCapNhat.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if(hocVien_khoaTap.getMaHocVien()!=null){
//                                    Snackbar snackbar = Snackbar.make(clEditKTDT, "Bạn đã tham gia khóa tập trước đó", Snackbar.LENGTH_LONG);
//                                    snackbar.show();                                }
//                                else {
//                                      UpdateKhoaTapCHoHocVien(maHocVien, maKT);
////                                    Random generator = new Random();
////                                    System.out.println("Random Integer: " + generator.nextInt());
////                                    Intent intent = new Intent(DetailTCActivity.this, RegisterTCActivity.class);
////                                    Bundle bundle = new Bundle();
////                                    bundle.putString("maKT", hocVien_khoaTap.getMaKhoaTap());
////                                    intent.putExtras(bundle);
////                                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
////                                    startActivityIfNeeded(intent, 0);
//                                }
//
//                            }
//                        });
//                    }
//                }
//                catch (Exception e){
//                    Log.d("quan", e.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<HocVien_KhoaTap> call, Throwable t) {
//                Log.d("quan", t.toString());
//            }
//        });
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

    public void UpdateTrangThaiHV(String maHV, int tt){
        simpleAPI = Constants.instance();
        simpleAPI.updateTrangThaiHocVien(maHV, tt).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if(status.getStatus() == 2){
                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Không tồn tại học viên này!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus() == 1){
                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Cập nhật thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Cập nhật không thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
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

    public void UpdateKhoaTapCHoHocVien(String maHV, String maKT){
        simpleAPI = Constants.instance();
        simpleAPI.updateKhoaTapChoHocVien(maHV, maKT).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if(status.getStatus() == 3){
                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Không tồn tại khóa tập này!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus() == 2){
                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Không tồn tại học viên này!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus() == 1){
                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Cập nhật khoa tap thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(clEditKTDT, "Cập nhật khoa tap không thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
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