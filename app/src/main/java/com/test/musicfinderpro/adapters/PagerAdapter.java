package com.test.musicfinderpro.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.musicfinderpro.tabs.HomeTab1;
import com.test.musicfinderpro.tabs.MusicPreviewTab4;
import com.test.musicfinderpro.tabs.AlbumTab3;
import com.test.musicfinderpro.tabs.SearchArtistTab2;
import com.test.musicfinderpro.tabs.Tab5;

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
                HomeTab1 homeTab1 = new HomeTab1();
                return homeTab1;
            case 1:

                SearchArtistTab2 searchArtistTab2 = new SearchArtistTab2();
                return searchArtistTab2;
//                MusicPreviewTab4 musicPreviewTab4 = new MusicPreviewTab4();
//                return musicPreviewTab4;

            case 2:

            AlbumTab3 albumTab3 = new AlbumTab3();
            return albumTab3;

            case 3:
                MusicPreviewTab4 musicPreviewTab4 = new MusicPreviewTab4();
                return musicPreviewTab4;

//                SearchArtistTab2 searchArtistTab2 = new SearchArtistTab2();
//                return searchArtistTab2;

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
