package com.example.testbaitap.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.RecyclerHDAdapter;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.DoanhThu;
import com.example.testbaitap.entity.HoaDon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesDailyFragment extends Fragment {
    private TextView textView;
    private SimpleAPI simpleAPI;
    private ArrayList<DoanhThu> doanhThuArrayList;
    private RecyclerHDAdapter recyclerHDAdapter;
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_daily, container, false);
        textView = view.findViewById(R.id.textView);
        TextView tvNgay = view.findViewById(R.id.textViewNgay);
        recyclerView = view.findViewById(R.id.recyHoaDon);
        doanhThuArrayList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
        String tgHienTai = simpleDateFormat.format(Calendar.getInstance().getTime());
        LoadData(tgHienTai);
        tvNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(requireContext(),(view, year, month, dayOfMonth) ->
                {
                    tvNgay.setText("Ngày "+dayOfMonth+" tháng "+(month+1)+" năm "+year);
                    doanhThuArrayList = new ArrayList<>();
                    LoadData(year+"-"+(month+1)+"-"+dayOfMonth);

                },now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
        return view;
    }
    public void LoadData(String ngay){
        simpleAPI = Constants.instance();
        simpleAPI.getDoanhThuTheoNgay(ngay).enqueue(new Callback<ArrayList<DoanhThu>>() {
            @Override
            public void onResponse(Call<ArrayList<DoanhThu>> call, Response<ArrayList<DoanhThu>> response) {
                try {
                    doanhThuArrayList = response.body();
                    if(doanhThuArrayList.size()>0){
                        textView.setText("Tổng doanh thu: "+doanhThuArrayList.get(0).getTongTien()+" VND");
                    }
                    else {
                        textView.setText("Chưa có dữ liệu ngày này!");
                    }
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoanhThu>> call, Throwable t) {
                textView.setText("Chưa có dữ liệu ngày này!");
                Log.d("quan", t.toString());
            }
        });

        simpleAPI.getHoaDonTrongNgay(ngay).enqueue(new Callback<ArrayList<HoaDon>>() {
            @Override
            public void onResponse(Call<ArrayList<HoaDon>> call, Response<ArrayList<HoaDon>> response) {
                try {
                    ArrayList<HoaDon> hoaDonArrayList = response.body();
                    recyclerHDAdapter = new RecyclerHDAdapter(hoaDonArrayList, requireContext());
                    recyclerView.setAdapter(recyclerHDAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HoaDon>> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }
}