package pl.wasper.popularmovies.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.converter.MoviesConverter;
import pl.wasper.popularmovies.data.FavoritesMovieContract;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.PosterURLBuilder;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.movie_title) TextView title;
    @BindView(R.id.movie_original_title) TextView originalTitle;
    @BindView(R.id.movie_release_date) TextView releaseDate;
    @BindView(R.id.movie_vote_average) TextView voteAverage;
    @BindView(R.id.movie_overview) TextView overview;
    @BindView(R.id.details_movie_poster) ImageView moviePoster;
    @BindView(R.id.favorites_button) Button favorites;

    private Movie movie;
    private boolean hasRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(ListActivity.INTENT_EXTRA_KEY);

        title.setText(movie.getTitle());
        originalTitle.setText(movie.getOrginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        voteAverage.setText(buildVoteAverage(movie));
        overview.setText(movie.getOverview());

        Picasso.with(this)
            .load(PosterURLBuilder.build(movie.getPosterPath()))
            .into(moviePoster);



        hasRecord = hasRecord(movie);

        if (hasRecord) {
            favorites.setText(getText(R.string.remove_from_favorites));
        }
    }

    private String buildVoteAverage(Movie movie) {
        return String.format("%.1f / 10", movie.getVoteAverage());
    }

    public void addOrRemoveRecord(View view) {
        int id = movie.getId();
        String title = movie.getTitle();

        if (!hasRecord) {
            addRecord(movie);
            prepareActiveButton();
            hasRecord = true;
        } else {
            removeRecord(movie);
            preprareInactiveButton();
            hasRecord = false;
        }
    }

    private void preprareInactiveButton() {
        Toast.makeText(this, getText(R.string.removed_toast), Toast.LENGTH_LONG).show();
        favorites.setText(getText(R.string.add_to_favorites));
    }

    private void prepareActiveButton() {
        Toast.makeText(this, getText(R.string.added_toast), Toast.LENGTH_LONG).show();
        favorites.setText(getText(R.string.remove_from_favorites));
    }

    private Cursor getRecord(Movie movie) {

        try {
            return getContentResolver().query(
                buildContentUriWithId(movie.getId()),
                null,
                null,
                null,
                null
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean hasRecord(Movie movie) {
        return getRecord(movie).getCount() > 0;
    }

    private Uri addRecord(Movie movie) {
        ContentValues values = MoviesConverter.singleToContentValues(movie);

        return getContentResolver().insert(
            FavoritesMovieContract.FavoriteMovieEntry.CONTENT_URI,
            values
        );
    }

    private int removeRecord(Movie movie) {
        return getContentResolver().delete(buildContentUriWithId(movie.getId()), null, null);
    }

    private Uri buildContentUriWithId(int movieId) {
        return FavoritesMovieContract.FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(String.valueOf(movieId))
            .build();
    }
}
