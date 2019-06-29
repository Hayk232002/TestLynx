package com.example.testlynx;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity{

    private SlidingUpPanelLayout sp;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView tv;
    Toolbar toolbar;
    TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = (SlidingUpPanelLayout) findViewById(R.id.sp);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tv = (TextView) findViewById(R.id.tv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);

        setSupportActionBar(toolbar);
        setViewPager();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            findViewById(R.id.pager).setBackgroundResource(R.drawable.shape);
//        }

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                MainActivity.this,
                findViewById(R.id.pager),
                new AccelerateDecelerateInterpolator(),
                MainActivity.this.getResources().getDrawable(R.drawable.ic_menu_white_24dp), // Menu open icon
                MainActivity.this.getResources().getDrawable(R.drawable.ic_close_white_24dp))); // Menu close icon

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
        final TextView finalBarTitleView = barTitleView;
        barTitleView.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_android_black_24dp);
        tabs.getTabAt(1).setIcon(R.drawable.ic_call_black_24dp);
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void addTabs(AppBarLayout app_bar, TabLayout tabs, ViewPager viewPager){
//        if (!iftabsadd) {
//            app_bar.addView(tabs);
//            tabs.setupWithViewPager(viewPager);
//            tabs.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.tabs_anim));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                tabs.setBackgroundColor(getColor(R.color.colorPrimary));
//            }
//            AutoTransition autoTransition = new AutoTransition();
//            autoTransition.setDuration(200);
//            TransitionManager.beginDelayedTransition(app_bar,autoTransition);
//
//            tabs.getTabAt(0).setIcon(R.drawable.ic_android_black_24dp);
//            tabs.getTabAt(1).setIcon(R.drawable.ic_call_black_24dp);
//
//            iftabsadd=true;
//        }
//
//        else {
//            app_bar.removeView(tabs);
//
//            iftabsadd=false;
//        }
//    }
}
