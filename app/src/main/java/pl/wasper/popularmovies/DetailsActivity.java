package pl.wasper.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pl.wasper.popularmovies.domain.Movie;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView title = (TextView) findViewById(R.id.movie_title);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");

        title.setText(movie.getTitle());
    }
}
