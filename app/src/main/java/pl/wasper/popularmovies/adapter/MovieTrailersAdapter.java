package pl.wasper.popularmovies.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.domain.MovieTrailer;
import pl.wasper.popularmovies.network.YoutubeURLBuilder;

/**
 * Created by wasper on 02.03.17.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {
    private ArrayList<MovieTrailer> mMovieTrailers = new ArrayList<MovieTrailer>();
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
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.getContext(), R.color.odd_background)
            );
        }

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trailer_grid_element_image) ImageView trailerGridElementImage;
        @BindView(R.id.trailer_grid_element_title) TextView trailerGridElementTitle;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            trailerGridElementImage.setOnClickListener(this);
        }

        public void bind(int position) {

            Picasso.with(itemView.getContext())
                .load(YoutubeURLBuilder
                    .buildTrailerImageUrlString(mMovieTrailers.get(position).getTrailerId())
                )
                .into(trailerGridElementImage);

            trailerGridElementImage.setContentDescription(mMovieTrailers
                .get(position)
                .getTrailerTitle()
            );

            trailerGridElementTitle.setText(mMovieTrailers.get(position).getTrailerTitle());
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
