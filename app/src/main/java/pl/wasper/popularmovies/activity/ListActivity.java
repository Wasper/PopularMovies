package pl.wasper.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindBool;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.activity.fragment.MovieFragment;
import pl.wasper.popularmovies.activity.fragment.MovieListFragment;

public class ListActivity extends AppCompatActivity {
    @BindBool(R.bool.use_tablet_view) boolean useTabletView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            if (!useTabletView) {
                MovieFragment movieFragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.movie_fragment);
                Bundle details = movieFragment.getArguments();
                Intent detailsIntent = new Intent(this, DetailsActivity.class);
                detailsIntent.putExtras(details);
                startActivity(detailsIntent);
            }
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        MovieListFragment fragment = new MovieListFragment();

        getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.list_fragment, fragment)
            .commit();

        if (useTabletView && bundle != null) {
            MovieFragment movieFragment = new MovieFragment();
            movieFragment.setArguments(bundle);

            getSupportFragmentManager().
                beginTransaction()
                .add(R.id.movie_fragment, movieFragment)
                .commit();
        }
    }
}
