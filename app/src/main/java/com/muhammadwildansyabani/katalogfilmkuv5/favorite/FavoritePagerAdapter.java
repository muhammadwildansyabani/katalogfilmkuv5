package com.muhammadwildansyabani.katalogfilmkuv5.favorite;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.muhammadwildansyabani.katalogfilmkuv5.favorite.movie.FavoriteMovieFragment;
import com.muhammadwildansyabani.katalogfilmkuv5.favorite.tv.FavoriteTvFragment;

public class FavoritePagerAdapter extends FragmentStatePagerAdapter {
    private final int pageCount;
    public FavoritePagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.pageCount = pageCount;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FavoriteMovieFragment();
            case 1:
                return new FavoriteTvFragment();
            default:
                return null;
        }

    }
}
