package pl.wasper.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by wasper on 21.02.17.
 */

public class FavoriteMovieContentProvider extends ContentProvider {
    private FavoritesMovieDbHelper mDbHelper;

    public static final int FAVORITES = 100;
    public static final int FAVORITES_WITH_ID = 101;

    private static final UriMatcher uriMacher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(
            FavoritesMovieContract.AUTHORITY,
            FavoritesMovieContract.FAVORITES_PATH,
            FAVORITES
        );
        uriMatcher.addURI(
            FavoritesMovieContract.AUTHORITY,
            FavoritesMovieContract.FAVORITES_PATH_WITH_ID,
            FAVORITES_WITH_ID
        );

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new FavoritesMovieDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        switch (uriMacher.match(uri)) {
            case FAVORITES:
                cursor = db.query(
                    FavoritesMovieContract.FavoriteMovieEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                );
                break;
            case FAVORITES_WITH_ID:
                String movieId = uri.getPathSegments().get(1);
                String mSelection = FavoritesMovieContract.FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID +
                    "=?";
                String[] mSelectionArgs = new String[] {movieId};

                cursor = db.query(
                    FavoritesMovieContract.FavoriteMovieEntry.TABLE_NAME,
                    projection,
                    mSelection,
                    mSelectionArgs,
                    null,
                    null,
                    sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri");
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri mUri;

        switch (buildUriMatcher().match(uri)) {
            case FAVORITES:
                long id = db.insert(
                    FavoritesMovieContract.FavoriteMovieEntry.TABLE_NAME,
                    null,
                    values
                );

                if (id > 0 ) {
                    mUri = ContentUris.withAppendedId(uri, id);
                } else {
                    throw new SQLException("Failed to insert row");
                }

                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri");
        }

        getContext().getContentResolver().notifyChange(mUri, null);

        return mUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows;

        switch (uriMacher.match(uri)) {
            case FAVORITES_WITH_ID:
                String movieId = uri.getPathSegments().get(1);
                String mSelection = FavoritesMovieContract.FavoriteMovieEntry.COLUMN_NAME_MOVIE_ID +
                    "=?";
                String[] mSelectionArgs = new String[]{movieId};

                rows = db.delete(
                    FavoritesMovieContract.FavoriteMovieEntry.TABLE_NAME,
                    mSelection,
                    mSelectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri");
        }

        if (rows != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
