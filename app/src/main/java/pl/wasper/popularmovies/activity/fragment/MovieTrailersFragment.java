package pl.wasper.popularmovies.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.adapter.IMovieTrailerListItemClickListener;
import pl.wasper.popularmovies.adapter.MovieTrailersAdapter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.domain.MovieTrailer;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.network.YoutubeURLBuilder;
import pl.wasper.popularmovies.task.MovieTrailersTask;
import pl.wasper.popularmovies.task.callback.IMovieTrailersListCallback;

/**
 * Created by wasper on 01.03.17.
 */

public class MovieTrailersFragment extends Fragment
    implements IMovieTrailerListItemClickListener, IMovieTrailersListCallback {

    @BindView(R.id.movie_trailers_list_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.error) TextView mError;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindInt(R.integer.movie_trailers_list_column_count) int columnCount;

    private MovieTrailersAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_movie_trailers, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
            view.getContext(),
            columnCount
        );

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieTrailersAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (getArguments() != null
            && getArguments().containsKey(MovieListFragment.MOVIE_EXTRA_KEY)
            && getArguments().containsKey(MovieListFragment.SORT_KEY)) {

            Movie movie = getArguments().getParcelable(MovieListFragment.MOVIE_EXTRA_KEY);

            URL url = URLBuilder.buildTrailersListUrl(movie);

            if (url == null) {
                showApiKeyError();
            } else {
                new MovieTrailersTask(this).execute(url);
            }
        }

        return view;
    }

    @Override
    public void onListItemClick(View view, MovieTrailer movieTrailer) {
        Uri uri = Uri.parse(
            YoutubeURLBuilder.buildTrailerImageUrlString(movieTrailer.getTrailerId())
        );

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void adaptElements(ArrayList<MovieTrailer> trailers) {
        mAdapter.setMovieTrailers(trailers);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showConnectError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setText(R.string.connection_error);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showParseError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setText(R.string.parsing_error);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mRecyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showApiKeyError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mError.setText(R.string.api_key_error);
        mError.setVisibility(View.VISIBLE);
    }
}
