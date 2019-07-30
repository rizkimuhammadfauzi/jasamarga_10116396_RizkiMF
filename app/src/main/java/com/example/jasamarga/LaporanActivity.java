package com.example.jasamarga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.jasamarga.adapter.LaporanAdapter;
import com.example.jasamarga.adapter.ViewPagerAdapter;
import com.example.jasamarga.data.modelData;
import com.example.jasamarga.fragment.FragmentOne;
import com.example.jasamarga.fragment.FragmentTwo;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LaporanActivity extends AppCompatActivity {

    private ImageView back;
    ViewPager viewPager;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_laporan);

        viewPager = findViewById(R.id.viewPager);
        back = findViewById(R.id.back);

        addTabs(viewPager);
        ((TabLayout) findViewById(R.id.tabLayout)).setupWithViewPager( viewPager );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LaporanActivity.this,DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentOne(), "Belum Selesai");
        adapter.addFrag(new FragmentTwo(), "Selesai");
        viewPager.setAdapter(adapter);
    }
}
