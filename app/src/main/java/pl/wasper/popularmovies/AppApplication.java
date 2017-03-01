package pl.wasper.popularmovies;

/**
 * Created by wasper on 24.02.17.
 */

import android.app.Application;

public class AppApplication extends Application {
    public static AppApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
