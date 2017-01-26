package pl.wasper.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import java.net.URL;
import java.util.ArrayList;

import pl.wasper.popularmovies.adapter.ListAdapter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.task.ListCallback;
import pl.wasper.popularmovies.task.MoviesListTask;

public class ListActivity extends AppCompatActivity implements ListCallback {

    private URL url;
    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        sortByTopRated();
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
                sortByTopRated();
                break;
            case R.id.most_popular_item:
                sortByPopular();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sortByPopular() {
        url = URLBuilder.buildUrl(URLBuilder.POPULAR_PATH);
        new MoviesListTask(this).execute(url);
    }

    public void sortByTopRated() {
        url = URLBuilder.buildUrl(URLBuilder.TOP_RATED_PATH);
        new MoviesListTask(this).execute(url);
    }

    @Override
    public void adaptElements(ArrayList<Movie> movies) {
        mAdapter.setMoviesList(movies);
        mRecyclerView.setAdapter(mAdapter);
    }
}
