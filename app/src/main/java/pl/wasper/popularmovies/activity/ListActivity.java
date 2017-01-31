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

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.adapter.ListAdapter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.task.ListCallback;
import pl.wasper.popularmovies.task.MoviesListTask;

public class ListActivity extends AppCompatActivity
    implements ListCallback, ListAdapter.ListItemClickListener {

    public static final String INTENT_EXTRA_KEY = "Movie";
    private static final String SORT_KEY = "sort_type";
    private static final String PREFERENCES_NAME = "app_preferences";

    private RecyclerView mRecyclerView;
    private TextView mError;
    private ProgressBar progressBar;
    private ListAdapter mAdapter;
    private String currentSortType = URLBuilder.TOP_RATED_PATH;

    /** I used it because i want to save sort type when user back from detail activity */
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        loadSortType();

        prepareRecyclerView();
        prepareListSortedBy(currentSortType);
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
                currentSortType = URLBuilder.TOP_RATED_PATH;
                break;
            case R.id.most_popular_item:
                currentSortType = URLBuilder.POPULAR_PATH;
                break;
        }

        saveSortType(currentSortType);
        prepareListSortedBy(currentSortType);

        return super.onOptionsItemSelected(item);
    }

    private void saveSortType(String currentSortType) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SORT_KEY, currentSortType);
        editor.apply();
    }

    private void loadSortType() {
        currentSortType = preferences.getString(SORT_KEY, currentSortType);
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

    public void prepareListSortedBy(String sortType) {
        URL url = URLBuilder.buildUrl(sortType);

        if (url == null) {
            showApiKeyError();
        } else {
            new MoviesListTask(this).execute(url);
        }
    }

    private void prepareRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mError = (TextView) findViewById(R.id.error);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

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
