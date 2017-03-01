package pl.wasper.popularmovies.activity.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindBool;
import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.activity.DetailsActivity;
import pl.wasper.popularmovies.adapter.IListItemClickListener;
import pl.wasper.popularmovies.adapter.ListAdapter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.domain.SortType;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.task.FavoriteMoviesListTask;
import pl.wasper.popularmovies.task.IListCallback;
import pl.wasper.popularmovies.task.MoviesListTask;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by wasper on 28.02.17.
 */

public class ListFragment extends Fragment
    implements IListItemClickListener, IListCallback{

    public static final String MOVIE_EXTRA_KEY = "Movie";
    private static final String SORT_KEY = "sort_type";
    private static final String PREFERENCES_NAME = "app_preferences";

    @BindView(R.id.list_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.error) TextView mError;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindInt(R.integer.column_count) int columnCount;
    @BindBool(R.bool.use_tablet_view) boolean useTabletView;

    private ListAdapter mAdapter;
    private SortType currentSortType = SortType.TOP_RATED;
    private SharedPreferences preferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_list, container);
        ButterKnife.bind(this, view);

        preferences = view.getContext().getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        loadSortType();

        prepareRecyclerView(view);
        prepareList();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_rated_item:
                currentSortType = SortType.TOP_RATED;
                break;
            case R.id.most_popular_item:
                currentSortType = SortType.POPULAR;
                break;
            case R.id.favorites_item:
                currentSortType = SortType.FAVORITES;
                break;
        }

        saveSortType();
        prepareList();

        return super.onOptionsItemSelected(item);
    }

    private void prepareRecyclerView(View view) {
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(
            view.getContext(),
            columnCount
        );
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void prepareList() {
        switch (currentSortType) {
            case TOP_RATED:
            case POPULAR:
                URL url = URLBuilder.buildSortedListUrl(currentSortType);

                if (url == null) {
                    showApiKeyError();
                } else {
                    new MoviesListTask(this).execute(url);
                }
                break;
            case FAVORITES:
                new FavoriteMoviesListTask(this).execute();
                break;
        }
    }

    private void loadSortType() {
        currentSortType = SortType.valueOf(preferences.getString(
            SORT_KEY,
            currentSortType.toString()
        ));
    }

    private void saveSortType() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SORT_KEY, currentSortType.toString());
        editor.apply();
    }

    @Override
    public void onListItemClick(View view, Movie movie) {
        if (useTabletView) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MOVIE_EXTRA_KEY, movie);

            Fragment detailFragment = new DetailsFragment();
            detailFragment.setArguments(bundle);

            getFragmentManager()
                .beginTransaction()
                .replace(R.id.details_fragment, detailFragment)
                .commit();
        } else {
            Intent intent = new Intent(view.getContext(), DetailsActivity.class);
            intent.putExtra(MOVIE_EXTRA_KEY, movie);

            startActivity(intent);
        }
    }

    @Override
    public void adaptElements(ArrayList<Movie> movies) {
        mAdapter.setMoviesList(movies);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showConnectError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setText(R.string.connection_error);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showParseError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setText(R.string.parsing_error);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showApiKeyError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setText(R.string.api_key_error);
        mError.setVisibility(View.VISIBLE);
    }
}
