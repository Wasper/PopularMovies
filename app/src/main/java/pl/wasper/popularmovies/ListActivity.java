package pl.wasper.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;
import java.util.ArrayList;

import pl.wasper.popularmovies.adapter.ListAdapter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.task.ListCallback;
import pl.wasper.popularmovies.task.MoviesListTask;

public class ListActivity extends AppCompatActivity
    implements ListCallback, ListAdapter.ListItemClickListener {

    private static final String SORT_KEY = "sort_type";

    private URL url;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private String currentSortType = URLBuilder.TOP_RATED_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        prepareRecyclerView();
        Log.d("TEST", "TEST");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SORT_KEY, currentSortType);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.getString(SORT_KEY) != null)
            currentSortType = savedInstanceState.getString(SORT_KEY);

        /** not in onCreate() because it's called after onStart() */
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

        prepareListSortedBy(currentSortType);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void adaptElements(ArrayList<Movie> movies) {
        mAdapter.setMoviesList(movies);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void prepareListSortedBy(String sortType) {
        url = URLBuilder.buildUrl(sortType);
        new MoviesListTask(this).execute(url);
    }

    private void prepareRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("Movie", movie);

        startActivity(intent);
    }
}
