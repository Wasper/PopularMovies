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

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.data.FavoritesMovieContract;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.PosterURLBuilder;

public class DetailsActivity extends AppCompatActivity {
    private TextView title;
    private TextView originalTitle;
    private TextView releaseDate;
    private TextView voteAverage;
    private TextView overview;
    private ImageView moviePoster;
    private Button favorites;

    private Movie movie;
    private boolean hasRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title = (TextView) findViewById(R.id.movie_title);
        originalTitle = (TextView) findViewById(R.id.movie_original_title);
        releaseDate = (TextView) findViewById(R.id.movie_release_date);
        voteAverage = (TextView) findViewById(R.id.movie_vote_average);
        overview = (TextView) findViewById(R.id.movie_overview);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(ListActivity.INTENT_EXTRA_KEY);

        title.setText(movie.getTitle());
        originalTitle.setText(movie.getOrginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        voteAverage.setText(buildVoteAverage(movie));
        overview.setText(movie.getOverview());
        favorites = (Button) findViewById(R.id.favorites_button);

        moviePoster = (ImageView) findViewById(R.id.details_movie_poster);

        Picasso.with(this)
            .load(PosterURLBuilder.build(movie.getPosterPath()))
            .into(moviePoster);



        hasRecord = hasRecord(movie.getId());

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
            addRecord(id, title);
            prepareActiveButton();
            hasRecord = true;
        } else {
            removeRecord(id);
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

    private Cursor getRecord(int movieId) {

        try {
            return getContentResolver().query(
                buildContentUriWithId(movieId),
                null,
                null,
                null,
                null
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            //TODO (1) obsluzyc wyjatek bledu sql
        }
    }

    private boolean hasRecord(int movieId) {
        return getRecord(movieId).getCount() > 0;
    }

    private Uri addRecord(int movieId, String title) {
        ContentValues values = new ContentValues();
        values.put(FavoritesMovieContract.FavoriteMovieEntry.COLUMN_NAME_TITLE, title);
        values.put(FavoritesMovieContract.FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID, movieId);

        return getContentResolver().insert(
            FavoritesMovieContract.FavoriteMovieEntry.CONTENT_URI,
            values
        );

        //TODO (2) obsluzyc wyjatek bledu przy zapisie
    }

    private int removeRecord(int movieId) {
        return getContentResolver().delete(buildContentUriWithId(movieId), null, null);
    }

    private Uri buildContentUriWithId(int movieId) {
        return FavoritesMovieContract.FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(String.valueOf(movieId))
            .build();
    }
}
