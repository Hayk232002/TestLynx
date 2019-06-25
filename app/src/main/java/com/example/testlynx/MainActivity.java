package com.example.testlynx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private SlidingUpPanelLayout sp;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView tv;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = (SlidingUpPanelLayout) findViewById(R.id.sp);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tv = (TextView) findViewById(R.id.tv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        setViewPager();

        //slidingUpPanelLayout click listener
        sp.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                tv.setAlpha(1 - slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }

        });

        //getting actionbar title
        Field titleField = null;

        try {
            titleField = Toolbar.class.getDeclaredField("mTitleTextView");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        titleField.setAccessible(true);
        TextView barTitleView = null;
        try {
            barTitleView = (TextView) titleField.get(toolbar);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //actionbar title click listener
        barTitleView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Title", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottomappbar_menu, menu);
        return true;
    }

    private void setViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }
}
