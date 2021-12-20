package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.BaiTapTheoNhomCoRecyclerAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.BaiTap;
import com.example.testbaitap.entity.ChiTietBaiTap;
import com.example.testbaitap.entity.HocVien;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercice_by_muscle);
         tvTB = findViewById(R.id.tvTB);
        btnHoanThanh = findViewById(R.id.btnHoanThanh);
        btnHoanThanh.setOnClickListener(view->{
            Toast.makeText(this,"cc nhat quan",Toast.LENGTH_SHORT).show();
        });
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
    public void LoadData(String maHV, String ngayTap){
        simpleAPI = Constants.instance();
        simpleAPI.getKhachHang(maHV).enqueue(new Callback<HocVien>() {
            @Override
            public void onResponse(Call<HocVien> call, Response<HocVien> response) {
                try {
                    HocVien hocVien = response.body();
                    LoadKT(hocVien.getMaKhoaTap(), ngayTap);
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
    public void LoadKT(String maKT, String ngayTap){
        simpleAPI = Constants.instance();
        simpleAPI.getBaiTapTheoKhoaVaNgay(maKT, ngayTap).enqueue(new Callback<ArrayList<BaiTap>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiTap>> call, Response<ArrayList<BaiTap>> response) {
                baiTapArrayList = response.body();
                try {
                    if(baiTapArrayList.size()==0){
                        tvTB.setText("Chưa có bài tập nào");
                    }
                    BaiTapTheoNhomCoRecyclerAdapter baiTapTheoNhomCoRecyclerAdapter = new BaiTapTheoNhomCoRecyclerAdapter(baiTapArrayList, ExcerciseByCourseActivity.this);
                    baiTapRecyclerView.setAdapter(baiTapTheoNhomCoRecyclerAdapter);
                    baiTapRecyclerView.setLayoutManager(new LinearLayoutManager(ExcerciseByCourseActivity.this));
                    baiTapTheoNhomCoRecyclerAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent intent = new Intent(ExcerciseByCourseActivity.this, DetailExcerciseActivity.class);
                            intent.putExtra("maBaiTap", String.valueOf(baiTapArrayList.get(position).getMaBaiTap()));
                            startActivity(intent);
                        }

//                            if(precent == 100){
//                                btnHoanThanh.setEnabled(true);
//                            }
//                            else
//                                btnHoanThanh.setEnabled(false);
                    });
                }
                catch (Exception e){
                    tvTB.setText("Chưa có bài tập nào");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BaiTap>> call, Throwable t) {
                Log.d("quan", t.toString());
                //Toast.makeText(ExcerciseByCourseActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}