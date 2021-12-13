package com.example.testbaitap.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.DoanhThu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SalesYearlyFragment extends Fragment {
    private Spinner spinnerThang;
    private List<String> list, listThang;
    private SimpleAPI simpleAPI;
    ArrayList<DoanhThu> doanhThuArrayList;
    BarChart barChart;

    public static SalesYearlyFragment newInstance(String param1, String param2) {
        SalesYearlyFragment fragment = new SalesYearlyFragment();
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
        View view = inflater.inflate(R.layout.fragment_sales_yearly, container, false);

        barChart = view.findViewById(R.id.barChart);
        listThang = new ArrayList<>();
        listThang.add("2016");
        listThang.add("2017");
        listThang.add("2018");
        listThang.add("2019");
        listThang.add("2020");
        listThang.add("2021");
        listThang.add("2022");


        spinnerThang = (Spinner) view.findViewById(R.id.spinnerThang);
        ArrayAdapter spinnerAdapterThang = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, listThang);
        spinnerThang.setAdapter(spinnerAdapterThang);

        doanhThuArrayList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
        String tgHienTai = simpleDateFormat.format(Calendar.getInstance().getTime());
        String tam = tgHienTai.substring(0,4);
        LoadData("2021");
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doanhThuArrayList = new ArrayList<>();
                LoadData(listThang.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(requireContext(),"Chưa chọn", Toast.LENGTH_SHORT).show();
            }
        });
        TextView tvThang = view.findViewById(R.id.tvThang);
        tvThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(requireContext(),(view, year, month, dayOfMonth) ->
                {
                    doanhThuArrayList = new ArrayList<>();
                    LoadData(String.valueOf(year));

                },now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        return view;
    }
    public void LoadData(String nam){
        simpleAPI = Constants.instance();
        simpleAPI.getDoanhThuTheoNam(nam).enqueue(new Callback<ArrayList<DoanhThu>>() {
            @Override
            public void onResponse(Call<ArrayList<DoanhThu>> call, Response<ArrayList<DoanhThu>> response) {
                try {
                    ArrayList<BarEntry> entryArrayList = new ArrayList<>();
                    for(int i=0; i<doanhThuArrayList.size(); i++){
                        if(doanhThuArrayList.size()>0){
                            entryArrayList.add(new BarEntry(Integer.parseInt(doanhThuArrayList.get(i).getNgay()), doanhThuArrayList.get(i).getTongTien()));
                            BarDataSet dataSet = new BarDataSet(entryArrayList, "Doanh thu theo năm"); // add entries to dataset
                            dataSet.setColor(getResources().getColor(R.color.dot_dark_screen));
                            dataSet.setValueTextColor(getResources().getColor(R.color.black)); // styling, ...
                            dataSet.setValueTextSize(10f);
                            BarData barData = new BarData(dataSet);
                            barChart.setFitBars(true);
                            barChart.setData(barData);
                            barChart.animateY(1000);

                            Description description = new Description();
                            description.setText("");
                            barChart.setDescription(description);

                            barChart.setNoDataText("Chưa có dữ liệu");
                            barChart.setNoDataTextColor(getResources().getColor(R.color.red));
                        }
                        else { //2021-01-11
                            barChart.setNoDataText("Chưa có dữ liệu");
                            barChart.setNoDataTextColor(getResources().getColor(R.color.red));
                        }
                    }
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoanhThu>> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }
}