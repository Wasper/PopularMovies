package pl.wasper.popularmovies.task.callback;

import java.util.ArrayList;

import pl.wasper.popularmovies.domain.MovieReview;

/**
 * Created by wasper on 02.03.17.
 */

public interface IMovieReviewsListCallback extends IListCallback {
    void adaptElements(ArrayList<MovieReview> reviews);
}
