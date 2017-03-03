package pl.wasper.popularmovies.task.callback;

import java.util.ArrayList;

import pl.wasper.popularmovies.domain.MovieTrailer;

/**
 * Created by wasper on 02.03.17.
 */

public interface IMovieTrailersListCallback extends IListCallback {
    void adaptElements(ArrayList<MovieTrailer> trailers);
}
