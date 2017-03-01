package pl.wasper.popularmovies.converter;

import android.content.ContentValues;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.wasper.popularmovies.data.FavoritesMovieContract.FavoriteMovieEntry;
import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 24.02.17.
 */

public class MoviesConverter {
    private static final String TAG_RESULTS = "results";

    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_POSTER_PATH = "poster_path";
    private static final String TAG_ORIGINAL_TITLE = "original_title";
    private static final String TAG_RELEASE_DATE = "release_date";
    private static final String TAG_VOTE_AVERAGE = "vote_average";
    private static final String TAG_OVERVIEW = "overview";

    public static ArrayList<Movie> listFromCursor(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            do {
                movies.add(singleFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        return movies;
    }

    public static Movie singleFromCursor(Cursor cursor) {
        Movie movie = new Movie();

        if (cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID) != -1) {
            movie.setId(cursor.getInt(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID))
            );
        }
        if (cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_TITLE) != -1) {
            movie.setTitle(cursor.getString(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_TITLE))
            );
        }
        if (cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_POSTER_PATH) != -1) {
            movie.setPosterPath(cursor.getString(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_POSTER_PATH))
            );
        }
        if (cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_ORIGINAL_TITLE) != -1) {
            movie.setOrginalTitle(cursor.getString(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_ORIGINAL_TITLE))
            );
        }
        if (cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_RELEASE_DATE) != -1) {
            movie.setReleaseDate(cursor.getString(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_RELEASE_DATE))
            );
        }
        if (cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_VOTE_AVERAGE) != -1) {
            movie.setVoteAverage(cursor.getDouble(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_VOTE_AVERAGE))
            );
        }
        if (cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_OVERVIEW) != -1) {
            movie.setOverview(cursor.getString(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_NAME_OVERVIEW))
            );
        }

        return movie;
    }

    public static ArrayList<Movie> listFromJson(String jsonString) {
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray results = object.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);

                Movie movie = new Movie();
                movie.setId(element.getInt(TAG_ID));
                movie.setTitle(element.getString(TAG_TITLE));
                movie.setOrginalTitle(element.getString(TAG_ORIGINAL_TITLE));
                movie.setReleaseDate(element.getString(TAG_RELEASE_DATE));

                movie.setVoteAverage(element.getDouble(TAG_VOTE_AVERAGE));
                movie.setOverview(element.getString(TAG_OVERVIEW));
                movie.setPosterPath(element.getString(TAG_POSTER_PATH));

                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public static ContentValues singleToContentValues(Movie movie) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID, movie.getId());
        contentValues.put(FavoriteMovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        contentValues.put(FavoriteMovieEntry.COLUMN_NAME_ORIGINAL_TITLE, movie.getOrginalTitle());
        contentValues.put(FavoriteMovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(FavoriteMovieEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(FavoriteMovieEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
        contentValues.put(FavoriteMovieEntry.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());

        return contentValues;
    }
}
