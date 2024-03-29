package com.example.testbaitap.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.testbaitap.R;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.KhoaTap;
import com.example.testbaitap.entity.Status;
import com.example.testbaitap.viewModel.HLVViewModel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertTCActivity extends AppCompatActivity {
    private EditText edtMaKT, edtTenKT, edtGiaKT;
    private TextView tvLamLai, mText, tvCapNhat;
    private SimpleAPI simpleAPI;
    private String encoded = null; // encoded img bitmap to base64
    private String imgReceive = null;
    private String hlv, nv;
    private int  trangThai, giaKT;
    SharedPreferences sharedPreferences;
    private ImageButton imageButton;
    private ImageView imgHinhKT;
    private CardView btnCapNhat;
    private RadioButton rNam, rNu, rNN;
    private HLVViewModel viewModel;
    private Spinner spinner;

    private static final int PERMISSION_CODE =1;
    private static final int PICK_IMAGE=1;
    private  boolean checkInit = true;// chưa
    String filePath;
    private void configCloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "dmfrvd4tl");
        config.put("api_key", "258945955129684");
        config.put("api_secret", "taQ7f4rtk6nM2DzRGo9Crzj3WVs");
        config.put("secure", true);
        MediaManager.init(InsertTCActivity.this,null, config);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_khoa_tap);

        edtMaKT = findViewById(R.id.edtMaKT);
        edtMaKT.setEnabled(true);
        edtTenKT = findViewById(R.id.edtTenKT);
        edtGiaKT = findViewById(R.id.edtGiaKTTheoThang);
        mText = findViewById(R.id.tvLink);
        imgHinhKT = findViewById(R.id.imgKT);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        imageButton = findViewById(R.id.imgButtonUp);

        rNam = findViewById(R.id.rNam);
        rNu = findViewById(R.id.rNu);
        rNN = findViewById(R.id.rNN);

        tvCapNhat = findViewById(R.id.tvCapNhat);
        tvLamLai = findViewById(R.id.tvLamLai);
        tvCapNhat.setText("Cập nhật");

//        spinner = (Spinner) findViewById(R.id.spinnerHLV);
//        viewModel = new ViewModelProvider(this).get(HLVViewModel.class);
//        HuanLuyenVien huanLuyenVien = new HuanLuyenVien("01", "Huy", "011" ,"Q9", "", 1);
//        HuanLuyenVien huanLuyenVien1 = new HuanLuyenVien("02", "Q", "022" ,"Q9", "", 1);
//        viewModel.addHLVData(huanLuyenVien);
//        viewModel.addHLVData(huanLuyenVien1);

//        viewModel.getHLVList().observe(this, new Observer<ArrayList<HuanLuyenVien>>() {
//            @Override
//            public void onChanged(ArrayList<HuanLuyenVien> huanLuyenViens) {
//                //set adapter
//                ArrayAdapter spinnerAdapter = new ArrayAdapter<>(InsertKhoaTapActivity.this, R.layout.support_simple_spinner_dropdown_item, huanLuyenViens);
//                spinner.setAdapter(spinnerAdapter);
//                spinner.setSelection(0);
//            }
//        });
        // mBtnUpload.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        String mAuthor = sharedPreferences.getString("email", "admin");
        if(checkInit){
            try {
                configCloudinary();
            }
            catch (Exception e){

            }

            checkInit=false;
        }
        //when click mImageAdd request the permission to access the gallery
        LoadButton();

        // receive update
    }

    private void LoadButton() {
        tvLamLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgHinhKT.setImageResource(R.mipmap.logo1);
                edtMaKT.getText().clear();
                edtTenKT.getText().clear();
                edtGiaKT.getText().clear();
                mText.setText("");
                rNN.setChecked(true);
            }
        });
        imgHinhKT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mText.setText("");
                requestPermission();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    try {
                        if(filePath.isEmpty() || filePath.toString().trim().equals("")){
                            Toast.makeText(InsertTCActivity.this, "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Log.d("quan", e.toString());
                        Toast.makeText(InsertTCActivity.this, "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();
                    }
                    uploadToCloudinary(filePath);
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                    Toast.makeText(InsertTCActivity.this, "Vui lòng chọn ảnh!", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int dt = 2;
                if(rNam.isChecked()){
                    dt = 1;
                }
                else if(rNu.isChecked()){
                    dt = 0;
                }
                else {
                    dt = 2;
                }
                edtMaKT.setEnabled(true);
                String maKT = edtMaKT.getText().toString().trim();
                String tenKT = edtTenKT.getText().toString().trim();
                String giaKT = edtGiaKT.getText().toString().trim();
                String hinhKT = mText.getText().toString().trim();

                boolean check = true;

                if(maKT.isEmpty()){
                    edtMaKT.setError("Mã khóa tập không được bỏ trống!");
                    check=false;
                }


                if(tenKT.isEmpty()){
                    edtTenKT.setError("Tên khóa tập không được bỏ trống!");
                    check=false;
                }

                if(giaKT.isEmpty()){
                    edtTenKT.setError("Gía khóa tập không được bỏ trống!");
                    check=false;
                }

                if(hinhKT.isEmpty()){
                    Toast.makeText(InsertTCActivity.this, "Hình ảnh không được bỏ trống!", Toast.LENGTH_SHORT).show();
                    check=false;
                }
                if(check==true){
                    try {
                        KhoaTap khoaTap = new KhoaTap(maKT, tenKT, hinhKT, Integer.parseInt(giaKT), dt, 1, "01", "01");
                        EditKT(khoaTap);
                    }
                    catch (Exception e){
                        Log.d("quan", e.toString());
                    }
                }

            }
        });
    }

    public void EditKT(KhoaTap khoaTap){
        simpleAPI  = Constants.instance();
        simpleAPI.insertKhoaTap(khoaTap).enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                try {
                    if(status.getStatus()==1){
                        Intent intent;
                        Toast.makeText(InsertTCActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(InsertTCActivity.this, ManageTCActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityIfNeeded(intent, 0);
                        finish();
                    }
                    else {
                        Toast.makeText(InsertTCActivity.this, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Log.d("quan", e.toString());
                    Toast.makeText(InsertTCActivity.this, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.d("quan", t.toString());
            }
        });
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (InsertTCActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
            accessTheGallery();
        } else {
            ActivityCompat.requestPermissions(
                    InsertTCActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessTheGallery();
            } else {
                Toast.makeText(InsertTCActivity.this, "Không có quyền truy cập vào thư viện", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void accessTheGallery(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //get the image's file location
        filePath = getRealPathFromUri(data.getData(), InsertTCActivity.this);

        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            try {
                //set picked image to the mProfile
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgHinhKT.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromUri(Uri imageUri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if(cursor==null) {
            return imageUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    private void uploadToCloudinary(String filePath) {
        mText.setText("");
        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                mText.setText("Bắt đầu tải lên...");
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                mText.setText("Vui đòng đợi... ");
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
                mText.setText(resultData.get("url").toString());
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                mText.setText("Lỗi "+ error.getDescription());
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                mText.setText("Reshedule "+error.getDescription());
            }
        }).dispatch();
    }
}