package com.example.testbaitap.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.activity.DSKhoaTapActivity;
import com.example.testbaitap.activity.MainActivity;
import com.example.testbaitap.R;
import com.example.testbaitap.SlidingAdapter;
import com.example.testbaitap.SlidingModel;
import com.example.testbaitap.activity.WaterActivity;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.TheTrang;
import com.example.testbaitap.utils.CustomProcessbar;
import com.example.testbaitap.utils.ProgressItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.itangqi.waveloadingview.WaveLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {
    private int[] myImageList = new int[]{R.drawable.banner_1, R.mipmap.banner_calculator, R.mipmap.banner_3, R.mipmap.img_reminder, R.mipmap.banner_reminder };
    private ArrayList<SlidingModel> imageModelArrayList;
    private static ViewPager mPager, viewPagerKhoaTap;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RelativeLayout rel_one, rel_two, rel_three;

    private TextView  txtProgress, tvNhanXetBmiH, tvLuongNuoc, tvProcessTL, tvNgayConLai;
    private Spinner spinner;
    private List<String> list;
    private SimpleAPI simpleAPI;
    ArrayList<TheTrang> trangArrayList;


    private CustomProcessbar customSeekBarH;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;

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

    private WaveLoadingView waterLevelView;
    private ProgressBar progressBarTL;

    public Fragment_Home() {
        // Required empty public constructor
    }
    MainActivity mainAcdsctivity;
    public Fragment_Home(MainActivity mainActivity) {
        this.mainAcdsctivity = mainActivity;
    }
    public static Fragment_Home newInstance(String str, String str2, MainActivity mainActivity) {
        Fragment_Home mainFragment = new Fragment_Home(mainActivity);
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }
//    public static Fragment_Home newInstance(String param1, String param2) {
//        Fragment_Home fragment = new Fragment_Home();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__home, container, false);
        mPager = view.findViewById(R.id.pager);
        viewPagerKhoaTap = view.findViewById(R.id.viewPagerKhoaTap);


        //Thể trạng
        rel_one = (RelativeLayout) view.findViewById(R.id.rel_one);
        rel_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottom().show();
            }
        });

        //Lượng nước uống
        rel_two = (RelativeLayout) view.findViewById(R.id.rel_two);
        rel_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WaterActivity.class);
                startActivity(intent);
            }
        });

        //tập luyện
        rel_three = (RelativeLayout) view.findViewById(R.id.rel_three);
        rel_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAcdsctivity.loadFragmentworkout(new Fragment_Workout());
            }
        });

        //        walk and steps report

        mPager.setAdapter(new SlidingAdapter(getActivity(), imageModelArrayList));
        viewPagerKhoaTap.setAdapter(new SlidingAdapter(getActivity(), imageModelArrayList));
        NUM_PAGES = imageModelArrayList.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, false);
                viewPagerKhoaTap.setCurrentItem(currentPage++, false);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

        txtProgress = view.findViewById(R.id.txtProgress);
        tvNhanXetBmiH = view.findViewById(R.id.tvNhanXetBmiH);
        tvLuongNuoc = view.findViewById(R.id.tvLuongNuoc);
        tvProcessTL = view.findViewById(R.id.tvProcessTL);
        tvNgayConLai = view.findViewById(R.id.tvNgayConLai);
        customSeekBarH = (CustomProcessbar) view.findViewById(R.id.customSeekBarH);
        initDataToSeekbar();
        waterLevelView = (WaveLoadingView) view.findViewById(R.id.waterLevelView);

        LoadDataHomeFragment("quan");

        LinearLayout lnXemNgay = (LinearLayout) view.findViewById(R.id.lnXemNgay);
        lnXemNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DSKhoaTapActivity.class);
                startActivity(intent);
            }
        });

        progressBarTL = (ProgressBar) view.findViewById(R.id.progressTL);
        progressBarTL.setProgress(79);
        tvProcessTL.setText("79"+"%");
        tvNgayConLai.setText("19"+" ngày còn lại");

        return view;
    }
    private ArrayList<SlidingModel> populateList() {
        ArrayList<SlidingModel> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SlidingModel imageModel = new SlidingModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }
    public BottomSheetDialog diaLogBottom() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(getContext(), R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet, null);
        sheetDialog.setContentView(viewDialog);
        BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) viewDialog.getParent());
        mBehavior.setPeekHeight(2000);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        return sheetDialog;
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

        customSeekBarH.initData(progressItemList);
        customSeekBarH.invalidate();
    }

    public void LoadDataHomeFragment(String maHocVien){
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVGanNhat(maHocVien).enqueue(new Callback<TheTrang>() {
            @Override
            public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                theTrang = response.body();
                try {
                    float bmi = theTrang.getBmi();
                    customSeekBarH.setProgress((int)(bmi));
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
                    tvNhanXetBmiH.setText(txt);

                    float ln = theTrang.getLuongNuoc()/3000*100;
                    waterLevelView.setCenterTitle("36%");
                    waterLevelView.setProgressValue(36);
//                    waterLevelView.setProgressValue((int)(ln));
//                    waterLevelView.setCenterTitle(String.valueOf((int) (ln))+ "%");
                    String lnHT = String.format("%.0f", theTrang.getLuongNuoc());
                    tvLuongNuoc.setText(lnHT+" ml");
                }
                catch (Exception e ){
                    customSeekBarH.setProgress(0);
                    txtProgress.setText("0");
                    waterLevelView.setProgressValue(0);
                    tvLuongNuoc.setText("Lượng nước uống: 0 ml");
                    tvNhanXetBmiH.setText("Chưa có số liệu vào ngày này!");
                }
            }

            @Override
            public void onFailure(Call<TheTrang> call, Throwable t) {
                Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
                customSeekBarH.setProgress(0);
                txtProgress.setText("0");
                tvNhanXetBmiH.setText("Chưa có số liệu vào ngày này!");
                waterLevelView.setProgressValue(0);
                tvLuongNuoc.setText("Lượng nước uống: 0 ml");
            }
        });
    }
}