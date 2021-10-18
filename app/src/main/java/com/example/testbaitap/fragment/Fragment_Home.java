package com.example.testbaitap.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.testbaitap.MainActivity;
import com.example.testbaitap.R;
import com.example.testbaitap.SlidingAdapter;
import com.example.testbaitap.SlidingModel;
import com.example.testbaitap.activity.WaterActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment_Home extends Fragment {
    private int[] myImageList = new int[]{R.drawable.banner_1, R.mipmap.banner_calculator, R.mipmap.banner_3, R.mipmap.img_reminder, R.mipmap.banner_reminder, };
    private ArrayList<SlidingModel> imageModelArrayList;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RelativeLayout rel_one, rel_two, rel_three;

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
        NUM_PAGES = imageModelArrayList.size();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, false);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);

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
}