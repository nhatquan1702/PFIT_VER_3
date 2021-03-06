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
import com.example.testbaitap.utils.CustomProcessbar;
import com.example.testbaitap.utils.ProgressItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompareFragment extends Fragment {
    private TextView tvTitleTenTheTrang, tvNhanXetBmi, tvTTDaChon, tvTTHienTai;
    private Spinner spinner;
    private List<String> list;
    private SimpleAPI simpleAPI;
    private TheTrang theTrangDC, theTrangHT;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ProgressBar progressBar;

    public CompareFragment() {
        // Required empty public constructor
    }

    public static CompareFragment newInstance() {
        CompareFragment fragment = new CompareFragment();
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
        View view = inflater.inflate(R.layout.fragment_compare, container, false);

        progressBar = view.findViewById(R.id.progress_barSss);
        sharedPreferences = requireContext().getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        list = new ArrayList<>();
        list.add("C??n n???ng");
        list.add("Chi???u cao");
        list.add("V??ng 1");
        list.add("V??ng 2");
        list.add("V??ng 3");
        list.add("V??ng tay");
        list.add("V??ng ????i");

        tvTitleTenTheTrang = (TextView) view.findViewById(R.id.tvTitleTenTheTrang);
        tvTTDaChon = (TextView) view.findViewById(R.id.TTDaChon);
        tvTTHienTai = (TextView) view.findViewById(R.id.TTHienTai);
        tvNhanXetBmi = (TextView) view.findViewById(R.id.tvNhanXetBmi);

        spinner = (Spinner) view.findViewById(R.id.imgButtonTenTheTrang);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0);
        tvTitleTenTheTrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePicker = new DatePickerDialog(requireContext(),(view, year, month, dayOfMonth) ->
                {
                    theTrangDC = new TheTrang();
                    theTrangHT = new TheTrang();
                    String strDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                    tvTitleTenTheTrang.setText("Ng??y " + dayOfMonth + " Th??ng " + (month + 1) + " N??m " + year);
                    LoadData(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), strDate);
                },now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });

        return view;
    }

    public void LoadData(String maHocVien, String date){
        theTrangDC = new TheTrang();
        theTrangHT = new TheTrang();
        progressBar.setVisibility(View.VISIBLE);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVTheoNgay(maHocVien, currentDate).enqueue(new Callback<TheTrang>() {
            @Override
            public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                try {
                    theTrangHT = response.body();
                    simpleAPI = Constants.instance();
                    simpleAPI.getTheTrangHVTheoNgay(maHocVien, date).enqueue(new Callback<TheTrang>() {
                        @Override
                        public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                            try {
                                theTrangDC = response.body();
                                float HSCanNang = theTrangHT.getCanNang()-theTrangDC.getCanNang();
                                String strDouble1 = String.format("%.2f", HSCanNang);
                                tvTTDaChon.setText("C??n n???ng ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getCanNang())+ " kg");
                                tvTTHienTai.setText("C??n n???ng hi???n t???i: "+String.format("%.2f", theTrangHT.getCanNang())+ " kg");

                                if(HSCanNang > 0){
                                    tvNhanXetBmi.setText("C??n n???ng c???a b???n ???? t??ng "+strDouble1+" kg");
                                }
                                if(HSCanNang == 0){
                                    tvNhanXetBmi.setText("C??n n???ng c???a b???n kh??ng thay ?????i!");
                                }
                                if(HSCanNang < 0){
                                    tvNhanXetBmi.setText("C??n n???ng c???a b???n ???? gi???m "+strDouble1.substring(1)+" kg");
                                }
                                spinner.setSelection(0);
                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                        //?????i s??? postion l?? v??? tr?? ph???n t??? trong list Data
                                        try {
                                            float HSCanNang = theTrangHT.getCanNang()-theTrangDC.getCanNang();
                                            float HSChieuCao = theTrangHT.getChieuCao()-theTrangDC.getChieuCao();
                                            float HSV1 = theTrangHT.getVong1()-theTrangDC.getVong1();
                                            float HSV2 = theTrangHT.getVong2()-theTrangDC.getVong2();
                                            float HSV3 = theTrangHT.getVong3()-theTrangDC.getVong3();
                                            float HSVT= theTrangHT.getVongTay()-theTrangDC.getVongTay();
                                            float HSVD = theTrangHT.getVongDui()-theTrangDC.getVongDui();
                                            String strDouble1 = String.format("%.2f", HSCanNang);
                                            String strDouble2 = String.format("%.2f", HSChieuCao);
                                            String strDouble3 = String.format("%.2f", HSV1);
                                            String strDouble4 = String.format("%.2f", HSV2);
                                            String strDouble5 = String.format("%.2f", HSV3);
                                            String strDouble6 = String.format("%.2f", HSVT);
                                            String strDouble7 = String.format("%.2f", HSVD);
                                            switch (position){
                                                case 0: {
                                                    tvTTDaChon.setText("C??n n???ng ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getCanNang())+ " kg");
                                                    tvTTHienTai.setText("C??n n???ng hi???n t???i: "+String.format("%.2f", theTrangHT.getCanNang())+ " kg");

                                                    if(HSCanNang > 0){
                                                        tvNhanXetBmi.setText("C??n n???ng c???a b???n ???? t??ng "+strDouble1+" kg");
                                                    }
                                                    if(HSCanNang == 0){
                                                        tvNhanXetBmi.setText("C??n n???ng c???a b???n kh??ng thay ?????i!");
                                                    }
                                                    if(HSCanNang < 0){
                                                        tvNhanXetBmi.setText("C??n n???ng c???a b???n ???? gi???m "+strDouble1.substring(1)+" kg");
                                                    }
                                                    break;
                                                }
                                                case 1: {
                                                    tvTTDaChon.setText("Chi???u cao ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getChieuCao())+ " cm");
                                                    tvTTHienTai.setText("Chi???u cao hi???n t???i: "+String.format("%.2f", theTrangHT.getChieuCao())+ " cm");

                                                    if(HSChieuCao > 0){
                                                        tvNhanXetBmi.setText("Chi???u cao c???a b???n ???? t??ng "+strDouble2+" cm");
                                                    }
                                                    if(HSChieuCao == 0){
                                                        tvNhanXetBmi.setText("Chi???u cao c???a b???n kh??ng thay ?????i!");
                                                    }
                                                    if(HSChieuCao < 0){
                                                        tvNhanXetBmi.setText("Chi???u cao c???a b???n ???? gi???m "+strDouble2.substring(1)+" cm");
                                                    }
                                                    break;
                                                }
                                                case 2: {
                                                    tvTTDaChon.setText("S??? ??o v??ng 1 ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getVong1())+ " cm");
                                                    tvTTHienTai.setText("S??? ??o v??ng 1 hi???n t???i: "+String.format("%.2f", theTrangHT.getVong1())+ " cm");

                                                    if(HSV1 > 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 1 c???a b???n ???? t??ng "+strDouble3+" cm");
                                                    }
                                                    if(HSV1 == 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 1 c???a b???n kh??ng thay ?????i!");
                                                    }
                                                    if(HSV1 < 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 1 c???a b???n ???? gi???m "+strDouble3.substring(1)+" cm");
                                                    }
                                                    break;
                                                }
                                                case 3: {
                                                    tvTTDaChon.setText("S??? ??o v??ng 2 ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getVong2())+ " cm");
                                                    tvTTHienTai.setText("S??? ??o v??ng 2 hi???n t???i: "+String.format("%.2f", theTrangHT.getVong2())+ " cm");

                                                    if(HSV2 > 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 2 c???a b???n ???? t??ng "+strDouble4+" cm");
                                                    }
                                                    if(HSV2 == 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 2 c???a b???n kh??ng thay ?????i!");
                                                    }
                                                    if(HSV2 < 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 2 c???a b???n ???? gi???m "+strDouble4.substring(1)+" cm");
                                                    }
                                                    break;
                                                }
                                                case 4: {
                                                    tvTTDaChon.setText("S??? ??o v??ng 3 ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getVong3())+ " cm");
                                                    tvTTHienTai.setText("S??? ??o v??ng 3 hi???n t???i: "+String.format("%.2f", theTrangHT.getVong3())+ " cm");

                                                    if(HSV3 > 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 3 c???a b???n ???? t??ng "+strDouble5+" cm");
                                                    }
                                                    if(HSV3 == 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 3 c???a b???n kh??ng thay ?????i!");
                                                    }
                                                    if(HSV3 < 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng 3 c???a b???n ???? gi???m "+strDouble5.substring(1)+" cm");
                                                    }
                                                    break;
                                                }
                                                case 5: {
                                                    tvTTDaChon.setText("S??? ??o v??ng tay ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getVongTay())+ " cm");
                                                    tvTTHienTai.setText("S??? ??o v??ng tay hi???n t???i: "+String.format("%.2f", theTrangHT.getVongTay())+ " cm");

                                                    if(HSVT > 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng tay c???a b???n ???? t??ng "+strDouble6+" cm");
                                                    }
                                                    if(HSVT == 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng tay c???a b???n kh??ng thay ?????i!");
                                                    }
                                                    if(HSVT < 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng tay c???a b???n ???? gi???m "+strDouble6.substring(1)+" cm");
                                                    }
                                                    break;
                                                }
                                                case 6: {
                                                    tvTTDaChon.setText("S??? ??o v??ng ????i ng??y ???? ch???n: "+String.format("%.2f", theTrangDC.getVongDui())+ " cm");
                                                    tvTTHienTai.setText("S??? ??o v??ng ????i hi???n t???i: "+String.format("%.2f", theTrangHT.getVongDui())+ " cm");

                                                    if(HSVD > 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng ????i c???a b???n ???? t??ng "+strDouble7+" cm");
                                                    }
                                                    if(HSVD == 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng ????i c???a b???n kh??ng thay ?????i!");
                                                    }
                                                    if(HSVD < 0){
                                                        tvNhanXetBmi.setText("S??? ??o v??ng ????i c???a b???n ???? gi???m "+strDouble7.substring(1)+" cm");
                                                    }
                                                    break;
                                                }
                                                default: break;
                                            }
                                        }
                                        catch (Exception e ){
                                            tvTTDaChon.setText("Ng??y ???? ch???n: ");
                                            tvTTHienTai.setText("Ng??y ???? ch???n: ");
                                            tvNhanXetBmi.setText("Ch??a c?? s??? li???u v??o ng??y ???? ch???n!");
                                        }
                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                        Toast.makeText(requireContext(), "onNothingSelected", Toast.LENGTH_SHORT).show();
                                    }

                                });
                                progressBar.setVisibility(View.GONE);

                            }
                            catch (Exception e ){
                                tvTTDaChon.setText("Ng??y ???? ch???n: ");
                                tvTTHienTai.setText("Ng??y ???? ch???n: ");
                                tvNhanXetBmi.setText("Ch??a c?? s??? li???u v??o ng??y ???? ch???n!");
                                progressBar.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onFailure(Call<TheTrang> call, Throwable t) {
                            tvTTDaChon.setText("Ng??y ???? ch???n: ");
                            tvTTHienTai.setText("Ng??y ???? ch???n: ");
                            tvNhanXetBmi.setText("Ch??a c?? s??? li???u v??o ng??y ???? ch???n!");
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                    tvTTDaChon.setText("Ng??y ???? ch???n: ");
                    tvTTHienTai.setText("Ng??y ???? ch???n: ");
                    tvNhanXetBmi.setText("Ch??a c?? s??? li???u v??o ng??y h??m nay!");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TheTrang> call, Throwable t) {
                Log.d("quan", t.toString());
                tvTTDaChon.setText("Ng??y ???? ch???n: ");
                tvTTHienTai.setText("Ng??y ???? ch???n: ");
                tvNhanXetBmi.setText("Ch??a c?? s??? li???u v??o ng??y h??m nay!");
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}