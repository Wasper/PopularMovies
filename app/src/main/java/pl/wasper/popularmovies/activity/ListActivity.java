package pl.wasper.popularmovies.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.adapter.IListItemClickListener;
import pl.wasper.popularmovies.adapter.ListAdapter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.domain.SortType;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.task.FavoriteMoviesListTask;
import pl.wasper.popularmovies.task.IListCallback;
import pl.wasper.popularmovies.task.MoviesListTask;

import static pl.wasper.popularmovies.domain.SortType.FAVORITES;
import static pl.wasper.popularmovies.domain.SortType.POPULAR;
import static pl.wasper.popularmovies.domain.SortType.TOP_RATED;

public class ListActivity extends AppCompatActivity
    implements IListCallback, IListItemClickListener {

    public static final String INTENT_EXTRA_KEY = "Movie";
    private static final String SORT_KEY = "sort_type";
    private static final String PREFERENCES_NAME = "app_preferences";

    @BindView(R.id.list_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.error) TextView mError;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    private ListAdapter mAdapter;
    private SortType currentSortType = TOP_RATED;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        loadSortType();

        prepareRecyclerView();
        prepareList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_rated_item:
                currentSortType = TOP_RATED;
                break;
            case R.id.most_popular_item:
                currentSortType = POPULAR;
                break;
            case R.id.favorites_item:
                currentSortType = FAVORITES;
                break;
        }

        saveSortType();
        prepareList();

        return super.onOptionsItemSelected(item);
    }

    private void saveSortType() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SORT_KEY, currentSortType.toString());
        editor.apply();
    }

    private void loadSortType() {
        currentSortType = SortType.valueOf(preferences.getString(
                SORT_KEY,
                currentSortType.toString()
        ));
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

    private void prepareRecyclerView() {
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(INTENT_EXTRA_KEY, movie);

        startActivity(intent);
    }
}
