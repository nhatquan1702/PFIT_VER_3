package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ExcerciceByMuscleActivity extends AppCompatActivity {
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
                ExcerciceByMuscleActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        String maNhomCo = intent.getStringExtra("maNhomCo");
        String tenNhomCo = intent.getStringExtra("tenNhomCo");
        textViewTenNhomCo = findViewById(R.id.mtoolbar_title1);
        textViewTenNhomCo.setText(tenNhomCo);

        RecyclerView baiTapRecyclerView = findViewById(R.id.recyclerBaiTap);
        baiTapArrayList = new ArrayList<>();


        simpleAPI = Constants.instance();
        simpleAPI.getBaiTapTheoNhomCo(maNhomCo).enqueue(new Callback<ArrayList<BaiTap>>() {
            @Override
            public void onResponse(Call<ArrayList<BaiTap>> call, Response<ArrayList<BaiTap>> response) {
                baiTapArrayList = response.body();
                try {
                    if(baiTapArrayList.size()==0){
                        tvTB.setText("Chưa có bài tập nào");
                    }
                    BaiTapTheoNhomCoRecyclerAdapter baiTapTheoNhomCoRecyclerAdapter = new BaiTapTheoNhomCoRecyclerAdapter(baiTapArrayList, ExcerciceByMuscleActivity.this);
                    baiTapRecyclerView.setAdapter(baiTapTheoNhomCoRecyclerAdapter);
                    baiTapRecyclerView.setLayoutManager(new LinearLayoutManager(ExcerciceByMuscleActivity.this));
                    baiTapTheoNhomCoRecyclerAdapter.setOnClickItemRecyclerView(new ItemClickInterface() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent intent = new Intent(ExcerciceByMuscleActivity.this, DetailExcerciseActivity.class);
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
                //Toast.makeText(ExcerciceByMuscleActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}