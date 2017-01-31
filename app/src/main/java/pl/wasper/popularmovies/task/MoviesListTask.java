package pl.wasper.popularmovies.task;

import android.os.AsyncTask;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;

import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.NetworkTool;
import pl.wasper.popularmovies.parser.JSONParser;

/**
 * Created by wasper on 25.01.17.
 */

public class MoviesListTask extends AsyncTask<URL, Void, ArrayList<Movie>> {
    private ListCallback mListCallback;

    public MoviesListTask(ListCallback listCallback) {
        this.mListCallback = listCallback;
    }

    @Override
    protected ArrayList<Movie> doInBackground(URL... params) {
        URL url = params[0];
        ArrayList<Movie> movies = new ArrayList<Movie>();
        String response = NetworkTool.getUrlResponse(url);

        if (response == null) {
            return null;
        }

        try {
            movies = JSONParser.parse(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mListCallback.showProgressBar();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        mListCallback.hideProgressBar();

        if (movies == null) {
            mListCallback.showConnectError();
        } else if (movies.isEmpty()) {
            mListCallback.showParseError();
        } else {
            mListCallback.adaptElements(movies);
        }
    }
}
