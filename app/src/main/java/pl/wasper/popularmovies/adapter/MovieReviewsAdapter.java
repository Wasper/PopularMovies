package pl.wasper.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.domain.MovieReview;

/**
 * Created by wasper on 02.03.17.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {
    private ArrayList<MovieReview> mMovieReviews;
    private IMovieReviewListItemClickListener mIMovieReviewListItemClickListener;

    public MovieReviewsAdapter(IMovieReviewListItemClickListener iMovieReviewListItemClickListener) {
        this.mIMovieReviewListItemClickListener = iMovieReviewListItemClickListener;
    }

    public void setMovieTrailers(ArrayList<MovieReview> movieReviews) {
        this.mMovieReviews = movieReviews;
    }
    @Override
    public MovieReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        View view = LayoutInflater.from(context)
            .inflate(R.layout.movie_review_grid_element, viewGroup, false);
        
        return new MovieReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewsAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
        }

        public void bind(int position) {

        }

        @Override
        public void onClick(View view) {
            mIMovieReviewListItemClickListener.onListItemClick(
                view,
                mMovieReviews.get(getAdapterPosition())
            );
        }
    }
}
