package pl.wasper.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by wasper on 21.02.17.
 */

public final class FavoritesMovieContract {
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "pl.wasper.popularmovies";
    public static final String FAVORITES_PATH = "favorites";
    public static final String FAVORITES_PATH_WITH_ID = FAVORITES_PATH + "/#";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    private FavoritesMovieContract() {

    }

    public static final class FavoriteMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(FAVORITES_PATH)
            .build();

        public static final String TABLE_NAME = "favoriteMovie";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
    }
}
