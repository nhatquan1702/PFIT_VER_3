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
import com.example.testbaitap.entity.TheTrang;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalesMontlyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesMontlyFragment extends Fragment {
    private Spinner spinnerThang;
    private List<String> list, listThang;
    private SimpleAPI simpleAPI;
    ArrayList<DoanhThu> doanhThuArrayList;
    BarChart barChart;

    public static SalesMontlyFragment newInstance(String param1, String param2) {
        SalesMontlyFragment fragment = new SalesMontlyFragment();
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
        View view =  inflater.inflate(R.layout.fragment_sales_montly, container, false);
        barChart = view.findViewById(R.id.barChart);
        listThang = new ArrayList<>();
        listThang.add("01");
        listThang.add("02");
        listThang.add("03");
        listThang.add("04");
        listThang.add("05");
        listThang.add("06");
        listThang.add("07");
        listThang.add("08");
        listThang.add("09");
        listThang.add("10");
        listThang.add("11");
        listThang.add("12");

        spinnerThang = (Spinner) view.findViewById(R.id.spinnerThang);
        ArrayAdapter spinnerAdapterThang = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, listThang);
        spinnerThang.setAdapter(spinnerAdapterThang);
        spinnerThang.setSelection(11);

        doanhThuArrayList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
        String tgHienTai = simpleDateFormat.format(Calendar.getInstance().getTime());
        String thangTmp = tgHienTai.substring(6,8);
        String tam = tgHienTai.substring(0,4);
        LoadData("12", "2021");
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doanhThuArrayList = new ArrayList<>();
                LoadData(listThang.get(position), tam);
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
                    LoadData( String.valueOf(month+1), String.valueOf(year));
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

        return view;
    }

    public void LoadData(String thang, String nam){
        simpleAPI = Constants.instance();
        simpleAPI.getDoanhThuTheoThang(thang, nam).enqueue(new Callback<ArrayList<DoanhThu>>() {
            @Override
            public void onResponse(Call<ArrayList<DoanhThu>> call, Response<ArrayList<DoanhThu>> response) {
                doanhThuArrayList = response.body();
                try {
                    ArrayList<BarEntry> entryArrayList = new ArrayList<>();
                    for(int i=0; i<doanhThuArrayList.size(); i++){
                        if(doanhThuArrayList.size()>0){
                            entryArrayList.add(new BarEntry(Integer.parseInt(doanhThuArrayList.get(i).getNgay()), doanhThuArrayList.get(i).getTongTien()));
                            BarDataSet dataSet = new BarDataSet(entryArrayList, "Doanh thu theo tháng"); // add entries to dataset
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
//        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
//        for(int i=0; i<floatArrayList.size(); i++){
//            if(floatArrayList.size()>0){
//                entryArrayList.add(new BarEntry(testArrayList.get(i), floatArrayList.get(i)));
//            }
//            else { //2021-01-11
//                barChart.setNoDataText("Chưa có dữ liệu");
//                barChart.setNoDataTextColor(getResources().getColor(R.color.red));
//            }
//        }

    }
}