package pl.wasper.popularmovies.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.wasper.popularmovies.domain.MovieTrailer;

/**
 * Created by wasper on 02.03.17.
 */

public class TrailerConverter {
    private static final String TAG_RESULTS = "results";

    private static final String TAG_MOVIE_ID = "id";
    private static final String TAG_TRAILER_ID = "key";
    private static final String TAG_TRAILER_TITLE = "name";

    public static ArrayList<MovieTrailer> listFromJson(String jsonString) {
        ArrayList<MovieTrailer> trailers = new ArrayList<MovieTrailer>();

        try {
            JSONObject object = new JSONObject(jsonString);

            int movieId = object.getInt(TAG_MOVIE_ID);

            JSONArray results = object.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);

                MovieTrailer trailer = new MovieTrailer();
                trailer.setMovieId(movieId);
                trailer.setTrailerId(element.getString(TAG_TRAILER_ID));
                trailer.setTrailerTitle(element.getString(TAG_TRAILER_TITLE));

                trailers.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }
}
