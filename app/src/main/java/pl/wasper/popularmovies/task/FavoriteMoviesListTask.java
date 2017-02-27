package pl.wasper.popularmovies.task;

import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import pl.wasper.popularmovies.AppApplication;
import pl.wasper.popularmovies.converter.MoviesConverter;
import pl.wasper.popularmovies.data.FavoritesMovieContract;
import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 22.02.17.
 */

public class FavoriteMoviesListTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private IListCallback mIListCallback;

    public FavoriteMoviesListTask(IListCallback IListCallback) {
        mIListCallback = IListCallback;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        Cursor cursor = null;
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            cursor = AppApplication.app.getContentResolver().query(
                FavoritesMovieContract.FavoriteMovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                movies = MoviesConverter.listFromCursor(cursor);
                cursor.close();
            }
        }

        return movies;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mIListCallback.showProgressBar();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        mIListCallback.hideProgressBar();

        if (movies == null) {
            mIListCallback.showConnectError();
        } else if (movies.isEmpty()) {
            mIListCallback.showParseError();
        } else {
            mIListCallback.adaptElements(movies);
        }
    }
}
