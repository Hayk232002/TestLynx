package com.example.testlynx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity{
    //backdrop, toolbar tabs and timer
    private final AnimatorSet animatorSet = new AnimatorSet();
    private Interpolator interpolator;
    private int height;
    private boolean backdropShown = false;
    private Drawable openIcon;
    private Drawable closeIcon;
    private CountDownTimer timer;
    private long interval = 2000;
    private boolean timerrun = false;
    private boolean clickedonesmore = false;

    private SlidingUpPanelLayout sp;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView tv;
    Toolbar toolbar;
    TabLayout tabs;
    DrawerLayout drawerLayout;
    CoordinatorLayout main_content;
    ConstraintLayout chat_content;
    AppBarLayout app_bar;
    BottomNavigationView bottom_navigation;

    FragmentFriends fragmentFriends;
    FragmentGroups fragmentGroups;
    FragmentManager fm;
    Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = (SlidingUpPanelLayout) findViewById(R.id.sp);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tv = (TextView) findViewById(R.id.tv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        main_content = (CoordinatorLayout) findViewById(R.id.main_content);
        chat_content = (ConstraintLayout) findViewById(R.id.chat_content);
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        fragmentFriends = new FragmentFriends();
        fragmentGroups = new FragmentGroups();
        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.friends_container,fragmentFriends).commit();
        active = fragmentFriends;

        drawer();
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
            public void onClick(View view) {
                backdrop();
            }
        });

        //toolbar tabs click listener
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (backdropShown && !timerrun){
                    timer();
                }

                else if (backdropShown && timerrun){
                    clickedonesmore = true;
                    timer.cancel();
                    interval = 2000;
                    timer.start();

                    Log.wtf("time", "mtav");
                }

                int position = tab.getPosition();
                switch (position){
                    case 0:
                    {
                        toolbar.setTitle("Feed");
                        break;
                    }

                    case 1:
                    {
                        toolbar.setTitle("Forum");
                        break;
                    }

                    case 2:
                    {
                        toolbar.setTitle("Event");
                        break;
                    }

                    case 3:
                    {
                        toolbar.setTitle("Notification");
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_friends:
                    {
                        fm.beginTransaction().replace(R.id.friends_container,fragmentFriends).commit();
                        active = fragmentFriends;
                        return true;
                    }

                    case R.id.item_groups:
                    {
                        fm.beginTransaction().replace(R.id.friends_container,fragmentGroups).commit();
                        active = fragmentGroups;
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void setViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),toolbar);
        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_format_align_justify_black_24dp);
        tabs.getTabAt(1).setIcon(R.drawable.ic_forum_black_24dp);
        tabs.getTabAt(2).setIcon(R.drawable.ic_event_black_24dp);
        tabs.getTabAt(3).setIcon(R.drawable.ic_notifications_black_24dp);
    }

    private void backdrop(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) MainActivity.this).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;

        backdropShown = !backdropShown;

        // Cancel the existing animations
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();


        final int translateY = height -
                getResources().getDimensionPixelSize(R.dimen.shr_product_grid_reveal_height);

        ObjectAnimator animator = ObjectAnimator.ofFloat(viewPager, "translationY", backdropShown ? translateY : 0);
        animator.setDuration(200);
        if (interpolator != null) {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();

//        if (backdropShown){
//            app_bar.setElevation(0);
//        }
//
//        else{
//            app_bar.setElevation(8);
//        }
    }
    
    public void timer(){
        timerrun = true;
        timer = new CountDownTimer(interval,100) {
            @Override
            public void onTick(long l) {
                interval = l;
               Log.wtf("time", ""+interval);
            }

            @Override
            public void onFinish() {
                backdropShown = !backdropShown;

                animatorSet.removeAllListeners();
                animatorSet.end();
                animatorSet.cancel();

                final int translateY = height -
                        getResources().getDimensionPixelSize(R.dimen.shr_product_grid_reveal_height);

                ObjectAnimator animator = ObjectAnimator.ofFloat(viewPager, "translationY", backdropShown ? translateY : 0);
                animator.setDuration(200);
                if (interpolator != null) {
                    animator.setInterpolator(interpolator);
                }
                animatorSet.play(animator);
                animator.start();

//                if (backdropShown){
//                    app_bar.setElevation(0);
//                }
//
//                else{
//                    app_bar.setElevation(8);
//                }

                interval = 2000;
                timerrun = false;
            }
        }.start();
    }

    public void drawer(){
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.setDrawerElevation(0);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                main_content.setTranslationX(slideX);
                chat_content.setTranslationX(slideX);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }


}