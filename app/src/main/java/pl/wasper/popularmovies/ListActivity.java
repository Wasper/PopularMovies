package pl.wasper.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.net.URL;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.task.MoviesListTask;

public class ListActivity extends AppCompatActivity {

    private static final String TOP_RATED_PATH = "top_rated";
    private static final String POPULAR_PATH = "popular";
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        URL url = URLBuilder.buildUrl(TOP_RATED_PATH);
        new MoviesListTask().execute(url);
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
        url = URLBuilder.buildUrl(POPULAR_PATH);
        new MoviesListTask().execute(url);
    }

    public void sortByTopRated() {
        url = URLBuilder.buildUrl(TOP_RATED_PATH);
        new MoviesListTask().execute(url);
    }
}
