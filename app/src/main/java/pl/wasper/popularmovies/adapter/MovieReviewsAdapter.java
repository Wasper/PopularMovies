package pl.wasper.popularmovies.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.domain.MovieReview;

/**
 * Created by wasper on 02.03.17.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {
    private ArrayList<MovieReview> mMovieReviews = new ArrayList<MovieReview>();
    private IMovieReviewListItemClickListener mIMovieReviewListItemClickListener;

    public MovieReviewsAdapter(IMovieReviewListItemClickListener iMovieReviewListItemClickListener) {
        this.mIMovieReviewListItemClickListener = iMovieReviewListItemClickListener;
    }

    public void setMovieReviews(ArrayList<MovieReview> movieReviews) {
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
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.getContext(), R.color.odd_background)
            );
        }
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.review_grid_element_author) TextView reviewGridElementAuthor;
        @BindView(R.id.review_grid_element_content) TextView reviewGridElementContent;
        @BindView(R.id.review_grid_element_see_more) TextView reviewGridElementSeeMore;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            reviewGridElementSeeMore.setOnClickListener(this);
        }

        public void bind(int position) {
            reviewGridElementAuthor.setText(mMovieReviews.get(position).getAuthor());
            reviewGridElementContent.setText(mMovieReviews.get(position).getContent());
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
