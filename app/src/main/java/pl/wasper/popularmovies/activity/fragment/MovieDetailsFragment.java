package pl.wasper.popularmovies.activity.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.converter.MoviesConverter;
import pl.wasper.popularmovies.data.FavoritesMovieContract;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.domain.types.SortType;
import pl.wasper.popularmovies.network.PosterURLBuilder;

/**
 * Created by wasper on 28.02.17.
 */

public class MovieDetailsFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.movie_title) TextView title;
    @BindView(R.id.movie_original_title) TextView originalTitle;
    @BindView(R.id.movie_release_date) TextView releaseDate;
    @BindView(R.id.movie_vote_average) TextView voteAverage;
    @BindView(R.id.movie_overview) TextView overview;
    @BindView(R.id.details_movie_poster) ImageView moviePoster;
    @BindView(R.id.favorites_button) Button favorites;
    @BindBool(R.bool.use_tablet_view) boolean useTabletView;

    private Movie movie;
    private SortType mSortType;
    private boolean hasRecord;

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, view);

        favorites.setOnClickListener(this);

        if (getArguments() != null
            && getArguments().containsKey(MovieListFragment.MOVIE_EXTRA_KEY)
            && getArguments().containsKey(MovieListFragment.SORT_KEY)) {
            movie = getArguments().getParcelable(MovieListFragment.MOVIE_EXTRA_KEY);
            mSortType = SortType.valueOf(getArguments().getString(MovieListFragment.SORT_KEY));
            prepareView(view, movie);
        }

        return view;
    }

    private void prepareView(View view, Movie movie) {
        title.setText(movie.getTitle());
        originalTitle.setText(movie.getOrginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        voteAverage.setText(buildVoteAverage(movie));
        overview.setText(movie.getOverview());

        Picasso.with(view.getContext())
            .load(PosterURLBuilder.build(movie.getPosterPath()))
            .into(moviePoster);

        hasRecord = hasRecord(view, movie);

        if (hasRecord) {
            favorites.setText(getText(R.string.remove_from_favorites));
        }
    }

    private String buildVoteAverage(Movie movie) {
        return String.format("%.1f / 10", movie.getVoteAverage());
    }

    public void addOrRemoveRecord(View view) {
        if (!hasRecord) {
            addRecord(view, movie);
            prepareActiveButton(view);
            hasRecord = true;
        } else {
            removeRecord(view, movie);
            preprareInactiveButton(view);
            hasRecord = false;
        }

        if (useTabletView && mSortType == SortType.FAVORITES) {
            refreshList();
        }
    }

    private void refreshList() {
        MovieListFragment listFragment = new MovieListFragment();

        getFragmentManager()
            .beginTransaction()
            .replace(R.id.list_fragment, listFragment)
            .commit();
    }

    private void preprareInactiveButton(View view) {
        Toast.makeText(view.getContext(), getText(R.string.removed_toast), Toast.LENGTH_LONG).show();
        favorites.setText(getText(R.string.add_to_favorites));
    }

    private void prepareActiveButton(View view) {
        Toast.makeText(view.getContext(), getText(R.string.added_toast), Toast.LENGTH_LONG).show();
        favorites.setText(getText(R.string.remove_from_favorites));
    }

    private Cursor getRecord(View view, Movie movie) {

        try {
            return view.getContext().getContentResolver().query(
                buildContentUriWithId(movie.getId()),
                null,
                null,
                null,
                null
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean hasRecord(View view, Movie movie) {
        return getRecord(view, movie).getCount() > 0;
    }

    private Uri addRecord(View view, Movie movie) {
        ContentValues values = MoviesConverter.singleToContentValues(movie);

        return view.getContext().getContentResolver().insert(
            FavoritesMovieContract.FavoriteMovieEntry.CONTENT_URI,
            values
        );
    }

    private int removeRecord(View view, Movie movie) {
        return view
            .getContext()
            .getContentResolver()
            .delete(buildContentUriWithId(movie.getId()), null, null);
    }

    private Uri buildContentUriWithId(int movieId) {
        return FavoritesMovieContract.FavoriteMovieEntry.CONTENT_URI
            .buildUpon()
            .appendPath(String.valueOf(movieId))
            .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favorites_button:
                addOrRemoveRecord(v);
                break;
        }
    }
}
