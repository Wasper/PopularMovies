package pl.wasper.popularmovies.network;

import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;

import pl.wasper.popularmovies.domain.SortType;

/**
 * Created by wasper on 25.01.17.
 */

public class URLBuilder {
    private static final String API_KEY_VALUE = "f21dbce7bd7679a7e0c3054ecb21fd95";
    private static final String API_KEY_QUERY_KEY = "api_key";
    private static final String HOST = "http://api.themoviedb.org/3/movie";

    public static URL buildUrl(SortType sortType) {
        if (API_KEY_VALUE.equals("")) {
            return null;
        }

        Uri uri = Uri.parse(HOST).buildUpon()
            .appendPath(sortType.toString().toLowerCase())
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
