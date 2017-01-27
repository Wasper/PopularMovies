package pl.wasper.popularmovies.listener;

import android.view.View;

import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 27.01.17.
 */

public class MovieElementOnClickListener implements View.OnClickListener {
    private Movie mMovie;

    public MovieElementOnClickListener(Movie movie) {
        mMovie = movie;
    }

    @Override
    public void onClick(View v) {

    }

    public Movie getMovie() {
        return mMovie;
    }
}
