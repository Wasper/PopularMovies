package pl.wasper.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.activity.fragment.MovieFragment;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        MovieFragment movieFragment = new MovieFragment();
        movieFragment.setArguments(bundle);

        getSupportFragmentManager().
            beginTransaction()
            .add(R.id.movie_fragment, movieFragment)
            .commit();

    }
}
