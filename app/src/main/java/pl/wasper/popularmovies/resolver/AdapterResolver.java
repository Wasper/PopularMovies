package pl.wasper.popularmovies.resolver;

import android.support.v7.widget.RecyclerView;

import pl.wasper.popularmovies.adapter.FavoritesListAdapter;
import pl.wasper.popularmovies.adapter.IListItemClickListener;
import pl.wasper.popularmovies.adapter.ListAdapter;

/**
 * Created by wasper on 23.02.17.
 */

public class AdapterResolver {
    public static RecyclerView.Adapter resolve(
        SortType type,
        IListItemClickListener listItemClickListener
    ) {
        switch (type) {
            case POPULAR:
            case TOP_RATED:
                return new ListAdapter(listItemClickListener);
            case FAVORITES:
                return new FavoritesListAdapter(listItemClickListener);
            default:
                throw new UnsupportedOperationException("Unexpected sort type");
        }
    }
}
