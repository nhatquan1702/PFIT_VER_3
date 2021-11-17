package com.example.testbaitap.process.n.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.TheTrang;
import com.example.testbaitap.utils.CustomProcessbar;
import com.example.testbaitap.utils.ProgressItem;
import com.github.mikephil.charting.charts.LineChart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeeklyFragment extends Fragment {
    private TextView tvTitleTenTheTrang, txtProgress, tvNhanXetBmi;
    private Spinner spinner;
    private List<String> list;
    private SimpleAPI simpleAPI;
    ArrayList<TheTrang> trangArrayList;
    private CustomProcessbar seekbar;

    private float totalSpan = 100;

    private float teal700 = 16;
    private float teal200 = 3;
    private float greenlight = 6;
    private float greendark = 5;
    private float smoking = 5;
    private float oglight = 5;
    private float ogdark = 5;
    private float red;
    private  TheTrang theTrang;


    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;

    public WeeklyFragment() {
        // Required empty public constructor
    }


    public static WeeklyFragment newInstance() {
        WeeklyFragment fragment = new WeeklyFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);
        seekbar = (CustomProcessbar) view.findViewById(R.id.customSeekBar);
        initDataToSeekbar();
        list = new ArrayList<>();
        list.add("Chỉ số bmi");
        list.add("Cân nặng");
        list.add("Chiều cao");
        list.add("Vòng 1");
        list.add("Vòng 2");
        list.add("Vòng 3");
        list.add("Vòng tay");
        list.add("Vòng đùi");
        list.add("Lượng nước");
        tvTitleTenTheTrang = (TextView) view.findViewById(R.id.tvTitleTenTheTrang);
        txtProgress = (TextView) view.findViewById(R.id.txtProgress);
        tvNhanXetBmi = (TextView) view.findViewById(R.id.tvNhanXetBmi);

        spinner = (Spinner) view.findViewById(R.id.imgButtonTenTheTrang);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);

        seekbar.setVisibility(View.INVISIBLE);
        txtProgress.setText("0");

        theTrang = new TheTrang();
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
        String tgHienTai = simpleDateFormat.format(Calendar.getInstance().getTime());
        LoadData("quan", tgHienTai);

        tvTitleTenTheTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(requireContext(),(view, year, month, dayOfMonth) ->
                {
                    theTrang = new TheTrang();
                    String strDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    tvTitleTenTheTrang.setText("Ngày " + dayOfMonth + " Tháng " + (month + 1) + " Năm " + year);
                    LoadData("quan", strDate);
                },now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        return view;
    }

    public void LoadData(String maHocVien, String date){
            simpleAPI = Constants.instance();
            simpleAPI.getTheTrangHVTheoNgay(maHocVien, date).enqueue(new Callback<TheTrang>() {
                @Override
                public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                    theTrang = response.body();
                    try {
                        float chieuCaoM = theTrang.getChieuCao()/100;
                        float bmi = theTrang.getCanNang() / (chieuCaoM*2);
                        seekbar.setVisibility(View.VISIBLE);
                        seekbar.setProgress((int)(bmi));
                        String strDouble = String.format("%.2f", bmi);
                        txtProgress.setText(strDouble + "kg/m2");
                        String txt = "";
                        if (bmi<16){
                            txt = "Trông bạn quá gầy";
                        }
                        if (bmi<18 &&bmi>16 || bmi ==16){
                            txt = "Trông bạn hơi gầy";
                        }
                        if (bmi>18 && bmi<25 || bmi ==18){
                            txt = "Thể trạng bình thường";
                        }
                        if (bmi>25 && bmi<30 || bmi ==25){
                            txt = "Trông bạn hơi béo";
                        }
                        if (bmi>30 && bmi<35 || bmi ==30){
                            txt = "Trông bạn quá béo";
                        }
                        if (bmi>35 && bmi<40 || bmi ==35){
                            txt = "Trông bạn rất béo";
                        }
                        if (bmi>40 || bmi ==40){
                            txt = "Thể trạng nguy hiểm";
                        }
                        tvNhanXetBmi.setText(txt);
                    }
                    catch (Exception e ){
                        seekbar.setVisibility(View.INVISIBLE);
                        txtProgress.setText("0");
                        tvNhanXetBmi.setText("Chưa có số liệu vào ngày này!");
                    }
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            //đối số postion là vị trí phần tử trong list Data
                            try {
                                float chieuCaoM = theTrang.getChieuCao()/100;
                                float bmi = theTrang.getCanNang() / (chieuCaoM*2);
                                String msg = "position :" + position + " value :" + list.get(position);
                                String strDouble1 = String.format("%.2f", theTrang.getCanNang());
                                String strDouble2 = String.format("%.2f", theTrang.getChieuCao());
                                String strDouble3 = String.format("%.2f", theTrang.getVong1());
                                String strDouble4 = String.format("%.2f", theTrang.getVong2());
                                String strDouble5 = String.format("%.2f", theTrang.getVong3());
                                String strDouble6 = String.format("%.2f", theTrang.getVongTay());
                                String strDouble7 = String.format("%.2f", theTrang.getVongDui());
                                String strDouble8 = String.format("%.0f", theTrang.getLuongNuoc());
                                String strDouble = String.format("%.2f", bmi);
                                String txt = "";
                                if (bmi<16){
                                    txt = "Trông bạn quá gầy";
                                }
                                if (bmi<18 &&bmi>16 || bmi ==16){
                                    txt = "Trông bạn hơi gầy";
                                }
                                if (bmi>18 && bmi<25 || bmi ==18){
                                    txt = "Thể trạng bình thường";
                                }
                                if (bmi>25 && bmi<30 || bmi ==25){
                                    txt = "Trông bạn hơi béo";
                                }
                                if (bmi>30 && bmi<35 || bmi ==30){
                                    txt = "Trông bạn quá béo";
                                }
                                if (bmi>35 && bmi<40 || bmi ==35){
                                    txt = "Trông bạn rất béo";
                                }
                                if (bmi>40 || bmi ==40){
                                    txt = "Thể trạng nguy hiểm";
                                }

                                switch (position){
                                    case 0: {
                                        seekbar.setVisibility(View.VISIBLE);
                                        seekbar.setProgress((int)(bmi));
                                        txtProgress.setText(strDouble +" kg/m2");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 1: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble1+ " kg");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 2: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble2 + " cm");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 3: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble3 +" cm");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 4: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble4 +" cm");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 5: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble5 +" cm");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 6: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble6 +" cm");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 7: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble7 +" cm");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    case 8: {
                                        seekbar.setVisibility(View.INVISIBLE);
                                        txtProgress.setText(strDouble8 +" ml");
                                        tvNhanXetBmi.setText(txt);
                                        break;
                                    }
                                    default: break;
                                }
                            }
                            catch (Exception e ){
                                seekbar.setVisibility(View.INVISIBLE);
                                txtProgress.setText("0");
                                tvNhanXetBmi.setText("Chưa có số liệu vào ngày này!");
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                            Toast.makeText(requireContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<TheTrang> call, Throwable t) {
                    seekbar.setVisibility(View.INVISIBLE);
                    txtProgress.setText("0");
                    tvNhanXetBmi.setText("Chưa có số liệu vào ngày này!");
                }
            });
    }

    private void initDataToSeekbar() {
        progressItemList = new ArrayList<ProgressItem>();
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((teal700 / totalSpan) * 100);
        mProgressItem.color = R.color.teal_200;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (teal200 / totalSpan) * 100;
        mProgressItem.color = R.color.teal_700;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenlight / totalSpan) * 100;
        mProgressItem.color = android.R.color.holo_green_dark;
        progressItemList.add(mProgressItem);
        // purple span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greendark / totalSpan) * 100;
        mProgressItem.color = android.R.color.holo_green_light;
        progressItemList.add(mProgressItem);
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (oglight / totalSpan) * 100;
        mProgressItem.color = android.R.color.holo_orange_light;
        progressItemList.add(mProgressItem);
        // greyspan
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (ogdark / totalSpan) * 100;
        mProgressItem.color = android.R.color.holo_orange_dark;
        progressItemList.add(mProgressItem);

        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (red / totalSpan) * 100;
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);

        seekbar.initData(progressItemList);
        seekbar.invalidate();
    }

}