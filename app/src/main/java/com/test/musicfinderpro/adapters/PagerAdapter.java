package com.test.musicfinderpro.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.musicfinderpro.Tab1;
import com.test.musicfinderpro.Tab2;
import com.test.musicfinderpro.Tab3;
import com.test.musicfinderpro.Tab4;
import com.test.musicfinderpro.Tab5;

/**
 * Created by Sujan on 13/03/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return  tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return  tab3;

            case 3:
                Tab4 tab4 = new Tab4();
                return tab4;

            case 4:
                Tab5 tab5 = new Tab5();
                return tab5;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
