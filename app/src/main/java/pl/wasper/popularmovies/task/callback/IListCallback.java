package pl.wasper.popularmovies.task.callback;

/**
 * Created by wasper on 02.03.17.
 */

public interface IListCallback {
    void showConnectError();
    void showParseError();
    void showProgressBar();
    void hideProgressBar();
    void showApiKeyError();
}
