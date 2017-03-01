package pl.wasper.popularmovies.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.activity.fragment.MovieDetailsFragment;
import pl.wasper.popularmovies.activity.fragment.MovieReviewsFragment;
import pl.wasper.popularmovies.activity.fragment.MovieTrailersFragment;

/**
 * Created by wasper on 01.03.17.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    public static final int TABS_COUNT = 3;

    private Bundle mBundle;
    private FragmentManager mFragmentManager;

    public PagerAdapter(FragmentManager fragmentManager, Bundle bundle) {
        super(fragmentManager);

        this.mFragmentManager = fragmentManager;
        this.mBundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
                movieDetailsFragment.setArguments(mBundle);
                return movieDetailsFragment;
            case 1:
                MovieTrailersFragment movieTrailersFragment = new MovieTrailersFragment();
                movieTrailersFragment.setArguments(mBundle);
                return movieTrailersFragment;
            case 2:
                MovieReviewsFragment movieReviewsFragment = new MovieReviewsFragment();
                movieReviewsFragment.setArguments(mBundle);
                return movieReviewsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    private void addDetailsFragment(Bundle bundle) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(bundle);

        mFragmentManager
            .beginTransaction()
            .add(R.id.movie_details, movieDetailsFragment)
            .commit();
    }
}
