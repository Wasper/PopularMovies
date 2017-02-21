package pl.wasper.popularmovies.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import pl.wasper.popularmovies.data.FavoritesMovieDbHelper;
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

    private FavoritesMovieDbHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDbHelper = new FavoritesMovieDbHelper(this);
        db = mDbHelper.getWritableDatabase();

        title = (TextView) findViewById(R.id.movie_title);
        originalTitle = (TextView) findViewById(R.id.movie_original_title);
        releaseDate = (TextView) findViewById(R.id.movie_release_date);
        voteAverage = (TextView) findViewById(R.id.movie_vote_average);
        overview = (TextView) findViewById(R.id.movie_overview);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(ListActivity.INTENT_EXTRA_KEY);

        title.setText(movie.getTitle());

        moviePoster = (ImageView) findViewById(R.id.details_movie_poster);

        Picasso.with(this)
            .load(PosterURLBuilder.build(movie.getPosterPath()))
            .into(moviePoster);

        originalTitle.setText(movie.getOrginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        voteAverage.setText(buildVoteAverage(movie));
        overview.setText(movie.getOverview());


        favorites = (Button) findViewById(R.id.favorites_button);

        if (favoriteMovieExists(db, movie.getId())) {
            favorites.setText(getText(R.string.remove_from_favorites));
        }
    }

    @Override
    protected void onDestroy() {
        mDbHelper.close();
        super.onDestroy();
    }

    private String buildVoteAverage(Movie movie) {
        return String.format("%.1f / 10", movie.getVoteAverage());
    }

    public void addOrRemoveRecord(View view) {
        int id = movie.getId();
        String title = movie.getTitle();

        if (!favoriteMovieExists(db, id)) {
            addRecord(db, id, title);
            prepareActiveButton();
        } else {
            removeRecord(db, id);
            preprareInactiveButton();
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

    private boolean favoriteMovieExists(SQLiteDatabase db, int movieId) {
        String selection = FavoritesMovieContract.WaitlistMovieEntry.COLUMN_NAME_MOVIE_ID + " = ?";
        String[] selectionArgs = new String[] { String.valueOf(movieId) };

        boolean isAdded;

        Cursor data = db.query(
            FavoritesMovieContract.WaitlistMovieEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null);

        isAdded = data.getCount() > 0;

        data.close();

        return isAdded;
    }

    private long addRecord(SQLiteDatabase db, int movieId, String title) {
        ContentValues values = new ContentValues();
        values.put(FavoritesMovieContract.WaitlistMovieEntry.COLUMN_NAME_TITLE, title);
        values.put(FavoritesMovieContract.WaitlistMovieEntry.COLUMN_NAME_MOVIE_ID, movieId);

        return db.insert(FavoritesMovieContract.WaitlistMovieEntry.TABLE_NAME, null, values);
    }

    private int removeRecord(SQLiteDatabase db, int movieId) {
        return db.delete(FavoritesMovieContract.WaitlistMovieEntry.TABLE_NAME,
            FavoritesMovieContract.WaitlistMovieEntry.COLUMN_NAME_MOVIE_ID + "=" + movieId, null);
    }
}
