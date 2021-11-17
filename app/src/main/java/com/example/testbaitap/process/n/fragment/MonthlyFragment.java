package com.example.testbaitap.process.n.fragment;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MonthlyFragment extends Fragment {
    private TextView tvTitleTenTheTrang;
    private Spinner spinner;
    private List<String> list;
    private SimpleAPI simpleAPI;
    ArrayList<TheTrang> trangArrayList;

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


        list = new ArrayList<>();
        list.add("Bmi");
        list.add("Cân nặng");
        list.add("Chiều cao");
        list.add("Vòng 1");
        list.add("Vòng 2");
        list.add("Vòng 3");
        list.add("Vòng tay");
        list.add("Vòng đùi");
        list.add("Lượng nước");
        tvTitleTenTheTrang = (TextView) view.findViewById(R.id.tvTitleTenTheTrang);
        tvTitleTenTheTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(requireContext(),(view, year, month, dayOfMonth) ->
                {
                    String strDate = "'" +year + "-" + (month + 1) + "-" + dayOfMonth + "'";
                    tvTitleTenTheTrang.setText("Ngày " + dayOfMonth + " Tháng " + (month + 1) + " Năm " + year);
                    Toast.makeText(requireContext(), strDate, Toast.LENGTH_SHORT).show();

                },now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        spinner = (Spinner) view.findViewById(R.id.imgButtonTenTheTrang);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //đối số postion là vị trí phần tử trong list Data
                String msg = "position :" + position + " value :" + list.get(position);
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(requireContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
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

}