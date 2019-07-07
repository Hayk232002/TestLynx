package com.example.testlynx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity{
    //backdrop
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

//        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
//                MainActivity.this,
//                findViewById(R.id.pager),
//                new AccelerateDecelerateInterpolator(),
//                MainActivity.this.getResources().getDrawable(R.drawable.ic_menu_white_24dp), // Menu open icon
//                MainActivity.this.getResources().getDrawable(R.drawable.ic_close_white_24dp))); // Menu close icon

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

                interval = 2000;
                timerrun = false;
            }
        }.start();
    }
}


//        int interval = 2000;
//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                backdropShown = !backdropShown;
//
//                animatorSet.removeAllListeners();
//                animatorSet.end();
//                animatorSet.cancel();
//
//                final int translateY = height -
//                        getResources().getDimensionPixelSize(R.dimen.shr_product_grid_reveal_height);
//
//                ObjectAnimator animator = ObjectAnimator.ofFloat(viewPager, "translationY", backdropShown ? translateY : 0);
//                animator.setDuration(200);
//                if (interpolator != null) {
//                    animator.setInterpolator(interpolator);
//                }
//                animatorSet.play(animator);
//                animator.start();
//            }
//        };
//
//        handler.postDelayed(runnable, interval);