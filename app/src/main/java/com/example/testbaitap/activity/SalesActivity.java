package com.example.testbaitap.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testbaitap.R;
import com.example.testbaitap.adapter.ViewPagerAdapter;
import com.example.testbaitap.fragment.SalesDailyFragment;
import com.example.testbaitap.fragment.SalesMontlyFragment;
import com.example.testbaitap.fragment.SalesYearlyFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SalesActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private FloatingActionButton fabDX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        fabDX = findViewById(R.id.fabDX);
        fabDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesActivity.this, AccountActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(intent, 0);
            }
        });
        viewPager2 = (ViewPager2) findViewById(R.id.viewPager2);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        LoadTabLayoutAndViewpager2();
    }

    private void LoadTabLayoutAndViewpager2() {
        List<Fragment> fragment = new ArrayList<>();
        fragment.add(new SalesDailyFragment());
        fragment.add(new SalesMontlyFragment());
        fragment.add(new SalesYearlyFragment());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(fragment, this);
        viewPager2.setAdapter(viewPagerAdapter);
        String[] titleTab = {"Ngày", "Tháng", "Năm"};
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titleTab[position]);
            }
        }).attach();

    }
}