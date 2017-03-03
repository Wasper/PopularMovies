package pl.wasper.popularmovies.task;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import pl.wasper.popularmovies.converter.TrailerConverter;
import pl.wasper.popularmovies.domain.MovieTrailer;
import pl.wasper.popularmovies.network.NetworkTool;
import pl.wasper.popularmovies.task.callback.IMovieTrailersListCallback;

/**
 * Created by wasper on 28.02.17.
 */

public class MovieTrailersTask extends AsyncTask<URL, Void, ArrayList<MovieTrailer>> {
    private IMovieTrailersListCallback mMovieTrailersListCallback;

    public MovieTrailersTask(IMovieTrailersListCallback movieTrailersListCallback) {
        this.mMovieTrailersListCallback = movieTrailersListCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mMovieTrailersListCallback.showProgressBar();
    }

    @Override
    protected ArrayList<MovieTrailer> doInBackground(URL... params) {
        URL url = params[0];

        String response = NetworkTool.getUrlResponse(url);

        if (response == null) {
            return null;
        }

        return TrailerConverter.listFromJson(response);
    }

    @Override
    protected void onPostExecute(ArrayList<MovieTrailer> trailers) {
        mMovieTrailersListCallback.hideProgressBar();

        if (trailers == null) {
            mMovieTrailersListCallback.showConnectError();
        } else if (trailers.isEmpty()) {
            mMovieTrailersListCallback.showParseError();
        } else {
            mMovieTrailersListCallback.adaptElements(trailers);
        }
    }
}
