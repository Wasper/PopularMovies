package pl.wasper.popularmovies.task;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.NetworkTool;
import pl.wasper.popularmovies.parser.JSONParser;

/**
 * Created by wasper on 25.01.17.
 */

public class MoviesListTask extends AsyncTask<URL, Void, String> {
    private ListCallback mListCallback;

    public MoviesListTask(ListCallback listCallback) {
        this.mListCallback = listCallback;
    }

    @Override
    protected String doInBackground(URL... params) {
        URL url = params[0];
        String response = null;

        try {
            response = NetworkTool.getUrlResponse(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String jsonMovies) {

        JSONParser parser = new JSONParser(jsonMovies);
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            movies = parser.parse();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mListCallback.adaptElements(movies);
    }
}
