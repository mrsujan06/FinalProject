package com.test.musicfinderpro.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.musicfinderpro.tabs.AlbumTab;
import com.test.musicfinderpro.tabs.HomeTab;
import com.test.musicfinderpro.tabs.MusicPreviewTab;
import com.test.musicfinderpro.tabs.SearchArtistTab;
import com.test.musicfinderpro.tabs.Tab5;

/**
 * Created by Sujan on 13/03/2018.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                HomeTab homeTab = new HomeTab();
                return homeTab;
            case 1:
                SearchArtistTab searchArtistTab = new SearchArtistTab();
                return searchArtistTab;

            case 2:
                AlbumTab albumTab = new AlbumTab();
                return albumTab;

            case 3:
                MusicPreviewTab musicPreviewTab = new MusicPreviewTab();
                return musicPreviewTab;

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

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
