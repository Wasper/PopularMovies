package pl.wasper.popularmovies.activity.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.adapter.PagerAdapter;

/**
 * Created by wasper on 01.03.17.
 */

public class MovieFragment extends Fragment {
    @BindBool(R.bool.use_tablet_view) boolean useTabletView;
    @BindView(R.id.fragment_movie_tabs) @Nullable TabLayout mTabLayout;
    @BindView(R.id.fragment_movie_view_pager) @Nullable ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        if (!isTabletView()) {
            prepareDefaultView(bundle);
        } else {
            prepareTabletView(bundle);
        }

        return view;
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putAll(getArguments());
//    }

    private boolean isTabletView() {
        return useTabletView && mTabLayout != null && mViewPager != null;
    }

    private void prepareTabletView(Bundle bundle) {
        mTabLayout.addTab(mTabLayout.newTab().setText(getText(R.string.details_title)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getText(R.string.trailers_title)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getText(R.string.reviews_title)));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(), bundle);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        mViewPager.addOnPageChangeListener(
            new TabLayout.TabLayoutOnPageChangeListener(mTabLayout)
        );
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void prepareDefaultView(Bundle bundle) {
        addDetailsFragment(bundle);
        addTrailersFragment(bundle);
        addReviewsFragment(bundle);
    }

    private void addDetailsFragment(Bundle bundle) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(bundle);

        getChildFragmentManager()
            .beginTransaction()
            .add(R.id.movie_details_default, movieDetailsFragment)
            .commit();
    }

    private void addTrailersFragment(Bundle bundle) {
        MovieTrailersFragment movieTrailersFragment = new MovieTrailersFragment();
        movieTrailersFragment.setArguments(bundle);

        getChildFragmentManager()
            .beginTransaction()
            .add(R.id.movie_trailers_default, movieTrailersFragment)
            .commit();
    }

    private void addReviewsFragment(Bundle bundle) {
        MovieReviewsFragment movieReviewsFragment = new MovieReviewsFragment();
        movieReviewsFragment.setArguments(bundle);

        getChildFragmentManager()
            .beginTransaction()
            .add(R.id.movie_reviews_default, movieReviewsFragment)
            .commit();
    }
}
