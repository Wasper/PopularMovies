package pl.wasper.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wasper on 21.02.17.
 */

public class FavoritesMovieDbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "FavoritesMovie.db";

    private static final String SQL_CREATE = "CREATE TABLE " +
        FavoritesMovieContract.WaitlistMovieEntry.TABLE_NAME + " (" +
        FavoritesMovieContract.WaitlistMovieEntry._ID + " INTEGER PRIMARY KEY, " +
        FavoritesMovieContract.WaitlistMovieEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
        FavoritesMovieContract.WaitlistMovieEntry.COLUMN_NAME_MOVIE_ID + " INTEGER NOT NULL);";

    private static final String SQL_DELETE = "DROP TABLE IF EXISTS " +
        FavoritesMovieContract.WaitlistMovieEntry.TABLE_NAME;

    public FavoritesMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE);
        onCreate(db);
    }
}
