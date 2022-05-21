package com.example.testbaitap.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.UserAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TaiKhoan;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAccountRegisterTC extends AppCompatActivity {
    private ArrayList<HocVien> hocVienArrayList;
    private ArrayList<HocVien> tmpArray;
    private SimpleAPI simpleAPI;
    private UserAdapter adapter;
    private FloatingActionButton fab1;
    private ProgressBar progressBar;
    RecyclerView mRecyclerView;
    private String maKT;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_account_register_tc);

        Bundle bundle = getIntent().getExtras();
        if(bundle!= null) {
            maKT = bundle.getString("maKT", "");
        }

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        LoadFab(maKT, 0);

        mRecyclerView = findViewById(R.id.rv_listbaiviet);
        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        hocVienArrayList = new ArrayList<>();

        LoadBaiViet(maKT, 0);
        adapter = new UserAdapter(getApplicationContext(), hocVienArrayList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                HocVien hocVien = adapter.getAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListAccountRegisterTC.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có thực sự muốn xóa tài khoản này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Đã xóa" , Toast.LENGTH_SHORT).show();
                        //call delete
                        DeleteHocVien(String.valueOf(hocVien.getMaHocVien()), hocVien.getMaKhoaTap(), 0);
                        LoadBaiViet(hocVien.getMaKhoaTap(), 0);
                        adapter = new UserAdapter(getApplicationContext(), hocVienArrayList);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Đã hủy thao tác" , Toast.LENGTH_SHORT).show();
                        LoadBaiViet(hocVien.getMaKhoaTap(), 0);
                        adapter = new UserAdapter(getApplicationContext(), hocVienArrayList);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        helper.attachToRecyclerView(mRecyclerView);
        searchView = findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                queryData(newText);
                return true;
            }
        });
        adapter.setOnItemClickListener(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                HocVien mPost = adapter.getAtPosition(position);
                // call update
                Intent intent = new Intent(ListAccountRegisterTC.this, AcceptUserRegister.class);
                Bundle bundle = new Bundle();
                bundle.putString("mahocvien", mPost.getMaHocVien());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);
            }
        });
    }
    public void queryData(String query) {
        ArrayList<HocVien> tmp = new ArrayList<>();
        for(HocVien hocVien:tmpArray){
            if(hocVien.getMaHocVien().contains(query))
                tmp.add(hocVien);
        }
        adapter.updateChange(tmp);
    }
    private void LoadFab(String maKT, int tt) {
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadBaiViet(maKT, tt);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadBaiViet(maKT, 0);
    }

    private void DeleteHocVien(String maHocVien, String maKT, int tt) {
        simpleAPI = Constants.instance();
//        simpleAPI.deletePost(post_id).enqueue(new Callback<Status>() {
//            @Override
//            public void onResponse(Call<Status> call, Response<Status> response) {
//                Status status = response.body();
//                if(status.getStatus()==2){
//                    Toast.makeText(ListAccountRegisterTC.this, "Xóa học viên thành công!", Toast.LENGTH_SHORT).show();
//                    LoadBaiViet(maKT, tt);
//                    adapter = new UserAdapter(getApplicationContext(), hocVienArrayList);
//                    mRecyclerView.setAdapter(adapter);
//                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                }
//                else {
//                    Toast.makeText(ListAccountRegisterTC.this, "Xóa học viên không thành công!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Status> call, Throwable t) {
//                Toast.makeText(ListAccountRegisterTC.this, "Lỗi: "+t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void LoadBaiViet(String maKT, int tt) {
        progressBar.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getHVChuaDuyet(maKT, tt).enqueue(new Callback<ArrayList<HocVien>>() {
            @Override
            public void onResponse(Call<ArrayList<HocVien>> call, Response<ArrayList<HocVien>> response) {
                try {
                    hocVienArrayList = response.body();
                    tmpArray = response.body();
                    adapter.updateChange(hocVienArrayList);
                    progressBar.setVisibility(View.GONE);
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ListAccountRegisterTC.this, "Chưa có học viên đăng ky khóa tập này!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HocVien>> call, Throwable t) {
                Toast.makeText(ListAccountRegisterTC.this, "Chưa có học viên đăng ky khóa tập này!", Toast.LENGTH_SHORT).show();
                if(call.isCanceled()) {
                    Log.d("fail", "request was aborted");
                }else {
                    Log.d("fail", "Unable to submit post to API.");
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}