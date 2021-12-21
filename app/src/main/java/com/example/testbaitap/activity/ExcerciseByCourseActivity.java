package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.BaiTapTheoNgayRecyclerAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.BaiTap;
import com.example.testbaitap.entity.ChiTietBaiTapChoHV;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.example.testbaitap.utils.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExcerciseByCourseActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SimpleAPI simpleAPI;
    TextView textViewTenNhomCo;
    private ArrayList<BaiTap> baiTapArrayList;
    RecyclerView baiTapRecyclerView;
    TextView tvTB;
    Button btnHoanThanh;
    private Status status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercice_by_muscle);
         tvTB = findViewById(R.id.tvTB);
        btnHoanThanh = findViewById(R.id.btnHoanThanh);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ExcerciseByCourseActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        String ngayTap = intent.getStringExtra("ngayTap");
        textViewTenNhomCo = findViewById(R.id.mtoolbar_title1);
        textViewTenNhomCo.setText("Ngày " + ngayTap);
        sharedPreferences = getSharedPreferences("dataHV_KT", MODE_PRIVATE);
        String maHocVien = sharedPreferences.getString("maHocVien", null);
        String maKhoaTap = sharedPreferences.getString("maKhoaTap", null);
        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        baiTapArrayList = new ArrayList<>();
        baiTapRecyclerView = findViewById(R.id.recyclerBaiTap);
        LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), ngayTap);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String ngayTap = intent.getStringExtra("ngayTap");
        textViewTenNhomCo = findViewById(R.id.mtoolbar_title1);
        textViewTenNhomCo.setText("Ngày " + ngayTap);
        sharedPreferences = getSharedPreferences("dataHV_KT", MODE_PRIVATE);
        String maHocVien = sharedPreferences.getString("maHocVien", null);
        String maKhoaTap = sharedPreferences.getString("maKhoaTap", null);
        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        baiTapArrayList = new ArrayList<>();
        baiTapRecyclerView = findViewById(R.id.recyclerBaiTap);
        LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), ngayTap);
    }

    public void LoadData(String maHV, String ngayTap){
        simpleAPI = Constants.instance();
        simpleAPI.getKhachHang(maHV).enqueue(new Callback<HocVien>() {
            @Override
            public void onResponse(Call<HocVien> call, Response<HocVien> response) {
                try {
                    HocVien hocVien = response.body();
                    LoadKT(hocVien.getMaKhoaTap(), ngayTap, hocVien.getMaHocVien());
                    LoadPhanTramHoanThanhBT(hocVien.getMaHocVien(), hocVien.getMaKhoaTap(), ngayTap);
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
    public void LoadKT(String maKT, String ngayTap, String maHV){
        simpleAPI = Constants.instance();
        simpleAPI.getBaiTapTheoKhoaVaNgay(maKT, ngayTap).enqueue(new Callback<ArrayList<BaiTap>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiTap>> call, Response<ArrayList<BaiTap>> response) {
                try {
                    baiTapArrayList = response.body();
                    if(baiTapArrayList.size()==0){
                        tvTB.setText("Chưa có bài tập nào");
                    }
                    simpleAPI = Constants.instance();
                    simpleAPI.getListChiTietBTTheoNgay(maKT, maHV, Integer.parseInt(ngayTap)).enqueue(new Callback<ArrayList<ChiTietBaiTapChoHV>>() {
                        @Override
                        public void onResponse(Call<ArrayList<ChiTietBaiTapChoHV>> call, Response<ArrayList<ChiTietBaiTapChoHV>> response) {
                            try {
                                ArrayList<ChiTietBaiTapChoHV> chiTietBaiTapChoHVArrayList = new ArrayList<>();
                                chiTietBaiTapChoHVArrayList = response.body();
                                BaiTapTheoNgayRecyclerAdapter baiTapTheoNhomCoRecyclerAdapter = new BaiTapTheoNgayRecyclerAdapter(baiTapArrayList, ExcerciseByCourseActivity.this, chiTietBaiTapChoHVArrayList);
                                baiTapRecyclerView.setAdapter(baiTapTheoNhomCoRecyclerAdapter);
                                baiTapRecyclerView.setLayoutManager(new LinearLayoutManager(ExcerciseByCourseActivity.this));
                                baiTapTheoNhomCoRecyclerAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        Intent intent = new Intent(ExcerciseByCourseActivity.this, DetailExcerciseActivity.class);
                                        intent.putExtra("maBaiTap", String.valueOf(baiTapArrayList.get(position).getMaBaiTap()));
                                        startActivity(intent);
                                    }

                                });
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<ChiTietBaiTapChoHV>> call, Throwable t) {
                            Log.d("quan", t.toString());
                        }
                    });

                }
                catch (Exception e){
                    tvTB.setText("Chưa có bài tập nào");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BaiTap>> call, Throwable t) {
                Log.d("quan", t.toString());
                tvTB.setText("Chưa có bài tập nào");
                //Toast.makeText(ExcerciseByCourseActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void LoadPhanTramHoanThanhBT(String maHV, String maKT, String ngayTap){
        simpleAPI = Constants.instance();
        simpleAPI.getPhanTramBTTheoNgay(maKT, maHV, Integer.parseInt(ngayTap)).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                try {
                    Status status = response.body();
                    if(status.getStatus()==100){
                        btnHoanThanh.setText("Đã hoàn thành");
                        btnHoanThanh.setEnabled(true);
//                        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                UpdateTrangThaiNgayTap(ngayTap, 1, maHV, maKT);
//                            }
//                        });
                    }
                    else {
                        btnHoanThanh.setText("Hoàn thành "+String.valueOf(status.getStatus())+"%");
                        btnHoanThanh.setEnabled(false);
//                        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                UpdateTrangThaiNgayTap(ngayTap, 0, maHV, maKT);
//                            }
//                        });
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
//    public void UpdateTrangThaiNgayTap(String ngay, int trangThai, String maHV, String maKT){
//        simpleAPI = Constants.instance();
//        simpleAPI.upDateTrangThaiNgayTap(ngay, trangThai).enqueue(new Callback<Status>() {
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                try {
//                    Status status1 = response.body();
//                    if(status1.getStatus() == 1){
//                        Toast.makeText(ExcerciseByCourseActivity.this, "Đã cập nhật hoàn thành ngày tập!", Toast.LENGTH_SHORT).show();
//                        Intent intent = getIntent();
//                        String ngayTap = intent.getStringExtra("ngayTap");
//                        textViewTenNhomCo = findViewById(R.id.mtoolbar_title1);
//                        textViewTenNhomCo.setText("Ngày " + ngayTap);
//                        sharedPreferences = getSharedPreferences("dataHV_KT", MODE_PRIVATE);
//                        String maHocVien = sharedPreferences.getString("maHocVien", null);
//                        String maKhoaTap = sharedPreferences.getString("maKhoaTap", null);
//                        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
//                        editor = sharedPreferences.edit();
//                        baiTapArrayList = new ArrayList<>();
//                        baiTapRecyclerView = findViewById(R.id.recyclerBaiTap);
//                        LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), ngayTap);
//                    }
//                    else {
//                        Toast.makeText(ExcerciseByCourseActivity.this, "Cập nhật hoàn thành không thành công!", Toast.LENGTH_SHORT).show();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Toast.makeText(ExcerciseByCourseActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                Log.d("quan", t.toString());
//            }
//        });
//    }
}