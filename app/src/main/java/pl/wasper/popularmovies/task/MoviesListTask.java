package pl.wasper.popularmovies.task;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

import pl.wasper.popularmovies.converter.MoviesConverter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.NetworkTool;

/**
 * Created by wasper on 25.01.17.
 */

public class MoviesListTask extends AsyncTask<URL, Void, ArrayList<Movie>> {
    private IListCallback mIListCallback;

    public MoviesListTask(IListCallback IListCallback) {
        this.mIListCallback = IListCallback;
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
