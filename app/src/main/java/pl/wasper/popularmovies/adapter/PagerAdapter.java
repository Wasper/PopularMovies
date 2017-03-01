package pl.wasper.popularmovies.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pl.wasper.popularmovies.activity.fragment.MovieDetailsFragment;
import pl.wasper.popularmovies.activity.fragment.MovieReviewsFragment;
import pl.wasper.popularmovies.activity.fragment.MovieTrailersFragment;

/**
 * Created by wasper on 01.03.17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public static final int TABS_COUNT = 3;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new MovieDetailsFragment();
            case 1:
                return new MovieTrailersFragment();
            case 2:
                return new MovieReviewsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }
}
