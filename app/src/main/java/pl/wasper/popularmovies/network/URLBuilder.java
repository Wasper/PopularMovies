package pl.wasper.popularmovies.network;

import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wasper on 25.01.17.
 */

public class URLBuilder {
    public static final String TOP_RATED_PATH = "top_rated";
    public static final String POPULAR_PATH = "popular";

    private static final String API_KEY_VALUE = "";
    private static final String API_KEY_QUERY_KEY = "api_key";
    private static final String HOST = "http://api.themoviedb.org/3/movie";

    public static URL buildUrl(String sortType) {
        if (API_KEY_VALUE.equals("")) {
            return null;
        }

        Uri uri = Uri.parse(HOST).buildUpon()
            .appendPath(sortType)
            .appendQueryParameter(API_KEY_QUERY_KEY, API_KEY_VALUE)
            .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}
