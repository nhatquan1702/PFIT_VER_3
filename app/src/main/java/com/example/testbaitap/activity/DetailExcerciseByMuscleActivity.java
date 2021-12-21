package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class DetailExcerciseByMuscleActivity extends AppCompatActivity {
    private TextView mtoolbar_title, tvTenDungCu, tvKhoiLuong, tvSoHiep, tvSoLanLap, tvTocDo, tvThoiGianNghi, tvCacBuoc, tvGhiChu, tvGhiChuFinal;
    private VideoView videoView;
    private Button button_raw, button_local, button_url;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonRaw;
    private Button buttonLocal;
    private TextView buttonURL;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SimpleAPI simpleAPI;
    RelativeLayout relDetailEx;
    private CardView card_view_ct, cardVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_excercise_by_muscle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mtoolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DetailExcerciseByMuscleActivity.this.finish();
            }
        });
        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        editor = sharedPreferences.edit();

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
        relDetailEx = (RelativeLayout) findViewById(R.id.relDetailEx);
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
                    buttonURL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String videoURL = baiTapFull.getVideo();
                            VideoViewUtils.playURLVideo(DetailExcerciseByMuscleActivity.this, videoView, videoURL);
                        }
                    });
                }
                catch (Exception e){
                    mtoolbar_title.setText("Chưa có bài tập này!");
                    cardVideo.setVisibility(View.INVISIBLE);
                    card_view_ct.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<BaiTapFull> call, Throwable t) {
                Toast.makeText(DetailExcerciseByMuscleActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        simpleAPI.getChiTietBaiTapTheoMa(maBaiTap, sharedPreferences.getString(Config.DATA_LOGIN_USERNAME, "")).enqueue(new Callback<ChiTietBaiTap>() {
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
                Toast.makeText(DetailExcerciseByMuscleActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        // Set the media controller buttons
        if (this.mediaController == null) {
            this.mediaController = new MediaController(DetailExcerciseByMuscleActivity.this);

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

}