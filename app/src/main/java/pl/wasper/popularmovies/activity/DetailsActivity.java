package pl.wasper.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.PosterURLBuilder;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView title = (TextView) findViewById(R.id.movie_title);
        TextView originalTitle = (TextView) findViewById(R.id.movie_original_title);
        TextView releaseDate = (TextView) findViewById(R.id.movie_release_date);
        TextView voteAverage = (TextView) findViewById(R.id.movie_vote_average);
        TextView overview = (TextView) findViewById(R.id.movie_overview);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(ListActivity.INTENT_EXTRA_KEY);

        title.setText(movie.getTitle());

        ImageView moviePoster = (ImageView) findViewById(R.id.details_movie_poster);

        Picasso.with(this)
            .load(PosterURLBuilder.build(movie.getPosterPath()))
            .into(moviePoster);

        originalTitle.setText(movie.getOrginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        voteAverage.setText(buildVoteAverage(movie));
        overview.setText(movie.getOverview());

    }

    private String buildVoteAverage(Movie movie) {
        return String.format("%.1f / 10", movie.getVoteAverage());
    }
}
