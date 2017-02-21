package pl.wasper.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by wasper on 21.02.17.
 */

public final class FavoritesMovieContract {

    private FavoritesMovieContract() {

    }

    public final class WaitlistMovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoriteMovie";
        public static final String COLUMN_NAME_TITLE = "title";
    }
}
