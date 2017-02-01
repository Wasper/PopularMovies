package pl.wasper.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.wasper.popularmovies.R;
import pl.wasper.popularmovies.domain.Movie;
import pl.wasper.popularmovies.network.PosterURLBuilder;

/**
 * Created by wasper on 26.01.17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<Movie> movies = new ArrayList<Movie>();
    private ListAdapter.ListItemClickListener mListItemClickListener;

    public ListAdapter(ListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    public void setMoviesList(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.grid_element, viewGroup, false);

        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(Movie movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            ImageView moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);

            Picasso.with(itemView.getContext())
                .load(PosterURLBuilder.build(movies.get(position).getPosterPath()))
                .into(moviePoster);

            moviePoster.setContentDescription(movies.get(position).getTitle());
        }

        @Override
        public void onClick(View v) {
            mListItemClickListener.onListItemClick(movies.get(getAdapterPosition()));
        }
    }
}
