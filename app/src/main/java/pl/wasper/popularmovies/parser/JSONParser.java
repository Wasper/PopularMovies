package pl.wasper.popularmovies.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 25.01.17.
 */

public class JSONParser {
    private static final String TAG_RESULTS = "results";
    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_ORIGINAL_TITLE = "original_title";
    private static final String TAG_RELEASE_DATE = "release_date";
    private static final String TAG_VOTE_AVERAGE = "vote_average";
    private static final String TAG_OVERVIEW = "overview";
    private static final String TAG_POSTER_PATH = "poster_path";


    public static ArrayList<Movie> parse(String jsonString) throws JSONException {
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        JSONObject object = new JSONObject(jsonString);
        JSONArray results = object.getJSONArray(TAG_RESULTS);

        for (int i = 0; i < results.length(); i++) {
            JSONObject element = results.getJSONObject(i);

            Movie movie = new Movie(element.getInt(TAG_ID));
            movie.setTitle(element.getString(TAG_TITLE));
            movie.setOrginalTitle(element.getString(TAG_ORIGINAL_TITLE));
            movie.setReleaseDate(element.getString(TAG_RELEASE_DATE));

            movie.setVoteAverage(element.getDouble(TAG_VOTE_AVERAGE));
            movie.setOverview(element.getString(TAG_OVERVIEW));
            movie.setPosterPath(element.getString(TAG_POSTER_PATH));

            movieList.add(movie);
        }

        return movieList;
    }
}
