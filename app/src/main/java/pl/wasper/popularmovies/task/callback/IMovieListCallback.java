package pl.wasper.popularmovies.task.callback;

import java.util.ArrayList;

import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 26.01.17.
 */

public interface IMovieListCallback extends IListCallback {
    void adaptElements(ArrayList<Movie> movies);
}
