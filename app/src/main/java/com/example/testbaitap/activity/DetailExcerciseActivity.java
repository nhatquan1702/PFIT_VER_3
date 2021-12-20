package com.example.testbaitap.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.BaiTapFull;
import com.example.testbaitap.entity.ChiTietBaiTap;
import com.example.testbaitap.entity.ChiTietBaiTapChoHV;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.utils.Config;
import com.example.testbaitap.utils.VideoViewUtils;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailExcerciseActivity extends AppCompatActivity {

    private TextView mtoolbar_title, tvTenDungCu, tvKhoiLuong, tvSoHiep, tvSoLanLap, tvTocDo, tvThoiGianNghi, tvCacBuoc, tvGhiChu, tvGhiChuFinal;
    private VideoView videoView;
    private Button button_raw, button_local, button_url;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonRaw;
    private Button buttonLocal;
    private TextView buttonURL;
    private SimpleAPI simpleAPI;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CheckBox checkboxHTBT;
    RelativeLayout relDetailEx;

    private CardView card_view_ct, cardVideo;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_excercise);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DetailExcerciseActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        String maBaiTap = intent.getStringExtra("maBaiTap");

        this.videoView = (VideoView) findViewById(R.id.videoView);
        this.cardVideo = (CardView) findViewById(R.id.caedVideo);
        this.card_view_ct = (CardView) findViewById(R.id.card_view_ct);
//        this.buttonRaw = (Button) findViewById(R.id.button_raw);
//        this.buttonLocal = (Button) findViewById(R.id.button_local );
        this.buttonURL = (TextView) findViewById(R.id.button_url);

        mtoolbar_title = (TextView) findViewById(R.id.mtoolbar_title);
        tvTenDungCu = (TextView) findViewById(R.id.tvTenDungCu);
        tvKhoiLuong = (TextView) findViewById(R.id.tvKhoiLuong);
        tvSoHiep = (TextView) findViewById(R.id.tvSoHiep);
        tvSoLanLap = (TextView) findViewById(R.id.tvSoLanLap);
        tvTocDo = (TextView) findViewById(R.id.tvTocDo);
        tvThoiGianNghi = (TextView) findViewById(R.id.tvThoiGianNghi);
        tvCacBuoc = (TextView) findViewById(R.id.tvCacBuoc);
        tvGhiChuFinal = (TextView) findViewById(R.id.ghiChu);
        tvGhiChu = (TextView) findViewById(R.id.tvGhiChuFinal);
        checkboxHTBT = (CheckBox) findViewById(R.id.checkboxHTBT);
        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tvGhiChuFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailExcerciseActivity.this);
                builder.setTitle("Cập nhật ghi chú");
                final EditText input = new EditText(DetailExcerciseActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String dm_Text = input.getText().toString();
                        if(!dm_Text.isEmpty()){
                            UpdateGhiChuBTChoHV(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), maBaiTap, dm_Text);
                        }
                        else {
                            dialog.dismiss();
                            Snackbar snackbar = Snackbar.make(relDetailEx, "Bạn chưa nhập ghi chú!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        relDetailEx = (RelativeLayout) findViewById(R.id.relDetailEx);
        LoadChiTietBTChoHV(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), maBaiTap);
        String currentDate = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        //Toast.makeText(DetailExcerciseActivity.this, currentDate.substring(0,2), Toast.LENGTH_SHORT).show();
        int a = Integer.parseInt(currentDate.substring(0,2));
        if((a>8 && a<12) || (a>14 && a<23)){
            checkboxHTBT.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailExcerciseActivity.this);
                builder.setTitle("Bạn có muốn thay đổi trạng thái hoàn thành bài tập?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer ttt = -1;
                        if(checkboxHTBT.isChecked()){
                            ttt=1;
                        }
                        else {
                            ttt=0;
                        }
                        UpdateTrangThaiBTChoHV(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), maBaiTap, ttt);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }
        else {
            checkboxHTBT.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailExcerciseActivity.this);
                builder.setTitle("Hiện tại đang ngoài khung giờ luyện tập!");
                builder.setMessage("Bạn có muốn thay đổi trạng thái hoàn thành bài tập?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer ttt = -1;
                        if(checkboxHTBT.isChecked()){
                            ttt=1;
                        }
                        else {
                            ttt=0;
                        }
                        UpdateTrangThaiBTChoHV(sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, ""), maBaiTap, ttt);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        }

        simpleAPI = Constants.instance();
        simpleAPI.getFullBaiTapTheoMa(maBaiTap).enqueue(new Callback<BaiTapFull>() {
            @Override
            public void onResponse(Call<BaiTapFull> call, Response<BaiTapFull> response) {
                BaiTapFull baiTapFull = response.body();
                try {
                    if(baiTapFull.equals(null)){
                        mtoolbar_title.setText("Chưa có bài tập này!");
                        cardVideo.setVisibility(View.INVISIBLE);
                        card_view_ct.setVisibility(View.INVISIBLE);
                    }
                    mtoolbar_title.setText(baiTapFull.getTenBaiTap());
                    tvCacBuoc.setText(baiTapFull.getMoTa());
                    tvTenDungCu.setText(baiTapFull.getTenDungCu());
                }
                catch (Exception e){
                    mtoolbar_title.setText("Chưa có bài tập này!");
                    cardVideo.setVisibility(View.INVISIBLE);
                    card_view_ct.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<BaiTapFull> call, Throwable t) {
                Toast.makeText(DetailExcerciseActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        simpleAPI.getChiTietBaiTapTheoMa(maBaiTap).enqueue(new Callback<ChiTietBaiTap>() {
            @Override
            public void onResponse(Call<ChiTietBaiTap> call, Response<ChiTietBaiTap> response) {
                ChiTietBaiTap chiTietBaiTap = response.body();
                try {
                    if(chiTietBaiTap.equals(null)){
                        mtoolbar_title.setText("Chưa có bài tập này!");
                        cardVideo.setVisibility(View.INVISIBLE);
                        card_view_ct.setVisibility(View.INVISIBLE);
                    }
                    tvKhoiLuong.setText(String.valueOf(chiTietBaiTap.getKhoiLuong()) + " (kg)");
                    tvSoHiep.setText(String.valueOf(chiTietBaiTap.getSoHiep()) + (" (hiệp)"));
                    tvSoLanLap.setText(String.valueOf(chiTietBaiTap.getSoLanLap()) + " (lần)");
                    tvTocDo.setText(String.valueOf(chiTietBaiTap.getTocDo()) + (" (giây)"));
                    tvThoiGianNghi.setText(String.valueOf(chiTietBaiTap.getThoiGianNghi())+ (" (giây)"));
                }
                catch (Exception e){
                    mtoolbar_title.setText("Chưa có bài tập này!");
                    cardVideo.setVisibility(View.INVISIBLE);
                    card_view_ct.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ChiTietBaiTap> call, Throwable t) {
                Toast.makeText(DetailExcerciseActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        // Set the media controller buttons
        if (this.mediaController == null) {
            this.mediaController = new MediaController(DetailExcerciseActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            this.mediaController.setAnchorView(videoView);

            // Set MediaController for VideoView
            this.videoView.setMediaController(mediaController);
        }


        // When the video file ready for playback.
        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {

                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

//        this.buttonRaw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // "myvideo.mp4" in directory "raw".
//                String resName = VideoViewUtils.RAW_VIDEO_SAMPLE;
//                VideoViewUtils.playRawVideo(DetailExcerciseActivity.this, videoView, resName);
//            }
//        });

//        this.buttonLocal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String localPath = VideoViewUtils.LOCAL_VIDEO_SAMPLE;
//                VideoViewUtils.playLocalVideo(DetailExcerciseActivity.this, videoView, localPath);
//            }
//        });

        this.buttonURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoURL = VideoViewUtils.URL_VIDEO_SAMPLE;
                VideoViewUtils.playURLVideo(DetailExcerciseActivity.this, videoView, videoURL);
            }
        });

    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
    }

    public void LoadChiTietBTChoHV(String maHocVien, String maBaiTap){
        simpleAPI = Constants.instance();
        simpleAPI.getChiTietBaiTapChoHocVien(maBaiTap, maHocVien).enqueue(new Callback<ChiTietBaiTapChoHV>() {
            @Override
            public void onResponse(Call<ChiTietBaiTapChoHV> call, Response<ChiTietBaiTapChoHV> response) {
                ChiTietBaiTapChoHV chiTietBaiTapChoHV = response.body();
                try {
                    if(chiTietBaiTapChoHV.getGhiChu() != null){
                        tvGhiChu.setText(chiTietBaiTapChoHV.getGhiChu());
                    }
                    else {
                        tvGhiChu.setText("Chưa có ghi chú nào!");
                    }
                    if(chiTietBaiTapChoHV.getTrangThai()==1){
                        checkboxHTBT.setChecked(true);
                    }
                }
                catch (Exception e ){
                    tvGhiChu.setText("Chưa có ghi chú nào!");
                    checkboxHTBT.setChecked(false);
                }
            }

            @Override
            public void onFailure(Call<ChiTietBaiTapChoHV> call, Throwable t) {
                tvGhiChu.setText("Chưa có ghi chú nào!");
                checkboxHTBT.setChecked(false);
                Log.d("quan", t.toString());
            }
        });
    }

    public void UpdateTrangThaiBTChoHV(String maHocVien, String maBaiTap, Integer trangThai){
        simpleAPI = Constants.instance();
        simpleAPI.updateTrangThaiChoHocVien(maBaiTap, maHocVien, trangThai).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if(status.getStatus()==4){
                        Snackbar snackbar = Snackbar.make(relDetailEx, "Không tìm thấy bài tập này!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus()==0){
                        Snackbar snackbar = Snackbar.make(relDetailEx, "Cập nhật không thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus()==1){
                        LoadChiTietBTChoHV(maHocVien, maBaiTap);
                        Snackbar snackbar = Snackbar.make(relDetailEx, "Cập nhật thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }catch (Exception e){
                    Snackbar snackbar = Snackbar.make(relDetailEx, "Cập nhật không thành công!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }

    public void UpdateGhiChuBTChoHV(String maHocVien, String maBaiTap, String ghiChu){
        simpleAPI = Constants.instance();
        simpleAPI.updateChiChuChoHocVien(maBaiTap, maHocVien, ghiChu).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if(status.getStatus()==4){
                        Snackbar snackbar = Snackbar.make(relDetailEx, "Không tìm thấy bài tập này!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus()==0){
                        Snackbar snackbar = Snackbar.make(relDetailEx, "Cập nhật không thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                    if(status.getStatus()==1){
                        LoadChiTietBTChoHV(maHocVien, maBaiTap);
                        Snackbar snackbar = Snackbar.make(relDetailEx, "Cập nhật thành công!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }catch (Exception e){
                    Snackbar snackbar = Snackbar.make(relDetailEx, "Cập nhật không thành công!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }

}