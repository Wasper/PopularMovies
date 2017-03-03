package pl.wasper.popularmovies.task;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import pl.wasper.popularmovies.converter.ReviewConverter;
import pl.wasper.popularmovies.domain.MovieReview;
import pl.wasper.popularmovies.network.NetworkTool;
import pl.wasper.popularmovies.task.callback.IMovieReviewsListCallback;

/**
 * Created by wasper on 28.02.17.
 */

public class MovieReviewsTask extends AsyncTask<URL, Void, ArrayList<MovieReview>> {
    private IMovieReviewsListCallback mMovieReviewsListCallback;

    public MovieReviewsTask(IMovieReviewsListCallback movieReviewsListCallback) {
        this.mMovieReviewsListCallback = movieReviewsListCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mMovieReviewsListCallback.showProgressBar();
    }

    @Override
    protected ArrayList<MovieReview> doInBackground(URL... params) {
        URL url = params[0];

        String response = NetworkTool.getUrlResponse(url);

        if (response == null) {
            return null;
        }

        return ReviewConverter.listFromJson(response);
    }

    @Override
    protected void onPostExecute(ArrayList<MovieReview> movieReviews) {
        mMovieReviewsListCallback.hideProgressBar();

        if (movieReviews == null) {
            mMovieReviewsListCallback.showConnectError();
        } else if (movieReviews.isEmpty()) {
            mMovieReviewsListCallback.showParseError();
        } else {
            mMovieReviewsListCallback.adaptElements(movieReviews);
        }
    }
}
