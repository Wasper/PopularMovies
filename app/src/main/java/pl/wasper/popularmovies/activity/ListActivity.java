package pl.wasper.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.activity.fragment.MovieListFragment;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        MovieListFragment fragment = new MovieListFragment();

        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.list_fragment, fragment)
            .commit();
    }
}
