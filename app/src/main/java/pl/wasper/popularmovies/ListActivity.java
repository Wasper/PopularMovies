package pl.wasper.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ListActivity extends AppCompatActivity {

    private static final String API_KEY_VALUE = "f21dbce7bd7679a7e0c3054ecb21fd95";
    private static final String API_KEY_QUERY_KEY = "api_key";
    private static final String HOST = "http://api.themoviedb.org/3/movie";
    private static final String TOP_RATED_PATH = "top_rated";
    private static final String POPULAR_PATH = "popular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_rated_item:
                break;
            case R.id.most_popular_item:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getUrlResponse(URL url) throws IOException {
        HttpURLConnection connection = null;
        String result = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();

            result = convertStreamToString(stream);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    public String convertStreamToString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);

        scanner.useDelimiter("\\A");

        boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return null;
        }
    }

    public URL buildUrl(String sortType) {
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

    class MoviesListTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL url = params[0];
            String response = null;

            try {
                response = getUrlResponse(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("TEST", s);
            super.onPostExecute(s);
        }
    }
}
