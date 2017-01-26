package pl.wasper.popularmovies.network;

/**
 * Created by wasper on 26.01.17.
 */

public class PosterURLBuilder {
    public static final String POSTER_HOST = "http://image.tmdb.org/t/p/w185";

    public static String build(String poster) {
        return POSTER_HOST + poster;
    }
}
