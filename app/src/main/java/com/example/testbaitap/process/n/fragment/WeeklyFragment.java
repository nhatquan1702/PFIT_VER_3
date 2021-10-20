package com.example.testbaitap.process.n.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.example.testbaitap.R;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

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


public class WeeklyFragment extends Fragment {

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
        LineChart lineChart = view.findViewById(R.id.lineChart);
        ArrayList<Entry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(2011, 60));
        entryArrayList.add(new Entry(2012, 50));
        entryArrayList.add(new Entry(2013, 30));
        entryArrayList.add(new Entry(2015, 40));
        entryArrayList.add(new Entry(2016, 20));
        entryArrayList.add(new Entry(2019, 60));
        entryArrayList.add(new Entry(2020, 45));
        entryArrayList.add(new Entry(2021, 35));
        entryArrayList.add(new Entry(2022, 25));

//        for (YourData data : dataObjects) {
//            entryArrayList.add(new Entry(data.getValueX(), data.getValueY()));
//        }
        LineDataSet dataSet = new LineDataSet(entryArrayList, "Cân nặng"); // add entries to dataset
        dataSet.setColor(getResources().getColor(R.color.red));
        dataSet.setValueTextColor(getResources().getColor(R.color.dot_dark_screen)); // styling, ...
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh


        Description description = new Description();
        description.setText("Mô tả: Trục đứng biểu thị cân nặng, trục ngang biểu thị các năm");
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
        return view;
    }
    public class My implements IAxisValueFormatter{

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return null;
        }
    }
}