package com.example.testbaitap.process.n.fragment;

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


public class MonthlyFragment extends Fragment {
    private Spinner spinner, spinnerThang ;
    private List<String> list, listThang;
    private SimpleAPI simpleAPI;
    ArrayList<TheTrang> trangArrayList;
    LineChart lineChart;

    public MonthlyFragment() {
        // Required empty public constructor
    }

    public static MonthlyFragment newInstance() {
        MonthlyFragment fragment = new MonthlyFragment();
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
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);

        lineChart = view.findViewById(R.id.lineChart);


//        setBackgroundColor(int color): set màu background bao phủ toàn bộ Chart, ngoài ra backgroundColor có thể set trong xml
//        setDescription(String desc): hiển thị text mô tả sẽ xuẩt hiện ở góc phải dưới của biểu đồ
//        setDescriptionColor(int color) : set màu cho text description
//        setDescriptionPosition(float x, float y): custom vị trí cho description text bằng pixel
//        setDescriptionTypeface(Typeface t): set TypeFace sử dụng cho việc vẽ description text
//        setDescriptionTextSize(float size): set kích thước Description text bằng pixel setNoDataText(String text) : Set text sẽ xuất hiện khi biều đồ trống
//        setDrawGridBackground(boolean enabled) : nếu nó đc enable thì hình chữ nhật nền phía sau vùng biểu đồ sẽ được vẽ.
//                setDrawBorders(boolean enabled): Enable/disable chart bolders (các đường bao quanh biểu đồ)
//        setBorderColor(int color) : Set màu cho chart bolders
//        setBorderWidth(float width): set bề dầy đường bao quanh biểu đồ bằng dp
//        setMaxVisibleValueCount(int count): đặt số lượng lable xuẩt hiện tối đa trên biểu đồ


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


        spinner = (Spinner) view.findViewById(R.id.imgButtonTenTheTrang);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);

        spinnerThang = (Spinner) view.findViewById(R.id.spinnerThang);
        ArrayAdapter spinnerAdapterThang = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, listThang);
        spinnerThang.setAdapter(spinnerAdapterThang);
        spinnerThang.setSelection(0);

        trangArrayList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
        String tgHienTai = simpleDateFormat.format(Calendar.getInstance().getTime());
        String tam = tgHienTai.substring(0,4);
        LoadData("quan", "01", tam);
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trangArrayList=new ArrayList<>();
                LoadData("quan", listThang.get(position), tam);
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
                    trangArrayList = new ArrayList<>();
                    //Log.d("quan", String.valueOf(month+1)+"+"+String.valueOf(year));
                    LoadData("quan", String.valueOf(month+1), String.valueOf(year));
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
    public class My implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return null;
        }
    }

    public void LoadData(String maHocVien, String thang, String nam){
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVTheoThang(maHocVien, thang, nam).enqueue(new Callback<ArrayList<TheTrang>>() {
            @Override
            public void onResponse(Call<ArrayList<TheTrang>> call, Response<ArrayList<TheTrang>> response) {
                trangArrayList = response.body();
                try {
                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                    for(int i=0; i<trangArrayList.size(); i++){
                        if(trangArrayList.size()>0){
                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getBmi()));
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getBmi()));

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Chỉ số bmi"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị chỉ số bmi, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getCanNang()));

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Cân nặng"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị cân nặng, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getChieuCao()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Chiều cao"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị chiều cao, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getVong1()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng 1"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng 1, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getVong2()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng 2"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng 2, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getVong3()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng 3"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng 3, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getVongTay()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng tay"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng tay, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getVongDui()));


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Số đo vòng đùi"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị số đo vòng đùi, trục ngang biểu thị các ngày");
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
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getLuongNuoc()));

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Lượng nước"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh


                                            Description description = new Description();
                                            description.setText("Mô tả: Trục đứng biểu thị lượng nước, trục ngang biểu thị các ngày");
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
                lineChart.setNoDataText("Chưa có dữ liệu");
                lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
            }
        });
    }
}