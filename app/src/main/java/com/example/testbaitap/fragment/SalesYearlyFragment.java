package com.example.testbaitap.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.SimpleAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SalesYearlyFragment extends Fragment {
    private Spinner spinnerThang;
    private List<String> list, listThang;
    private SimpleAPI simpleAPI;
    ArrayList<Integer> floatArrayList;
    ArrayList<Integer> testArrayList;
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
        spinnerThang.setSelection(0);
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                    floatArrayList = new ArrayList<>();
                    //LoadData("quan", String.valueOf(month+1), String.valueOf(year));
                    int a = month+1;
                    if(a==1){
                        spinnerThang.setSelection(0);
                    }
                    if(a==2){
                        spinnerThang.setSelection(1);
                    }
                    if(a==3){
                        spinnerThang.setSelection(2);
                    }
                    if(a==4){
                        spinnerThang.setSelection(3);
                    }
                    if(a==5){
                        spinnerThang.setSelection(4);
                    }
                    if(a==6){
                        spinnerThang.setSelection(5);
                    }
                    if(a==7){
                        spinnerThang.setSelection(6);
                    }
                    if(a==8){
                        spinnerThang.setSelection(7);
                    }
                    if(a==9){
                        spinnerThang.setSelection(7);
                    }
                    if(a==10){
                        spinnerThang.setSelection(9);
                    }
                    if(a==11){
                        spinnerThang.setSelection(10);
                    }
                    if(a==12){
                        spinnerThang.setSelection(11);
                    }

                },now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });


        floatArrayList = new ArrayList<>();
        testArrayList = new ArrayList<>();
        floatArrayList.add(2500000);
        floatArrayList.add(2800000);
        floatArrayList.add(2100000);
        floatArrayList.add(3500000);
        floatArrayList.add(3000000);
        testArrayList.add(1);
        testArrayList.add(2);
        testArrayList.add(3);
        testArrayList.add(4);
        testArrayList.add(5);
        LoadData();

        return view;
    }
    public void LoadData(){
        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
        for(int i=0; i<floatArrayList.size(); i++){
            if(floatArrayList.size()>0){
                entryArrayList.add(new BarEntry(testArrayList.get(i), floatArrayList.get(i)));
            }
            else { //2021-01-11
                barChart.setNoDataText("Chưa có dữ liệu");
                barChart.setNoDataTextColor(getResources().getColor(R.color.red));
            }
        }
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
}