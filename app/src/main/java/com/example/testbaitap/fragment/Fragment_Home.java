package com.example.testbaitap.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.activity.ListTCActivity;
import com.example.testbaitap.activity.MainActivity;
import com.example.testbaitap.R;
import com.example.testbaitap.SlidingAdapter;
import com.example.testbaitap.SlidingModel;
import com.example.testbaitap.activity.WaterActivity;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.entity.TheTrang;
import com.example.testbaitap.utils.Config;
import com.example.testbaitap.utils.CustomProcessbar;
import com.example.testbaitap.utils.ProgressItem;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import me.itangqi.waveloadingview.WaveLoadingView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Home extends Fragment {
    private final int[] myImageList = new int[]{R.mipmap.banner_reminder, R.mipmap.banner_calculator, R.mipmap.banner_3, R.mipmap.img_reminder, R.mipmap.banner_1};
    private ArrayList<SlidingModel> imageModelArrayList;
    private static ViewPager mPager, viewPagerKhoaTap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RelativeLayout rel_one, rel_two, rel_three;

    private TextView txtProgress;
    private TextView tvNhanXetBmiH;
    private TextView tvLuongNuoc;
    private Spinner spinner;
    private List<String> list;
    private SimpleAPI simpleAPI;
    ArrayList<TheTrang> trangArrayList;
    private boolean checkUpdate;
    private ProgressBar progressBarTL;
    TextView tvProcessTL;


    private CustomProcessbar customSeekBarH;

    private final float smoking = 5;
    private float red;
    private TheTrang theTrang;

    private WaveLoadingView waterLevelView;
    private RelativeLayout relHomeFr;


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
        relHomeFr = view.findViewById(R.id.relHomeFr);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefresh = view.findViewById(R.id.swipe_refresh);
        sharedPreferences = requireContext().getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();
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
                Intent intent = new Intent(getActivity().getBaseContext(), WaterActivity.class);
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
        TextView tvNgayConLai = view.findViewById(R.id.tvNgayConLai);
        customSeekBarH = (CustomProcessbar) view.findViewById(R.id.customSeekBarH);
        initDataToSeekbar();
        waterLevelView = (WaveLoadingView) view.findViewById(R.id.waterLevelView);

        LoadDataHomeFragment(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""));

        LinearLayout lnXemNgay = (LinearLayout) view.findViewById(R.id.lnXemNgay);
        lnXemNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTCActivity.class);
                startActivity(intent);
            }
        });

        progressBarTL = (ProgressBar) view.findViewById(R.id.progressTL);
        LoadPTBT(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""));
        tvNgayConLai.setText("");
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        mSwipeRefresh.setOnRefreshListener(() -> {
            LoadTheTrang(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), currentDate);
            LoadDataHomeFragment(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""));
            LoadPTBT(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""));
            mSwipeRefresh.setRefreshing(false);
        });

        return view;
    }

    public void LoadPTBT(String maHV){
        progressBar.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getKhachHang(maHV).enqueue(new Callback<HocVien>() {
            @Override
            public void onResponse(Call<HocVien> call, Response<HocVien> response) {
                try {
                    HocVien hocVien = response.body();
                    progressBar.setVisibility(View.VISIBLE);
                    simpleAPI.getPhanTramBTTheoKhoa(hocVien.getMaKhoaTap(), hocVien.getMaHocVien()).enqueue(new Callback<Status>() {
                        @Override
                        public void onResponse(Call<Status> call, Response<Status> response) {
                            try {
                                Status status = response.body();
                                if (status.getStatus()<0){
                                    progressBarTL.setProgress(0);
                                    tvProcessTL.setText("0" + "%");
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                                else {
                                    progressBarTL.setProgress(status.getStatus());
                                    tvProcessTL.setText(String.valueOf(status.getStatus()) + "%");
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                progressBarTL.setProgress(0);
                                tvProcessTL.setText("0" + "%");
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Status> call, Throwable t) {
                            progressBarTL.setProgress(0);
                            tvProcessTL.setText("0" + "%");
                            Log.d("quan", t.toString());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }
                catch (Exception e){
                    e.printStackTrace();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<HocVien> call, Throwable t) {
                Log.d("quan", t.toString());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
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
        EditText tvCanNang;
        EditText tvChieuCao;
        EditText tvVongTay;
        EditText tvVongDui;
        EditText tvV1;
        EditText tvV2;
        EditText tvV3;
        tvCanNang = (EditText) viewDialog.findViewById(R.id.edtCN);
        tvChieuCao = (EditText) viewDialog.findViewById(R.id.edtCC);
        tvVongTay = (EditText) viewDialog.findViewById(R.id.edtVT);
        tvVongDui = (EditText) viewDialog.findViewById(R.id.edtVD);
        tvV1 = (EditText) viewDialog.findViewById(R.id.edtV1);
        tvV2 = (EditText) viewDialog.findViewById(R.id.edtV2);
        tvV3 = (EditText) viewDialog.findViewById(R.id.edtV3);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        progressBar.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVTheoNgay(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), currentDate).enqueue(new Callback<TheTrang>() {
            @Override
            public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                try {
                    TheTrang tt = response.body();
                    tvCanNang.setText(String.valueOf(tt.getCanNang()));
                    tvChieuCao.setText(String.valueOf(tt.getChieuCao()));
                    tvVongTay.setText(String.valueOf(tt.getVongTay()));
                    tvVongDui.setText(String.valueOf(tt.getVongDui()));
                    tvV1.setText(String.valueOf(tt.getVong1()));
                    tvV2.setText(String.valueOf(tt.getVong2()));
                    tvV3.setText(String.valueOf(tt.getVong3()));
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    tvCanNang.setText("");
                    tvChieuCao.setText("");
                    tvVongTay.setText("");
                    tvVongDui.setText("");
                    tvV1.setText("");
                    tvV2.setText("");
                    tvV3.setText("");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TheTrang> call, Throwable t) {
                //Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("quan", t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });

        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cn = tvCanNang.getText().toString().trim();
                String cc = tvChieuCao.getText().toString().trim();
                String vt = tvVongTay.getText().toString().trim();
                String vd = tvVongDui.getText().toString().trim();
                String v1 = tvV1.getText().toString().trim();
                String v2 = tvV2.getText().toString().trim();
                String v3 = tvV3.getText().toString().trim();
                String ln = "0";

                boolean check = true;
                if (cn.isEmpty()) {
                    tvCanNang.setError("Cân nặng không được bỏ trống!");
                    check = false;
                }
                else if (Float.parseFloat(cn)>200 ||  Float.parseFloat(cn)<0) {
                    tvCanNang.setError("Cân nặng không hợp lệ!");
                    check = false;
                }

                if (cc.isEmpty()) {
                    tvChieuCao.setError("Chiều cao không được bỏ trống!");
                    check = false;
                }

                else if (Float.parseFloat(cc)>200 ||  Float.parseFloat(cc)<0) {
                    tvChieuCao.setError("Chiều cao không hợp lệ!");
                    check = false;
                }

                if (vt.isEmpty()) {
                    tvVongTay.setError("Vòng tay không được bỏ trống!");
                    check = false;
                }

                else if (Float.parseFloat(vt)>50 ||  Float.parseFloat(vt)<0) {
                    tvVongTay.setError("Số đo vòng tay không hợp lệ!");
                    check = false;
                }

                if (vd.isEmpty()) {
                    tvVongDui.setError("Vòng đùi không được bỏ trống!");
                    check = false;
                }

                else if (Float.parseFloat(vd)>100 ||  Float.parseFloat(vd)<0) {
                    tvVongDui.setError("Số đo vòng đùi không hợp lệ!");
                    check = false;
                }

                if (v1.isEmpty()) {
                    tvV1.setError("Vòng 1 không được bỏ trống!");
                    check = false;
                }

                else if (Float.parseFloat(v1)>200 ||  Float.parseFloat(v1)<0) {
                    tvV1.setError("Số đo vòng 1 không hợp lệ!");
                    check = false;
                }

                if (v2.isEmpty()) {
                    tvV2.setError("Vòng 2 không được bỏ trống!");
                    check = false;
                }

                else if (Float.parseFloat(v2)>200 ||  Float.parseFloat(v2)<0) {
                    tvV2.setError("Số đo vòng 2 không hợp lệ!");
                    check = false;
                }

                if (v3.isEmpty()) {
                    tvV3.setError("Vòng 3 không được bỏ trống!");
                    check = false;
                }

                else if (Float.parseFloat(v3)>200 ||  Float.parseFloat(v3)<0) {
                    tvV3.setError("Số đo vòng 3 không hợp lệ!");
                    check = false;
                }

                if (check) {
                    simpleAPI = Constants.instance();
                    simpleAPI.getTheTrangHVTheoNgay(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), currentDate).enqueue(new Callback<TheTrang>() {
                        @Override
                        public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                            try {
                                theTrang = response.body();
                                float bmi = theTrang.getBmi();
                                if (bmi != 0 || !String.valueOf(theTrang.getBmi()).isEmpty()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                    builder.setTitle("Thể trạng đã cập nhật rồi");
                                    builder.setMessage("Bạn có muốn chỉnh sửa?");
                                    builder.setPositiveButton("Có", (dialog, which) ->
                                            updateTT(sheetDialog,
                                                    currentDate,
                                                    sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""),
                                                    Float.valueOf(cc),
                                                    Float.valueOf(cn),
                                                    Float.valueOf(v1),
                                                    Float.valueOf(v2),
                                                    Float.valueOf(v3),
                                                    Float.valueOf(vt),
                                                    Float.valueOf(vd),
                                                    Float.valueOf(ln)));

                                    builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                } else {
                                    insertTT(sheetDialog, currentDate, sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), Float.valueOf(cc), Float.valueOf(cn), Float.valueOf(v1), Float.valueOf(v2), Float.valueOf(v3), Float.valueOf(vt), Float.valueOf(vd), Float.valueOf(ln));
                                }
                            } catch (Exception e) {
                                insertTT(sheetDialog, currentDate, sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), Float.valueOf(cc), Float.valueOf(cn), Float.valueOf(v1), Float.valueOf(v2), Float.valueOf(v3), Float.valueOf(vt), Float.valueOf(vd), Float.valueOf(ln));
                            }
                        }

                        @Override
                        public void onFailure(Call<TheTrang> call, Throwable t) {
                            insertTT(sheetDialog, currentDate, sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), Float.valueOf(cc), Float.valueOf(cn), Float.valueOf(v1), Float.valueOf(v2), Float.valueOf(v3), Float.valueOf(vt), Float.valueOf(vd), Float.valueOf(ln));
                        }
                    });
                }
            }
        });
        return sheetDialog;
    }

    public void LoadTheTrang(String maHV, String ngayHT) {

    }

    public void insertTT(BottomSheetDialog sheetDialog, String currentDate, String mahocvien, Float cc, Float cn, Float v1, Float v2, Float v3, Float vt, Float vd, Float ln) {
        TheTrang theTrang = new TheTrang(mahocvien, currentDate, Float.valueOf(cc), Float.valueOf(cn), Float.valueOf(v1), Float.valueOf(v2), Float.valueOf(v3), Float.valueOf(vt), Float.valueOf(vd), Float.valueOf(ln));
        simpleAPI = Constants.instance();
        simpleAPI.insertTheTrang(theTrang).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if (status.getStatus() == 2) {
                        Snackbar snackbar = Snackbar.make(relHomeFr, "Thể trạng đã cập nhật rồi!", Snackbar.LENGTH_LONG);
                        snackbar.show();
//                        Toast.makeText(requireContext(), "Thể trạng đã cập nhật rồi!", Toast.LENGTH_SHORT).show();
                    }
                    if (status.getStatus() == 1) {
                        sheetDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(relHomeFr, "Thể trạng cập nhật thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
//                        Toast.makeText(requireContext(), "Thể trạng cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    }
                    if (status.getStatus() == 0) {
                        Snackbar snackbar = Snackbar.make(relHomeFr, "Thể trạng cập nhật không thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
//                        Toast.makeText(requireContext(), "Thể trạng cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    sheetDialog.dismiss();
                    //Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                sheetDialog.dismiss();
                //Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateTT(BottomSheetDialog sheetDialog, String currentDate, String mahocvien, Float cc, Float cn, Float v1, Float v2, Float v3, Float vt, Float vd, Float ln) {
        TheTrang theTrang = new TheTrang(mahocvien, currentDate, Float.valueOf(cc), Float.valueOf(cn), Float.valueOf(v1), Float.valueOf(v2), Float.valueOf(v3), Float.valueOf(vt), Float.valueOf(vd), Float.valueOf(ln));
        simpleAPI = Constants.instance();
        simpleAPI.updateTheTrang(theTrang).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if (status.getStatus() == 2) {
                        Snackbar snackbar = Snackbar.make(relHomeFr, "Bạn chưa cập nhật thể trạng hôm nay!", Snackbar.LENGTH_LONG);
                        snackbar.show();
//                        Toast.makeText(requireContext(), "Bạn chưa cập nhật thể trạng hôm nay!", Toast.LENGTH_SHORT).show();
                    }
                    if (status.getStatus() == 1) {
                        sheetDialog.dismiss();
                        Snackbar snackbar = Snackbar.make(relHomeFr, "Thể trạng cập nhật thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
//                        Toast.makeText(requireContext(), "Thể trạng cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    }
                    if (status.getStatus() == 0) {
                        Snackbar snackbar = Snackbar.make(relHomeFr, "Thể trạng cập nhật không thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
//                        Toast.makeText(requireContext(), "Thể trạng cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    sheetDialog.dismiss();
                    //Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                sheetDialog.dismiss();
                //Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataToSeekbar() {
        ArrayList<ProgressItem> progressItemList = new ArrayList<ProgressItem>();
        // red span
        ProgressItem mProgressItem = new ProgressItem();
        float totalSpan = 100;
        float teal700 = 16;
        mProgressItem.progressItemPercentage = ((teal700 / totalSpan) * 100);
        mProgressItem.color = R.color.teal_200;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        float teal200 = 3;
        mProgressItem.progressItemPercentage = (teal200 / totalSpan) * 100;
        mProgressItem.color = R.color.teal_700;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        float greenlight = 6;
        mProgressItem.progressItemPercentage = (greenlight / totalSpan) * 100;
        mProgressItem.color = android.R.color.holo_green_dark;
        progressItemList.add(mProgressItem);
        // purple span
        mProgressItem = new ProgressItem();
        float greendark = 5;
        mProgressItem.progressItemPercentage = (greendark / totalSpan) * 100;
        mProgressItem.color = android.R.color.holo_green_light;
        progressItemList.add(mProgressItem);
        mProgressItem = new ProgressItem();
        float oglight = 5;
        mProgressItem.progressItemPercentage = (oglight / totalSpan) * 100;
        mProgressItem.color = android.R.color.holo_orange_light;
        progressItemList.add(mProgressItem);
        // greyspan
        mProgressItem = new ProgressItem();
        float ogdark = 5;
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

    public void LoadDataHomeFragment(String maHocVien) {
        progressBar.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getTheTrangHVGanNhat(maHocVien).enqueue(new Callback<TheTrang>() {
            @Override
            public void onResponse(Call<TheTrang> call, Response<TheTrang> response) {
                theTrang = response.body();
                try {
                    float bmi = theTrang.getBmi();
                    customSeekBarH.setProgress((int) (bmi));
                    String strDouble = String.format("%.2f", bmi);
                    txtProgress.setText(strDouble + "kg/m2");
                    String txt = "";
                    if (bmi < 16) {
                        txt = "Trông bạn quá gầy";
                    }
                    if (bmi < 18 && bmi > 16 || bmi == 16) {
                        txt = "Trông bạn hơi gầy";
                    }
                    if (bmi > 18 && bmi < 25 || bmi == 18) {
                        txt = "Thể trạng bình thường";
                    }
                    if (bmi > 25 && bmi < 30 || bmi == 25) {
                        txt = "Trông bạn hơi béo";
                    }
                    if (bmi > 30 && bmi < 35 || bmi == 30) {
                        txt = "Trông bạn quá béo";
                    }
                    if (bmi > 35 && bmi < 40 || bmi == 35) {
                        txt = "Trông bạn rất béo";
                    }
                    if (bmi > 40 || bmi == 40) {
                        txt = "Thể trạng nguy hiểm";
                    }
                    tvNhanXetBmiH.setText(txt);

                    float ln = theTrang.getLuongNuoc() / 3500 * 100;
                    String lnF = String.format("%.0f", ln);
                    waterLevelView.setCenterTitle(String.valueOf(lnF)+"%");
                    waterLevelView.setProgressValue((int)(ln));
//                    waterLevelView.setProgressValue((int)(ln));
//                    waterLevelView.setCenterTitle(String.valueOf((int) (ln))+ "%");
                    String lnHT = String.format("%.0f", theTrang.getLuongNuoc());
                    tvLuongNuoc.setText(lnHT + " ml");
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    customSeekBarH.setProgress(0);
                    txtProgress.setText("0");
                    waterLevelView.setProgressValue(0);
                    tvLuongNuoc.setText("0 ml");
                    tvNhanXetBmiH.setText("Chưa có số liệu vào ngày này!");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TheTrang> call, Throwable t) {
                //Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show();
                customSeekBarH.setProgress(0);
                txtProgress.setText("0");
                tvNhanXetBmiH.setText("Chưa có số liệu vào ngày này!");
                waterLevelView.setProgressValue(0);
                tvLuongNuoc.setText("0 ml");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void CheckUpdateTT(String maHocVien, String ngay) {

    }
}