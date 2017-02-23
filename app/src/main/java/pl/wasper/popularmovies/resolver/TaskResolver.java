package pl.wasper.popularmovies.resolver;

import android.os.AsyncTask;

import pl.wasper.popularmovies.task.FavoriteMoviesListTask;
import pl.wasper.popularmovies.task.IListCallback;
import pl.wasper.popularmovies.task.MoviesListTask;

/**
 * Created by wasper on 23.02.17.
 */

public class TaskResolver {
    public static AsyncTask resolve(
        SortType type,
        IListCallback IListCallback
    ) {
        switch (type) {
            case POPULAR:
            case TOP_RATED:
                return new MoviesListTask(IListCallback);
            case FAVORITES:
                return new FavoriteMoviesListTask(IListCallback);
            default:
                throw new UnsupportedOperationException("Unexpected sort type");
        }
    }
}
