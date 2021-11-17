package com.example.testbaitap.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.activity.AccountActivity;
import com.example.testbaitap.activity.ExcerciceByMuscleActivity;
import com.example.testbaitap.activity.ExcerciseByCourseActivity;
import com.example.testbaitap.activity.LoginActivity;
import com.example.testbaitap.activity.MainActivity;
import com.example.testbaitap.adapter.NgayTapRecyclerAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien_KhoaTap;
import com.example.testbaitap.entity.NgayTap;
import com.example.testbaitap.excercise.ItemCleckInterfaceCheckBox;
import com.example.testbaitap.excercise.ItemClickInterface;
import com.example.testbaitap.utils.Cont;

import java.util.ArrayList;
import java.util.List;

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
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private int squareSize;
    private int width;
    private ArrayList<NgayTap> ngayTapArrayList;
    private ArrayList<Integer> processArraylist;
    private int workoutPosition = -1;

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

        sharedPreferences = getContext().getSharedPreferences("dataLogin", MODE_PRIVATE);
        //Toast.makeText(requireContext(), sharedPreferences.getString("email", "username"), Toast.LENGTH_SHORT).show();
        String taiKhoan = sharedPreferences.getString("email", "username");
        if(taiKhoan.equals("username")){
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
            simpleAPI = Constants.instance();
            simpleAPI.getMaKhoaTapTheoMaHocVien(taiKhoan).enqueue(new Callback<HocVien_KhoaTap>() {
                @Override
                public void onResponse(Call<HocVien_KhoaTap> call, Response<HocVien_KhoaTap> response) {
                    HocVien_KhoaTap hocVien_khoaTap = response.body();
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("dataHV_KT", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("maHocVien", hocVien_khoaTap.getMaHocVien().trim());
                    editor.putString("maKhoaTap", hocVien_khoaTap.getMaKhoaTap().trim());
                    editor.commit();
                    if(hocVien_khoaTap == null){
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
                }

                @Override
                public void onFailure(Call<HocVien_KhoaTap> call, Throwable t) {
                    Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
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
                for(int i=0; i<ngayTapArrayList.size(); i++){
                    processArraylist.add(ngayTapArrayList.get(i).getNgayTap());
                }

                int daysCompletionConter = 0;
                double k = 0.0d;
                for (int i = 0; i < Cont.TOTAL_DAYS; i++) {
                    double d = k;
                    double progress = (double) processArraylist.get(i);
                    Double.isNaN(progress);
                    k = (double) ((float) (d + ((progress * 4.348d) / 100.0d)));
                    if (processArraylist.get(i) >= 99.0f) {
                        daysCompletionConter++;
                    }
                }
                int i2 = daysCompletionConter;
                daysCompletionConter = i2 + (i2 / 3);
                progressBar.setProgress((int) k);
                TextView textView = percentScore1;
                StringBuilder sb = new StringBuilder();
                sb.append((int) k);
                sb.append("%");
                textView.setText(sb.toString());
                TextView textView2 = dayleft;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Cont.TOTAL_DAYS - daysCompletionConter);
                sb2.append(" ngày còn lại");
                textView2.setText(sb2.toString());

                adapter = new NgayTapRecyclerAdapter(ngayTapArrayList, requireContext(), processArraylist);

                arr = new ArrayList<>();
                recyclerView.getRecycledViewPool().clear();
                for (int i3 = 1; i3 <= Cont.TOTAL_DAYS; i3++) {
                    ArrayList<String> arrayList = arr;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Ngày ");
                    sb3.append(i3);
                    arrayList.add(sb3.toString());
                }
                if (z) {
                    SharedPreferences.Editor edit2 = sharedPreferences.edit();
                    edit2.putBoolean(str, false);
                    edit2.apply();
                    daysCompletionConter = 0;
                }
                recyclerView.setAdapter(adapter);
                adapter.setOnClickItemRecyclerView(new ItemClickInterface() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(requireContext(), ExcerciseByCourseActivity.class);
                        intent.putExtra("ngayTap", String.valueOf(ngayTapArrayList.get(position).getNgayTap()));
                        startActivity(intent);
                    }
                });

                adapter.setItemCleckInterfaceCheckBox(new ItemCleckInterfaceCheckBox() {
                    @Override
                    public void onClick(View view, int position, boolean check) {
                        int ngayHT = 0;
                        if(ngayTapArrayList.get(position)!=null && check==true){
                            ngayHT = ngayHT+1;
                        }
                        Toast.makeText(requireContext(), String.valueOf(ngayHT), Toast.LENGTH_SHORT).show();
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
}