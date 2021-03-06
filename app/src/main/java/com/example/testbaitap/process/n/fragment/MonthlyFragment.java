package com.example.testbaitap.process.n.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.example.testbaitap.utils.Config;
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

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefresh;

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

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        sharedPreferences = requireContext().getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        lineChart = view.findViewById(R.id.lineChart);


//        setBackgroundColor(int color): set m??u background bao ph??? to??n b??? Chart, ngo??i ra backgroundColor c?? th??? set trong xml
//        setDescription(String desc): hi???n th??? text m?? t??? s??? xu???t hi???n ??? g??c ph???i d?????i c???a bi???u ?????
//        setDescriptionColor(int color) : set m??u cho text description
//        setDescriptionPosition(float x, float y): custom v??? tr?? cho description text b???ng pixel
//        setDescriptionTypeface(Typeface t): set TypeFace s??? d???ng cho vi???c v??? description text
//        setDescriptionTextSize(float size): set k??ch th?????c Description text b???ng pixel setNoDataText(String text) : Set text s??? xu???t hi???n khi bi???u ????? tr???ng
//        setDrawGridBackground(boolean enabled) : n???u n?? ??c enable th?? h??nh ch??? nh???t n???n ph??a sau v??ng bi???u ????? s??? ???????c v???.
//                setDrawBorders(boolean enabled): Enable/disable chart bolders (c??c ???????ng bao quanh bi???u ?????)
//        setBorderColor(int color) : Set m??u cho chart bolders
//        setBorderWidth(float width): set b??? d???y ???????ng bao quanh bi???u ????? b???ng dp
//        setMaxVisibleValueCount(int count): ?????t s??? l?????ng lable xu???t hi???n t???i ??a tr??n bi???u ?????


        list = new ArrayList<>();
        list.add("Ch??? s??? bmi");
        list.add("C??n n???ng");
        list.add("Chi???u cao");
        list.add("V??ng 1");
        list.add("V??ng 2");
        list.add("V??ng 3");
        list.add("V??ng tay");
        list.add("V??ng ????i");
        list.add("L?????ng n?????c");

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
        LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), "01", tam);
        spinnerThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trangArrayList=new ArrayList<>();
                LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), listThang.get(position), tam);
                mSwipeRefresh.setOnRefreshListener(() -> {
                    LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), listThang.get(position), tam);
                    mSwipeRefresh.setRefreshing(false);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(requireContext(),"Ch??a ch???n", Toast.LENGTH_SHORT).show();
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
                    LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), String.valueOf(month+1), String.valueOf(year));

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
        progressBar.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVTheoThang(maHocVien, thang, nam).enqueue(new Callback<ArrayList<TheTrang>>() {
            @Override
            public void onResponse(Call<ArrayList<TheTrang>> call, Response<ArrayList<TheTrang>> response) {
                try {
                    trangArrayList = response.body();
                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                    for(int i=0; i<trangArrayList.size(); i++){
                        if(trangArrayList.size()>0){
                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getBmi()));
                        }
                        else { //2021-01-11
                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                        }
                    }

                    LineDataSet dataSet = new LineDataSet(entryArrayList, "Ch??? s??? bmi"); // add entries to dataset
                    dataSet.setColor(getResources().getColor(R.color.red));
                    dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                    LineData lineData = new LineData(dataSet);
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                    lineChart.animateX(2000);


                    Description description = new Description();
                    description.setText("M?? t???: Tr???c ?????ng bi???u th??? ch??? s??? bmi, tr???c ngang bi???u th??? c??c th??ng");
                    lineChart.setDescription(description);

                    lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                    progressBar.setVisibility(View.INVISIBLE);
                }
                catch (Exception e){
                    lineChart.setNoDataText("Ch??a c?? d??? li???u");
                    lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                    progressBar.setVisibility(View.INVISIBLE);
                }
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        //?????i s??? postion l?? v??? tr?? ph???n t??? trong list Data
                        try {

                            switch (position){
                                case 0: {
                                    ArrayList<Entry> entryArrayList = new ArrayList<>();
                                    entryArrayList = new ArrayList<>();
                                    for(int i=0; i<trangArrayList.size(); i++){
                                        if(trangArrayList.size()>0){
                                            entryArrayList.add(new Entry(Integer.parseInt(trangArrayList.get(i).getNgay().substring(8,10)), trangArrayList.get(i).getBmi()));

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Ch??? s??? bmi"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? ch??? s??? bmi, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "C??n n???ng"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? c??n n???ng, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "Chi???u cao"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? chi???u cao, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "S??? ??o v??ng 1"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? s??? ??o v??ng 1, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "S??? ??o v??ng 2"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? s??? ??o v??ng 2, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "S??? ??o v??ng 3"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? s??? ??o v??ng 3, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "S??? ??o v??ng tay"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? s??? ??o v??ng tay, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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


                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "S??? ??o v??ng ????i"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? s??? ??o v??ng ????i, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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

                                            LineDataSet dataSet = new LineDataSet(entryArrayList, "L?????ng n?????c"); // add entries to dataset
                                            dataSet.setColor(getResources().getColor(R.color.red));
                                            dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
                                            LineData lineData = new LineData(dataSet);
                                            lineChart.setData(lineData);
                                            lineChart.invalidate(); // refresh
                                            lineChart.animateX(2000);

                                            Description description = new Description();
                                            description.setText("M?? t???: Tr???c ?????ng bi???u th??? l?????ng n?????c, tr???c ngang bi???u th??? c??c ng??y");
                                            lineChart.setDescription(description);

                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
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
                                            //ch??a c?? d??? li???u
                                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
                                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                                        }
                                    }
                                    break;
                                }
                                default: break;
                            }
                        }
                        catch (Exception e ){
                            lineChart.setNoDataText("Ch??a c?? d??? li???u");
                            lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        Toast.makeText(requireContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
                        lineChart.setNoDataText("Ch??a c?? d??? li???u");
                        lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<TheTrang>> call, Throwable t) {
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
                lineChart.setNoDataText("Ch??a c?? d??? li???u");
                lineChart.setNoDataTextColor(getResources().getColor(R.color.red));
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}