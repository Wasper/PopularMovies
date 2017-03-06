package pl.wasper.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindBool;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.activity.fragment.MovieFragment;

public class DetailsActivity extends AppCompatActivity {
    @BindBool(R.bool.use_tablet_view) boolean useTabletView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (!useTabletView) {
            MovieFragment movieFragment = new MovieFragment();
            movieFragment.setArguments(bundle);

            getSupportFragmentManager().
                beginTransaction()
                .add(R.id.movie_fragment, movieFragment)
                .commit();
        } else {
            Intent listActivityIntent = new Intent(this, ListActivity.class);
            listActivityIntent.putExtras(bundle);

            startActivity(listActivityIntent);
        }
    }
}
