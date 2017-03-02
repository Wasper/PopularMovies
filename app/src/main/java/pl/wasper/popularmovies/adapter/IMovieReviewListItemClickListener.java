package pl.wasper.popularmovies.adapter;

import android.view.View;

import pl.wasper.popularmovies.domain.MovieReview;

/**
 * Created by wasper on 02.03.17.
 */

public interface IMovieReviewListItemClickListener {
    void onListItemClick(View view, MovieReview movieReview);
}
