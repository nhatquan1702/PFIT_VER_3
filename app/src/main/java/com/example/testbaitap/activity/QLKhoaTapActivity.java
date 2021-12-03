package com.example.testbaitap.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.TrainingCourseAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.KhoaTap;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLKhoaTapActivity extends AppCompatActivity {
    private ArrayList<KhoaTap> khoaTapArrayList;
    private SimpleAPI simpleAPI;
    private SharedPreferences sharedPreferences;
    private TrainingCourseAdapter trainingCourseAdapter;
    private String mAuthor = null;
    private FloatingActionButton fab1, fab2, fab3, fab4;
    private Boolean fabCheck = true;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    int requestCode = 2;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlkhoa_tap);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        LoadFab();

        mRecyclerView = findViewById(R.id.rv_listbaiviet);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefresh = findViewById(R.id.swipe_refresh);

        khoaTapArrayList = new ArrayList<>();
//        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
//        mAuthor = sharedPreferences.getString("email", "admin");
//        Log.d("author",mAuthor);
        LoadKhoaTap();
        trainingCourseAdapter = new TrainingCourseAdapter(QLKhoaTapActivity.this, khoaTapArrayList);
        mRecyclerView.setAdapter(trainingCourseAdapter);
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
                KhoaTap mPost = trainingCourseAdapter.getAtPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(QLKhoaTapActivity.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có thực sự muốn xóa bài viết này?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Đã xóa" , Toast.LENGTH_SHORT).show();
                        //call delete
                        DeletePost(String.valueOf(mPost.getMaKhoaTap()));
                        LoadKhoaTap();
                        trainingCourseAdapter = new TrainingCourseAdapter(QLKhoaTapActivity.this , khoaTapArrayList);
                        mRecyclerView.setAdapter(trainingCourseAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Đã hủy thao tác" , Toast.LENGTH_SHORT).show();
                        LoadKhoaTap();
                        trainingCourseAdapter = new TrainingCourseAdapter(getApplicationContext(), khoaTapArrayList);
                        mRecyclerView.setAdapter(trainingCourseAdapter);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        helper.attachToRecyclerView(mRecyclerView);

        trainingCourseAdapter.setOnItemClickListener(new ItemClickInterface() {
            @Override
            public void onClick(View view, int position) {
                KhoaTap mPost = trainingCourseAdapter.getAtPosition(position);
                // call update
//                Intent intent = new Intent(QLKhoaTapActivity.this, EditTCActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("post_title", mPost.getPost_title());
//                bundle.putString("post_content", mPost.getPost_content());
//                bundle.putString("post_img", mPost.getPost_img());
//                bundle.putInt("post_id", mPost.getPost_id());
//                intent.putExtras(bundle);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivityIfNeeded(intent, 0);
            }
        });
        mSwipeRefresh.setOnRefreshListener(() -> {
            LoadKhoaTap();
            mSwipeRefresh.setRefreshing(false);
        });
    }
    private void LoadFab() {
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fabCheck){
                    FabVisible();
                    fab1.setImageResource(R.drawable.ic_baseline_close_24);
                    fabCheck = false;
                }
                else {
                    FabHine();
                    fab1.setImageResource(R.drawable.ic_baseline_add_24);
                    fabCheck = true;
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog datePicker = new DatePickerDialog(QLKhoaTapActivity.this,(view, year, month, dayOfMonth) ->{
//                    String strDate = "'" +year +"-"+ (month+1) +"-"+ dayOfMonth +"'";
//                    mData = new ArrayList<>();
//                    simpleAPI = Constants.instance();
//                    simpleAPI.getBaiVietByAccountAndDate(mAuthor,strDate).enqueue(new Callback<ArrayList<Post>>() {
//                        @Override
//                        public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
//                            mData = response.body();
//                            Log.e("json", response.body().toString()+" " +strDate + " " + mAuthor + " " + String.valueOf(mData.size()));
//                            if(mData.size() == 0)
//                                Toast.makeText(ListBaiVietActivity.this,"không có bài viết nào vào ngày này",Toast.LENGTH_SHORT).show();
//                            adapter.updateChange(mData);
//                        }
//
//                        @Override
//                        public void onFailure(Call<ArrayList<Post>> call, Throwable t) {
//                            if(call.isCanceled()) {
//                                Log.d("fail", "request was aborted");
//                            }else {
//                                Log.d("fail", "Unable to submit post to API.");
//                            }
//                        }
//                    });
//                },now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
//                datePicker.show();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(QLKhoaTapActivity.this, ChiTietKhoaTap.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                startActivityIfNeeded(intent, 0);
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadKhoaTap();
                trainingCourseAdapter = new TrainingCourseAdapter(getApplicationContext(), khoaTapArrayList);
                mRecyclerView.setAdapter(trainingCourseAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
        });
    }

    private void DeletePost(String maKhoaTap) {
        simpleAPI = Constants.instance();
        simpleAPI.updateTrangThaiKhoaTap(maKhoaTap, 0).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                if(status.getStatus()==2){
                    Toast.makeText(QLKhoaTapActivity.this, "Xóa bài viết thành công!", Toast.LENGTH_SHORT).show();
                    LoadKhoaTap();
                    trainingCourseAdapter = new TrainingCourseAdapter(getApplicationContext(), khoaTapArrayList);
                    mRecyclerView.setAdapter(trainingCourseAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                }
                else {
                    Toast.makeText(QLKhoaTapActivity.this, "Xóa bài viết không thành công!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Toast.makeText(QLKhoaTapActivity.this, "Lỗi: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCode) {
            if(resultCode == Activity.RESULT_OK){
                LoadKhoaTap();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    public void LoadKhoaTap() {
        progressBar.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getAllKhoaTap().enqueue(new Callback<ArrayList<KhoaTap>>() {
            @Override
            public void onResponse(Call<ArrayList<KhoaTap>> call, Response<ArrayList<KhoaTap>> response) {
                try {
                    khoaTapArrayList = response.body();
                    trainingCourseAdapter.updateChange(khoaTapArrayList);
                    progressBar.setVisibility(View.GONE);
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<KhoaTap>> call, Throwable t) {
                Log.d("quan", t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void FabVisible(){
        fab2.show();
        fab3.show();
        fab4.show();
    }
    private void FabHine(){
        fab2.hide();
        fab3.hide();
        fab4.hide();
    }
}