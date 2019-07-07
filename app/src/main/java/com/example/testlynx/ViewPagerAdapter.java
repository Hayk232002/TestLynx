package com.example.testlynx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static int TAB_COUNT = 4;
    private Toolbar toolbar;

    public ViewPagerAdapter(@NonNull FragmentManager fm, Toolbar toolbar) {
        super(fm);
        this.toolbar = toolbar;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return FragmentFeed.newInstance();
            case 1:
                return FragmentForum.newInstance();
            case 2:
                return FragmentEvent.newInstance();
            case 3:
                return FragmentNotifications.newInstance();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//
//        switch (position) {
//            case 0:
//                toolbar.setTitle("Feed");
//                break;
//
//            case 1:
//                toolbar.setTitle("Forum");
//                break;
//        }
//        return super.getPageTitle(position);
//    }


}
