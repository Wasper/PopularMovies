package pl.wasper.popularmovies.task;

import android.database.Cursor;
import android.os.AsyncTask;

import java.util.ArrayList;

import pl.wasper.popularmovies.AppApplication;
import pl.wasper.popularmovies.converter.MoviesConverter;
import pl.wasper.popularmovies.data.FavoritesMovieContract;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.task.callback.IMovieListCallback;

/**
 * Created by wasper on 22.02.17.
 */

public class FavoriteMoviesListTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private IMovieListCallback mIMovieListCallback;

    public FavoriteMoviesListTask(IMovieListCallback IMovieListCallback) {
        mIMovieListCallback = IMovieListCallback;
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

        mIMovieListCallback.showProgressBar();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        mIMovieListCallback.hideProgressBar();

        if (movies == null) {
            mIMovieListCallback.showConnectError();
        } else if (movies.isEmpty()) {
            mIMovieListCallback.showEmptyListInfo();
        } else {
            mIMovieListCallback.adaptElements(movies);
        }
    }
}
