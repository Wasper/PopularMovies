package pl.wasper.popularmovies.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.wasper.popularmovies.domain.MovieReview;

/**
 * Created by wasper on 02.03.17.
 */

public class ReviewConverter {
    private static final String TAG_RESULTS = "results";

    private static final String TAG_MOVIE_ID = "id";
    private static final String TAG_REVIEW_AUTHOR = "author";
    private static final String TAG_REVIEW_CONTENT = "content";
    private static final String TAG_REVIEW_URL_STING = "url";

    public static ArrayList<MovieReview> listFromJson(String jsonString) {
        ArrayList<MovieReview> reviews = new ArrayList<MovieReview>();

        try {
            JSONObject object = new JSONObject(jsonString);

            int movieId = object.getInt(TAG_MOVIE_ID);

            JSONArray results = object.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < results.length(); i++) {
                JSONObject element = results.getJSONObject(i);

                MovieReview review = new MovieReview();
                review.setMovieId(movieId);
                review.setAuthor(element.getString(TAG_REVIEW_AUTHOR));
                review.setContent(element.getString(TAG_REVIEW_CONTENT));
                review.setUrlString(element.getString(TAG_REVIEW_URL_STING));

                reviews.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }
}
