package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.HocVien;
import com.example.testbaitap.entity.TaiKhoan;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    private SimpleAPI simpleAPI;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefresh;
    ImageView imageEditHoTen, imgEditSDT, imgEditDiaChi, imgEditMK, imgEditQR;
    TextView textViewHoTen, textViewSDT, textViewDiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        mSwipeRefresh = findViewById(R.id.swipe_refresh);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        //Toast.makeText(AccountActivity.this, sharedPreferences.getString("email", "username"), Toast.LENGTH_SHORT).show();
        textViewHoTen = findViewById(R.id.textViewHoTen);
        textViewSDT = findViewById(R.id.textViewSDT);
        textViewDiaChi = findViewById(R.id.textViewDiaChi);
        editor = sharedPreferences.edit();
        LoadThongTinCaNhan(sharedPreferences.getString("email", "username"));
        mSwipeRefresh.setOnRefreshListener(() -> {
            LoadThongTinCaNhan(sharedPreferences.getString("email", "username"));
            mSwipeRefresh.setRefreshing(false);
        });
        CardView button = findViewById(R.id.cvDKTK);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.remove("email");
                editor.remove("password");
                editor.remove("role");

                editor.commit();
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        imageEditHoTen = findViewById(R.id.imageEditHoTen);
        imageEditHoTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottomHotten().show();
            }
        });

        imgEditSDT = findViewById(R.id.imgEditSDT);
        imgEditSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottomSDT().show();
            }
        });
        imgEditDiaChi = findViewById(R.id.imgEditDiaChi);
        imgEditDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottomDiaChi().show();
            }
        });
        imgEditMK = findViewById(R.id.imgEditMK);
        imgEditMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottomMK().show();
            }
        });
        imgEditQR = findViewById(R.id.imgEditQR);
        imgEditQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottomQR().show();
            }
        });
    }

    public void LoadThongTinCaNhan(String tk){
        progressBar.setVisibility(View.VISIBLE);
        simpleAPI = Constants.instance();
        simpleAPI.getThongTinTaiKhoan(tk).enqueue(new Callback<TaiKhoan>() {
            @Override
            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
               try {
                   TaiKhoan khachHang = response.body();
                   textViewHoTen.setText(khachHang.getHoTen());
                   textViewSDT.setText(khachHang.getSoDienThoai());
                   textViewDiaChi.setText(khachHang.getDiaChi());
                   progressBar.setVisibility(View.GONE);
               }catch (Exception e){
                   e.printStackTrace();
                   progressBar.setVisibility(View.GONE);
               }
            }

            @Override
            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                Toast.makeText(AccountActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public BottomSheetDialog diaLogBottomHotten() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_hoten, null);
        sheetDialog.setContentView(viewDialog);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        return sheetDialog;
    }
    @SuppressLint("ResourceAsColor")
    public BottomSheetDialog diaLogBottomTuoi() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_tuoi, null);
        sheetDialog.setContentView(viewDialog);
        NumberPicker np = (NumberPicker) viewDialog.findViewById(R.id.numberPickTuoi);
        np.setMaxValue(100); // max value 100
        np.setMinValue(1);   // min value 0
        np.setValue(22);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        return sheetDialog;
    }
    public BottomSheetDialog diaLogBottomGioiTinh() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_gioitinh, null);
        sheetDialog.setContentView(viewDialog);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        return sheetDialog;
    }
    public BottomSheetDialog diaLogBottomSDT() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_sdt, null);
        sheetDialog.setContentView(viewDialog);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        return sheetDialog;
    }
    public BottomSheetDialog diaLogBottomDiaChi() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_diachi, null);
        sheetDialog.setContentView(viewDialog);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        return sheetDialog;
    }
    public BottomSheetDialog diaLogBottomMK() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_matkhau, null);
        sheetDialog.setContentView(viewDialog);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        return sheetDialog;
    }
    public BottomSheetDialog diaLogBottomQR() {
        BottomSheetDialog sheetDialog = new BottomSheetDialog(AccountActivity.this, R.style.SheetDialog);
        View viewDialog = getLayoutInflater().inflate(R.layout.bottom_sheet_qr, null);
        sheetDialog.setContentView(viewDialog);
        viewDialog.findViewById(R.id.btnUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetDialog.dismiss();
            }
        });
        String data = sharedPreferences.getString("email", "")+"||"+sharedPreferences.getString("password", "");
        ImageView imageView = (ImageView)viewDialog.findViewById(R.id.imgQR);
        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, data.length());
        try {
            // Getting QR-Code as Bitmap
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(AccountActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
//        Picasso.get()
//                .load(post.getPost_img())
//                .placeholder(R.drawable.gallery)
//                .error(R.drawable.gallery)
//                .into(imageView);
        return sheetDialog;
    }
}