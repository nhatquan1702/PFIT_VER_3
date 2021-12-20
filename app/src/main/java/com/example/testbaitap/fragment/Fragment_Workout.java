package com.example.testbaitap.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.activity.DetailTCActivity;
import com.example.testbaitap.activity.ExcerciseByCourseActivity;
import com.example.testbaitap.activity.LoginActivity;
import com.example.testbaitap.activity.MainActivity;
import com.example.testbaitap.adapter.NgayTapRecyclerAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.HocVien_KhoaTap;
import com.example.testbaitap.entity.KhoaTap;
import com.example.testbaitap.entity.NgayTap;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.example.testbaitap.utils.Config;
import com.example.testbaitap.viewModel.PTNgayTapViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Workout extends Fragment {
    private NgayTapRecyclerAdapter adapter;
    private ArrayList<String> arr;
    private SimpleAPI simpleAPI;
    private TextView dayleft;
    private int height;
    private SharedPreferences sharedPreferences;
    private GridLayoutManager manager;
    private TextView percentScore1;
    private ProgressBar progressBarPT;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView recyclerView;
    private int squareSize;
    private int width;
    private ArrayList<NgayTap> ngayTapArrayList;
    private ArrayList<Integer> processArraylist;
    private int workoutPosition = -1;
    public PTNgayTapViewModel ptNgayTapViewModel;
    private ImageView b;
    private CardView cvHKT;

    SharedPreferences sharedPreferences1;
    SharedPreferences.Editor editor;

    public static Fragment_Workout newInstance(String param1, String param2) {
        Fragment_Workout fragment = new Fragment_Workout();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__workout, container, false);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ConstraintLayout constraintLayout = view.findViewById(R.id.constraintLayoutProcess);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        b = (ImageView) view.findViewById(R.id.b);
        cvHKT = (CardView) view.findViewById(R.id.cvHKT);
        progressBarPT = view.findViewById(R.id.progress_bar);
        progressBarPT.setVisibility(View.VISIBLE);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);

        sharedPreferences = getContext().getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        //Toast.makeText(requireContext(), sharedPreferences.getString("email", "username"), Toast.LENGTH_SHORT).show();
        String taiKhoan = sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, "");
        if(taiKhoan.equals("")){
            constraintLayout.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Bạn cần đăng nhập để sử dụng tính năng này");
            builder.setMessage("Bạn có muốn đăng nhập ngay?");
            builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else //lấy khóa tập tài khoản này, nếu chưa thì in thông báo chưa tham gia khóa tập nào{
        {
            progressBarPT.setVisibility(View.VISIBLE);
            simpleAPI = Constants.instance();
            simpleAPI.getKhachHang(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, "")).enqueue(new Callback<HocVien>() {
                @Override
                public void onResponse(Call<HocVien> call, Response<HocVien> response) {
                    HocVien hocVien = response.body();
                    try {
                        LoadKhoaTap(hocVien.getMaKhoaTap());
//                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataHV_KT", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("maHocVien", hocVien_khoaTap.getMaHocVien().trim());
//                        editor.putString("maKhoaTap", hocVien_khoaTap.getMaKhoaTap().trim());
                        //LoadPTKhoaTap(hocVien.getMaHocVien(), hocVien.getMaKhoaTap());

                        mSwipeRefresh.setOnRefreshListener(() -> {
                            LoadKhoaTap(hocVien.getMaKhoaTap());
                            //LoadPTKhoaTap(hocVien.getMaHocVien(), hocVien.getMaKhoaTap());
                            mSwipeRefresh.setRefreshing(false);
                        });
                        //editor.commit();
                        if(hocVien.getMaKhoaTap() == null){
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setTitle("Thông báo");
                            builder.setMessage("Bạn chưa tham gia khóa tập nào?");
                            builder.setPositiveButton("Đăng ký khóa tập", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(requireContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        progressBarPT.setVisibility(View.INVISIBLE);
                    }
                    catch (Exception e){
                        Log.d("quan", e.toString());
                        progressBarPT.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<HocVien> call, Throwable t) {
                    //Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    progressBarPT.setVisibility(View.INVISIBLE);
                }
            });
        }



        this.percentScore1 = (TextView) view.findViewById(R.id.percentScore);
        this.dayleft = (TextView) view.findViewById(R.id.daysLeft);
        String str = "thirtyday";
        boolean z = this.sharedPreferences.getBoolean(str, false);
        String str2 = "daysInserted";
        boolean z2 = this.sharedPreferences.getBoolean(str2, false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress);
        this.progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.launch_progressbar));


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        simpleAPI = Constants.instance();
        ngayTapArrayList = new ArrayList<>();
        processArraylist = new ArrayList<>();

        simpleAPI.getListNgayTap().enqueue(new Callback<ArrayList<NgayTap>>() {
            @Override
            public void onResponse(Call<ArrayList<NgayTap>> call, Response<ArrayList<NgayTap>> response) {
                ngayTapArrayList = response.body();
                LoadPhanTramNgayTap();
                for(int i=0; i<ngayTapArrayList.size(); i++){
                    processArraylist.add(ngayTapArrayList.get(i).getNgayTap());
                }

//                int daysCompletionConter = 0;
//                double k = 0.0d;
//                for (int i = 0; i < Cont.TOTAL_DAYS; i++) {
//                    double d = k;
//                    double progress = (double) processArraylist.get(i);
//                    Double.isNaN(progress);
//                    k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
//                    if (processArraylist.get(i) >= 99.0f) {
//                        daysCompletionConter++;
//                    }
//                }
//                int i2 = daysCompletionConter;
//                daysCompletionConter = i2 + (i2 / 3);
//                progressBar.setProgress((int) k);
//                TextView textView = percentScore1;
//                StringBuilder sb = new StringBuilder();
//                sb.append((int) k);
//                sb.append("%");
//                textView.setText(sb.toString());
//                TextView textView2 = dayleft;
//                StringBuilder sb2 = new StringBuilder();
//                sb2.append(Cont.TOTAL_DAYS - daysCompletionConter);
//                sb2.append(" ngày tập luyện");
//                textView2.setText(sb2.toString());

                adapter = new NgayTapRecyclerAdapter(ngayTapArrayList, requireContext(), processArraylist);

                arr = new ArrayList<>();
                recyclerView.getRecycledViewPool().clear();
//                for (int i3 = 1; i3 <= Cont.TOTAL_DAYS; i3++) {
//                    ArrayList<String> arrayList = arr;
//                    StringBuilder sb3 = new StringBuilder();
//                    sb3.append("Ngày ");
//                    sb3.append(i3);
//                    arrayList.add(sb3.toString());
//                }
//                if (z) {
//                    SharedPreferences.Editor edit2 = sharedPreferences.edit();
//                    edit2.putBoolean(str, false);
//                    edit2.apply();
//                    daysCompletionConter = 0;
//                }
                recyclerView.setAdapter(adapter);
                adapter.setOnClickItemRecyclerView(new ItemClickInterface() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(requireContext(), ExcerciseByCourseActivity.class);
                        intent.putExtra("ngayTap", String.valueOf(ngayTapArrayList.get(position).getNgayTap()));
                        startActivity(intent);
                    }

                });
                recyclerView.setLayoutManager(mLayoutManager);
            }

            @Override
            public void onFailure(Call<ArrayList<NgayTap>> call, Throwable t) {
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void  LoadPhanTramNgayTap (PTNgayTapViewModel ptNgayTapViewModel, String maKhoaTap, String maHocVien, Integer ngayTap){
        ptNgayTapViewModel = new ViewModelProvider(this).get(PTNgayTapViewModel.class);

        simpleAPI = Constants.instance();
        PTNgayTapViewModel finalPtNgayTapViewModel = ptNgayTapViewModel;
        simpleAPI.getPhanTramBTTheoNgay(maKhoaTap, maHocVien, ngayTap).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                finalPtNgayTapViewModel.addData(Integer.parseInt(String.valueOf(status.getStatus())));
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
        ptNgayTapViewModel.getArrayListMutableLiveData().observe(this, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> integers) {
                processArraylist.addAll(integers);
            }
        });
    }

    public void LoadPhanTramNgayTap(){
        int dem = 0;
        for(int i=0 ; i<ngayTapArrayList.size(); i++){
            if(ngayTapArrayList.get(i).getTrangThai()==1)
                dem++;
        }
        int pt = dem*100/23;
        percentScore1.setText(String.valueOf(pt)+"%");
        progressBarPT.setProgress(pt);
    }

    public void LoadPTKhoaTap(String maHocVien, String maKhoaTap){
        progressBarPT.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getPhanTramBTTheoKhoa(maKhoaTap, maHocVien).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                try {
                    Status status = response.body();
                    if(status.getStatus()>0){
                        percentScore1.setText(String.valueOf(status.getStatus())+"%");
                        progressBarPT.setVisibility(View.INVISIBLE);
                    }
                    else{
                        percentScore1.setText(String.valueOf(0)+"%");
                        progressBarPT.setVisibility(View.INVISIBLE);
                    }
                }
                catch (Exception e){
                    percentScore1.setText(String.valueOf(0)+"%");
                    Log.d("quan", e.toString());
                    progressBarPT.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                percentScore1.setText(String.valueOf(0)+"%");
                Log.d("quan", t.toString());
                progressBarPT.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void LoadKhoaTap(String maKT){
        progressBarPT.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getKhoaTap(maKT).enqueue(new Callback<KhoaTap>() {
            @Override
            public void onResponse(Call<KhoaTap> call, Response<KhoaTap> response) {
                try {
                    KhoaTap khoaTap = response.body();
                    Picasso.get()
                            .load(khoaTap.getHinhQuangCao())
                            .placeholder(R.drawable.banner_1)
                            .error(R.drawable.banner_1)
                            .into(b);
                    cvHKT.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(requireContext(), DetailTCActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("maKT", khoaTap.getMaKhoaTap());
                            bundle.putString("tenKT", khoaTap.getTenKhoaTap());
                            bundle.putString("hinhKT", khoaTap.getHinhQuangCao());
                            bundle.putInt("dtKT", khoaTap.getChoDoiTuong());
                            bundle.putInt("giaKT", khoaTap.getGiaTheoThang());
                            bundle.putInt("ttKT", khoaTap.getTrangThai());
                            bundle.putString("nvKT", khoaTap.getMaNhanVien());
                            bundle.putString("hlvKT", khoaTap.getMaHuanLuyenVien());
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        }
                    });
                    progressBarPT.setVisibility(View.INVISIBLE);
                }
                catch (Exception e){
                    percentScore1.setText(String.valueOf(0)+"%");
                    Log.d("quan", e.toString());
                    progressBarPT.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<KhoaTap> call, Throwable t) {
                progressBarPT.setVisibility(View.INVISIBLE);
            }
        });
    }
}