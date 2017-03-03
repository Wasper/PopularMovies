package pl.wasper.popularmovies.task;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import pl.wasper.popularmovies.converter.MoviesConverter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.NetworkTool;
import pl.wasper.popularmovies.task.callback.IMovieListCallback;

/**
 * Created by wasper on 25.01.17.
 */

public class MoviesListTask extends AsyncTask<URL, Void, ArrayList<Movie>> {
    private IMovieListCallback mIMovieListCallback;

    public MoviesListTask(IMovieListCallback IMovieListCallback) {
        this.mIMovieListCallback = IMovieListCallback;
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... params) {
        URL url = params[0];

        String response = NetworkTool.getUrlResponse(url);

        if (response == null) {
            return null;
        }

        return MoviesConverter.listFromJson(response);
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
            mIMovieListCallback.showParseError();
        } else {
            mIMovieListCallback.adaptElements(movies);
        }
    }
}
