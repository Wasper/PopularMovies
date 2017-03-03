package pl.wasper.popularmovies.domain;

/**
 * Created by wasper on 02.03.17.
 */

public class MovieTrailer {
    private int movieId;
    private String trailerId;
    private String trailerTitle;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getTrailerTitle() {
        return trailerTitle;
    }

    public void setTrailerTitle(String trailerTitle) {
        this.trailerTitle = trailerTitle;
    }
}
