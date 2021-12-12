package com.example.testbaitap.process.n.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.TheTrang;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class YearlyFragment extends Fragment {
    private Spinner spinner, spinnerNam;
    private List<String> list, listNam;
    private SimpleAPI simpleAPI;
    ArrayList<TheTrang> trangArrayList;
    LineChart lineChart;
    public YearlyFragment() {
        // Required empty public constructor
    }

    public static YearlyFragment newInstance() {
        YearlyFragment fragment = new YearlyFragment();
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
        View view = inflater.inflate(R.layout.fragment_yearly, container, false);

        lineChart = view.findViewById(R.id.lineChartY);
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
        spinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);

        listNam = new ArrayList<>();
        listNam.add("2017");
        listNam.add("2018");
        listNam.add("2019");
        listNam.add("2020");
        listNam.add("2021");
        listNam.add("2022");
        spinnerNam = (Spinner) view.findViewById(R.id.spinnerNam);
        ArrayAdapter spinnerAdapterNam = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, listNam);
        spinnerNam.setAdapter(spinnerAdapterNam);
        spinnerNam.setSelection(4);
        spinnerNam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trangArrayList=new ArrayList<>();
                LoadDataYearLy("quan", listNam.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(requireContext(),"Chưa chọn", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    public class My implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return null;
        }
    }

    public void LoadDataYearLy(String maHocVien, String nam){
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVTheoNam(maHocVien, nam).enqueue(new Callback<ArrayList<TheTrang>>() {
            @Override
            public void onResponse(Call<ArrayList<TheTrang>> call, Response<ArrayList<TheTrang>> response) {
                trangArrayList = response.body();
                try {
                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                    for(int i=0; i<trangArrayList.size(); i++){
                        if(trangArrayList.size()>0){
                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getBmi()));
                        }
                        else { //2021-01-11
                            lineChart.setNoDataText("Chưa có dữ liệu");
                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                        }
                    }

                    LineDataSet dataSet = new LineDataSet(entryArrayList, "Chỉ số bmi"); // add entries to dataset
                    dataSet.setColor(getResources().getColor(R.color.red));
                    dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                    LineData lineData = new LineData(dataSet);
                    lineChart.setData(lineData);
                    lineChart.invalidate(); // refresh
                    lineChart.animateX(2000);


                    Description description = new Description();
                    description.setText("Mô tả: Trục đứng biểu thị chỉ số bmi, trục ngang biểu thị các tháng");
                    lineChart.setDescription(description);

                    lineChart.setNoDataText("Chưa có dữ liệu");
                    lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                    XAxis xAxis = lineChart.getXAxis();
                    YAxis yAxisL = lineChart.getAxisLeft();
                    YAxis yAxisR = lineChart.getAxisRight();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            if (value == (int) value) {
                                if (value == 0)
                                    return "0";
                                else
                                    return String.valueOf((int)(value )).split(" ")[0];
                            }
                            else return "";
                        }
                    });
                }
                catch (Exception e){
                    lineChart.setNoDataText("Chưa có dữ liệu");
                    lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                }
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        //đối số postion là vị trí phần tử trong list Data
                        try {

                            switch (position){
                                case 0: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getBmi()));

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Chỉ số bmi"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị chỉ số bmi, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 1: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getCanNang()));

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Cân nặng"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị cân nặng, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 2: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getChieuCao()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Chiều cao"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị chiều cao, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 3: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getVong1()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng 1"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng 1, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 4: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getVong2()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng 2"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng 2, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 5: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getVong3()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng 3"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng 3, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 6: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getVongTay()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng tay"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng tay, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 7: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getVongDui()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng đùi"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng đùi, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                case 8: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(5,7)), trangArrayList.get(i).getLuongNuoc()));

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Lượng nước"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị lượng nước, trục ngang biểu thị các tháng");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                            XAxis xAxis = lineChart.getXAxis();
                                            YAxis yAxisL = lineChart.getAxisLeft();
                                            YAxis yAxisR = lineChart.getAxisRight();
                                            xAxis.setValueFormatter(new IndexAxisValueFormatter() {
                                                @Override
                                                public String getFormattedValue(float value) {
                                                    if (value == (int) value) {
                                                        if (value == 0)
                                                            return "0";
                                                        else
                                                            return String.valueOf((int)(value )).split(" ")[0];
                                                    }
                                                    else return "";
                                                }
                                            });
                                        }
                                        else {
                                            //chưa có dữ liệu
                                            lineChart.setNoDataText("Chưa có dữ liệu");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                default: break;
                            }
                        }
                        catch (Exception e ){
                            lineChart.setNoDataText("Chưa có dữ liệu");
                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        Toast.makeText(requireContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
                        lineChart.setNoDataText("Chưa có dữ liệu");
                        lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<TheTrang>> call, Throwable t) {
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}