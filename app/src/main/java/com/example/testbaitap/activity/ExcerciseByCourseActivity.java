package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.BaiTapTheoNhomCoRecyclerAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.BaiTap;
import com.example.testbaitap.excercise.ItemClickInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExcerciseByCourseActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SimpleAPI simpleAPI;
    TextView textViewTenNhomCo;
    private ArrayList<BaiTap> baiTapArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercice_by_muscle);
        TextView tvTB = findViewById(R.id.tvTB);

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
        baiTapArrayList = new ArrayList<>();
        RecyclerView baiTapRecyclerView = findViewById(R.id.recyclerBaiTap);

        simpleAPI = Constants.instance();
        simpleAPI.getBaiTapTheoKhoaVaNgay(maKhoaTap, ngayTap).enqueue(new Callback<ArrayList<BaiTap>>() {
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
                    });
                }
                catch (Exception e){
                    tvTB.setText("Chưa có bài tập nào");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BaiTap>> call, Throwable t) {
                Toast.makeText(ExcerciseByCourseActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}