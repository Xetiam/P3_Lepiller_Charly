package com.openclassrooms.entrevoisins.ui.neighbour_list.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.entrevoisins.ui.neighbour_list.fragment.FavoriteFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.fragment.NeighbourFragment;


public class ListFavoritePagerAdapter extends FragmentPagerAdapter {

    public ListFavoritePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return FavoriteFragment.newInstance();
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 1;
    }
}