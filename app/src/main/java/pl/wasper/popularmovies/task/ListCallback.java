package pl.wasper.popularmovies.task;

import java.util.ArrayList;
import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 26.01.17.
 */

public interface ListCallback {
    public void adaptElements(ArrayList<Movie> movies);
}
