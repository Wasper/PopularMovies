package pl.wasper.popularmovies.task;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by wasper on 28.02.17.
 */

public class MovieVideosTask extends AsyncTask<URL, Void, ArrayList<String>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(URL... params) {
        URL url = params[0];

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
    }
}
