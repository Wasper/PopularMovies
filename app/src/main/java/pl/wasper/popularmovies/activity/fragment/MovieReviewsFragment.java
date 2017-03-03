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

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.adapter.IMovieReviewListItemClickListener;
import pl.wasper.popularmovies.adapter.MovieReviewsAdapter;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.domain.MovieReview;
import pl.wasper.popularmovies.network.URLBuilder;
import pl.wasper.popularmovies.task.MovieReviewsTask;
import pl.wasper.popularmovies.task.callback.IMovieReviewsListCallback;

/**
 * Created by wasper on 01.03.17.
 */

public class MovieReviewsFragment extends Fragment
    implements IMovieReviewListItemClickListener, IMovieReviewsListCallback {

    @BindView(R.id.movie_reviews_list_recycler_view) RecyclerView mRecyclerView;
    @BindInt(R.integer.movie_reviews_list_column_count) int columnCount;

    private MovieReviewsAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(
        LayoutInflater inflater,
        @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(
            view.getContext(),
            columnCount
        );

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieReviewsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (getArguments() != null
            && getArguments().containsKey(MovieListFragment.MOVIE_EXTRA_KEY)
            && getArguments().containsKey(MovieListFragment.SORT_KEY)) {

            Movie movie = getArguments().getParcelable(MovieListFragment.MOVIE_EXTRA_KEY);

            URL url = URLBuilder.buildReviewsListUrl(movie);

            if (url == null) {
                showApiKeyError();
            } else {
                new MovieReviewsTask(this).execute(url);
            }
        }

        return view;
    }

    @Override
    public void onListItemClick(View view, MovieReview movieReview) {
        Uri uri = Uri.parse(movieReview.getUrlString());

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void adaptElements(ArrayList<MovieReview> reviews) {
        mAdapter.setMovieReviews(reviews);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showConnectError() {

    }

    @Override
    public void showParseError() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showApiKeyError() {
//        mRecyclerView.setVisibility(View.INVISIBLE);
//        mError.setText(R.string.api_key_error);
//        mError.setVisibility(View.VISIBLE);
    }
}
