package com.example.testbaitap.activity;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testbaitap.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;

public class AccountActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageEditHoTen, imgEditTuoi, imgEditGioiTinh, imgEditSDT, imgEditDiaChi, imgEditMK, imgEditQR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Toast.makeText(AccountActivity.this, sharedPreferences.getString("email", "username"), Toast.LENGTH_SHORT).show();

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
        return sheetDialog;
    }
}