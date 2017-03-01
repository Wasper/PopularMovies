package pl.wasper.popularmovies.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;

import pl.wasper.popularmovies.R;

public class ListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Fragment fragment = new ListFragment();

        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.list_fragment, fragment)
            .commit();
    }

}
