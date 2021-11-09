package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.BaiTapFull;
import com.example.testbaitap.entity.ChiTietBaiTap;
import com.example.testbaitap.utils.VideoViewUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailExcerciseActivity extends AppCompatActivity {

    private TextView mtoolbar_title, tvTenDungCu, tvKhoiLuong, tvSoHiep, tvSoLanLap, tvTocDo, tvThoiGianNghi, tvCacBuoc, tvGhiChu;
    private VideoView videoView;
    private Button button_raw, button_local, button_url;
    private int position = 0;
    private MediaController mediaController;
    private Button buttonRaw;
    private Button buttonLocal;
    private TextView buttonURL;
    private SimpleAPI simpleAPI;

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
        tvGhiChu = (TextView) findViewById(R.id.tvGhiChu);
        simpleAPI = Constants.instance();
        simpleAPI.getFullBaiTapTheoMa(maBaiTap).enqueue(new Callback<BaiTapFull>() {
            @Override
            public void onResponse(Call<BaiTapFull> call, Response<BaiTapFull> response) {
                BaiTapFull baiTapFull = response.body();
                mtoolbar_title.setText(baiTapFull.getTenBaiTap());
                tvCacBuoc.setText(baiTapFull.getMoTa());
                tvTenDungCu.setText(baiTapFull.getTenDungCu());
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
                tvKhoiLuong.setText(String.valueOf(chiTietBaiTap.getKhoiLuong()) + " (kg)");
                tvSoHiep.setText(String.valueOf(chiTietBaiTap.getSoHiep()) + (" (hiệp)"));
                tvSoLanLap.setText(String.valueOf(chiTietBaiTap.getSoLanLap()) + " (lần)");
                tvTocDo.setText(String.valueOf(chiTietBaiTap.getTocDo()) + (" (giây)"));
                tvThoiGianNghi.setText(String.valueOf(chiTietBaiTap.getThoiGianNghi())+ (" (giây"));
                tvGhiChu.setText(chiTietBaiTap.getGhiChu());
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

}