package com.example.testbaitap.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testbaitap.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetailsActivity extends AppCompatActivity {

    private TextView tvID, tvTT, tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        tvID = findViewById(R.id.tvID);
        tvTT = findViewById(R.id.tvToTal);
        tvStatus = findViewById(R.id.tvStatus);

        Intent intent = getIntent();
        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetail(jsonObject.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void showDetail(JSONObject response, String tongTien){
        try {
            tvID.setText(response.getString("id"));
            tvStatus.setText(response.getString("state"));
            tvTT.setText(response.getString(String.format("$%s", tongTien)));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}