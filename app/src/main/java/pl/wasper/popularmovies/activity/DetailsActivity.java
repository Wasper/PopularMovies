package pl.wasper.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.activity.fragment.DetailsFragment;
import pl.wasper.popularmovies.domain.Movie;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(ListActivity.MOVIE_EXTRA_KEY);

        Bundle bundle = new Bundle();
        bundle.putParcelable(ListActivity.MOVIE_EXTRA_KEY, movie);

        Fragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        getSupportFragmentManager().
            beginTransaction()
            .add(R.id.details_fragment, detailsFragment)
            .commit();

    }
}
