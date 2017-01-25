package pl.wasper.popularmovies.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import pl.wasper.popularmovies.domain.Movie;

/**
 * Created by wasper on 25.01.17.
 */

public class JSONParser {
    private String jsonString;
    private JSONObject object;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public JSONParser(String jsonString) {
        this.jsonString = jsonString;
    }

    public ArrayList<Movie> parse() throws JSONException {
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        object = new JSONObject(jsonString);
        JSONArray results = object.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            JSONObject element = results.getJSONObject(i);

            Movie movie = new Movie(element.getInt("id"));
            movie.setTitle(element.getString("title"));
            movie.setOrginalTitle(element.getString("original_title"));
            movie.setOrginalTitle(element.getString("original_title"));

            try {
                movie.setReleaseDate(formatter.parse(element.getString("release_date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            movie.setVoteAverage(element.getDouble("vote_average"));
            movie.setOverview(element.getString("overview"));

            movieList.add(movie);
        }

        return movieList;
    }
}
