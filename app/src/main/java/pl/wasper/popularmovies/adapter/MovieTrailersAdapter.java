package pl.wasper.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.domain.MovieTrailer;

/**
 * Created by wasper on 02.03.17.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {
    private ArrayList<MovieTrailer> mMovieTrailers;
    private IMovieTrailerListItemClickListener mIMovieTrailerListItemClickListener;

    public MovieTrailersAdapter(IMovieTrailerListItemClickListener iMovieTrailerListItemClickListener) {
        this.mIMovieTrailerListItemClickListener = iMovieTrailerListItemClickListener;
    }

    public void setMovieTrailers(ArrayList<MovieTrailer> movieTrailers) {
        this.mMovieTrailers = movieTrailers;
    }

    @Override
    public MovieTrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        View view = LayoutInflater.from(context)
            .inflate(R.layout.movie_trailer_grid_element, viewGroup, false);

        return new MovieTrailersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailersAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);
        }

        public void bind(int position) {

        }

        @Override
        public void onClick(View view) {
            mIMovieTrailerListItemClickListener.onListItemClick(
                view,
                mMovieTrailers.get(getAdapterPosition())
            );
        }
    }
}
