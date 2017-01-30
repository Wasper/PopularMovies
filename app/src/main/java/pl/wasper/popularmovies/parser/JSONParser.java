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
    private String jsonString;

    public JSONParser(String jsonString) {
        this.jsonString = jsonString;
    }

    public ArrayList<Movie> parse() throws JSONException {
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        JSONObject object = new JSONObject(jsonString);
        JSONArray results = object.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject element = results.getJSONObject(i);

            Movie movie = new Movie(element.getInt("id"));
            movie.setTitle(element.getString("title"));
            movie.setOrginalTitle(element.getString("original_title"));
            movie.setReleaseDate(element.getString("release_date"));

            movie.setVoteAverage(element.getDouble("vote_average"));
            movie.setOverview(element.getString("overview"));
            movie.setPosterPath(element.getString("poster_path"));

            movieList.add(movie);
        }

        return movieList;
    }
}
