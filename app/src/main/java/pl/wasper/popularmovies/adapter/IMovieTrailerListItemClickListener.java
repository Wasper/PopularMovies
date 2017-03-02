package pl.wasper.popularmovies.adapter;

import android.view.View;

import pl.wasper.popularmovies.domain.MovieTrailer;

/**
 * Created by wasper on 02.03.17.
 */

public interface IMovieTrailerListItemClickListener {
    void onListItemClick(View view, MovieTrailer movieTrailer);
}
