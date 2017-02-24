package pl.wasper.popularmovies.task;

import android.content.Context;

import java.util.ArrayList;

import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 26.01.17.
 */

public interface IListCallback {
    void adaptElements(ArrayList<Movie> movies);
    Context getApplicationContext();
    void showConnectError();
    void showParseError();
    void showProgressBar();
    void hideProgressBar();
}
