package com.example.testbaitap.activity;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.KhachHang;
import com.example.testbaitap.entity.TaiKhoan;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.WriterException;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    private SimpleAPI simpleAPI;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageEditHoTen, imgEditTuoi, imgEditGioiTinh, imgEditSDT, imgEditDiaChi, imgEditMK, imgEditQR;
    TextView textViewHoTen, textViewTuoi, textViewGioiTinh, textViewSDT, textViewDiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        Toast.makeText(AccountActivity.this, sharedPreferences.getString("email", "username"), Toast.LENGTH_SHORT).show();
        textViewHoTen = findViewById(R.id.textViewHoTen);
        textViewTuoi = findViewById(R.id.textViewTuoi);
        textViewGioiTinh = findViewById(R.id.textViewGioiTinh);
        textViewSDT = findViewById(R.id.textViewSDT);
        textViewDiaChi = findViewById(R.id.textViewDiaChi);
        editor = sharedPreferences.edit();
        simpleAPI = Constants.instance();
        simpleAPI.getKhachHang(sharedPreferences.getString("email", "username")).enqueue(new Callback<KhachHang>() {
            @Override
            public void onResponse(Call<KhachHang> call, Response<KhachHang> response) {
                KhachHang khachHang = response.body();
                Log.d("quan", khachHang.getMaHocVien());
                Log.d("quan", khachHang.getMatKhau());
                Log.d("quan", String.valueOf(khachHang.getTrangThai()));
                textViewHoTen.setText(khachHang.getHoTen());
                textViewTuoi.setText(String.valueOf(khachHang.getTuoi()));
                int gt = khachHang.getGioiTinh();
                if(gt==1){
                    textViewGioiTinh.setText("Nam");
                }
                else if(gt==0){
                    textViewGioiTinh.setText("Nữ");
                }
                else{
                    textViewGioiTinh.setText("Khác");
                }
                textViewSDT.setText(khachHang.getSoDienThoai());
                textViewDiaChi.setText(khachHang.getDiaChi());
            }

            @Override
            public void onFailure(Call<KhachHang> call, Throwable t) {
                Toast.makeText(AccountActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
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
        imgEditTuoi = findViewById(R.id.imgEditTuoi);
        imgEditTuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottomTuoi().show();
            }
        });
        imgEditGioiTinh = findViewById(R.id.imgEditGioiTinh);
        imgEditGioiTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLogBottomGioiTinh().show();
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
        String data = "quan_123";
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