package com.openclassrooms.entrevoisins.ui.neighbour_list.adapter;





import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.openclassrooms.entrevoisins.ui.neighbour_list.fragment.NeighbourFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.fragment.FavoriteFragment;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        if(position == 1){
            return FavoriteFragment.newInstance();
        }
        else{
            return NeighbourFragment.newInstance();
        }
    }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        return 2;
    }
}