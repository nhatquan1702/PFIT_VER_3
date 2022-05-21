package com.example.testbaitap.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testbaitap.R;
;
import com.example.testbaitap.api.Constants;
import com.example.testbaitap.api.SimpleAPI;
import com.example.testbaitap.entity.NhomCo;
import com.example.testbaitap.fragment.Fragment_Excercise;
import com.example.testbaitap.fragment.Fragment_Home;
import com.example.testbaitap.fragment.Fragment_Process;
import com.example.testbaitap.fragment.Fragment_Reminder;
import com.example.testbaitap.fragment.Fragment_Workout;
import com.example.testbaitap.utils.Config;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BottomNavigationView bottomNavigation;
    DrawerLayout drawer;
    ImageView imageView1;
    NavigationView navigationView;
    Toolbar toolbar;
    MenuItem menuItemLogin, menuItemAccount, menuItemManage, menuItemSales;
    LinearLayout container;
    ConstraintLayout serverError;
    SimpleAPI simpleAPI;
    ProgressBar progressMain;

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            String str = "";
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle(R.string.app_name);
                    MainActivity.this.openFragment(Fragment_Home.newInstance(str, str, MainActivity.this));
                    return true;

                case R.id.navigation_map:
                    toolbar.setTitle("Tập luyện");
                    MainActivity.this.openFragment(Fragment_Workout.newInstance(str, str));
                    return true;

                case R.id.navigation_world:
                    toolbar.setTitle("Bài tập");
                    MainActivity.this.openFragment(Fragment_Excercise.newInstance(str, str));
                    return true;

                case R.id.navigation_walk:
                    toolbar.setTitle("Theo dõi");
                    MainActivity.this.openFragment(Fragment_Process.newInstance(str, str));
                    return true;

                case R.id.navigation_news:
                    toolbar.setTitle("Nhắc nhở");
                    MainActivity.this.openFragment(Fragment_Reminder.newInstance(str, str));
                    return true;

                default:
                    return false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverError = findViewById(R.id.serverError);
        container = findViewById(R.id.container);
        progressMain = findViewById(R.id.progressMain);

        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            serverError.setVisibility(View.INVISIBLE);
            container.setVisibility(View.VISIBLE);
        }
        else {
            serverError.setVisibility(View.VISIBLE);
            container.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "Bạn cần kết nối Internet!", Toast.LENGTH_SHORT).show();
        }

        simpleAPI = Constants.instance();
        simpleAPI.getListNhomCo().enqueue(new Callback<ArrayList<NhomCo>>() {
            @Override
            public void onResponse(Call<ArrayList<NhomCo>> call, Response<ArrayList<NhomCo>> response) {
                serverError.setVisibility(View.INVISIBLE);
                container.setVisibility(View.VISIBLE);
                progressMain.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<NhomCo>> call, Throwable t) {
                serverError.setVisibility(View.VISIBLE);
                container.setVisibility(View.INVISIBLE);
                progressMain.setVisibility(View.INVISIBLE);
            }
        });

        this.navigationView = (NavigationView) findViewById(R.id.nav_views);
        //this.imageView1 = (ImageView) findViewById(R.id.setting);
        this.toolbar = initToolbar();
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawer = drawerLayout;
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(actionBarDrawerToggle);
        this.drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            public void onDrawerClosed(View view) {
                //Toast.makeText(MainActivity.this, R.string.navigation_drawer_close, Toast.LENGTH_SHORT).show();
            }

            public void onDrawerOpened(View view) {
                //Toast.makeText(MainActivity.this, R.string.navigation_drawer_open, Toast.LENGTH_SHORT).show();
            }
        });
        actionBarDrawerToggle.syncState();
        this.navigationView.setNavigationItemSelectedListener(this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);
        this.bottomNavigation = bottomNavigationView;
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(this.navigationItemSelectedListener);
        String str2 = "";
        openFragment(Fragment_Home.newInstance(str2 ,str2, MainActivity.this));

        Menu menu = navigationView.getMenu();
        menuItemLogin = menu.findItem(R.id.nav_login);
        menuItemAccount = menu.findItem(R.id.nav_account);
        menuItemManage = menu.findItem(R.id.nav_manage);
        menuItemSales = menu.findItem(R.id.nav_sales);

        sharedPreferences = getSharedPreferences(Config.DATA_LOGIN, MODE_PRIVATE);
        String role = sharedPreferences.getString(Config.DATA_LOGIN_ROLE, "-1");

        menuItemLogin.setVisible(role.equals("-1"));
        menuItemAccount.setVisible(!role.equals("-1"));
        if(role.equals("2")){
            menuItemManage.setVisible(true);
        }
        else {
            menuItemManage.setVisible(false);
        }

        if(role.equals("3")){
            menuItemSales.setVisible(true);
        }
        else {
            menuItemSales.setVisible(false);
        }



    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
    }

    public void loadFragmentworkout(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        toolbar.setTitle("Tập luyện");
        bottomNavigation.setSelectedItemId(R.id.navigation_map);
    }

    public void loadFragment_water(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.nav_host_fragment, fragment);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();
        toolbar.setTitle("Theo dõi");
        bottomNavigation.setSelectedItemId(R.id.navigation_walk);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar2);
        return toolbar2;
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        String str = "android.intent.extra.TEXT";
        String str2 = "android.intent.extra.SUBJECT";

        if (itemId == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        } else if (itemId == R.id.nav_share) {

            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.setType("text/plain");
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Tải ngay ứng dụng PFIT.\n Xin cảm ơn!\n  https://play.google.com/store/apps/details?id=" + getPackageName());
            sb3.append(getApplicationContext().getPackageName());
            String sb4 = sb3.toString();
            intent2.putExtra(str2, "Share App");
            intent2.putExtra(str, sb4);
            startActivity(Intent.createChooser(intent2, "Share via"));
        }else  if(itemId == R.id.nav_privacy)
        {
            //Liên kết tới trang điều khoản sử dụng
            Uri uri = Uri.parse("https://drive.google.com/file/d/1DvztngmjDeNSvOxqPcA82-6h2sa8o3Ok/view?usp=sharing");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        else if(itemId==R.id.nav_login){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        else if(itemId==R.id.nav_account){
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);
        }

        else if(itemId==R.id.nav_manage){
            //Toast.makeText(MainActivity.this, "khóa tập", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ManageTCActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }
        else if(itemId==R.id.nav_sales){
            //Toast.makeText(MainActivity.this, "doanh thu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SalesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
        }
        else if(itemId==R.id.nav_setting){
            Intent intent = new Intent(MainActivity.this, PayMentsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);
            Toast.makeText(MainActivity.this, "Cài đặt", Toast.LENGTH_SHORT).show();
        }

        this.drawer.closeDrawer((int) GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát ứng dụng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
                System.exit(1);
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
}
