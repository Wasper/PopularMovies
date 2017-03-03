package pl.wasper.popularmovies.adapter;

import android.view.View;

import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 23.02.17.
 */

public interface IMovieListItemClickListener {
    void onListItemClick(View view, Movie movie);
}
