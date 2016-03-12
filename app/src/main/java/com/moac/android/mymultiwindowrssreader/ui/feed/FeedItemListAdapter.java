package com.moac.android.mymultiwindowrssreader.ui.feed;

import com.moac.android.mymultiwindowrssreader.R;
import com.moac.android.mymultiwindowrssreader.model.FeedItem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Creates View items to be displayed in a RecyclerView, populated with data.
 */
public final class FeedItemListAdapter extends RecyclerView.Adapter<FeedItemListAdapter.ViewHolder> {

    private final List<FeedItem> dataset;
    private final OnFeedItemClickListener onFeedItemClickListener;

    public FeedItemListAdapter(final List<FeedItem> dataset,
                               final OnFeedItemClickListener onFeedItemClickListener) {
        this.dataset = dataset;
        this.onFeedItemClickListener = onFeedItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent,
                                         final int viewType) {
        // Create a new View instances from the XML layout definition
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.view_feed_item, parent, false);
        return new ViewHolder(view);
    }

    // Bind a View (new or recycled) to a data item
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindItem(dataset.get(position), onFeedItemClickListener);
    }

    // Returns the size of the dataset
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    /**
     * An callback interface to notify when a feed item is clicked
     */
    public interface OnFeedItemClickListener {

        void onItemClicked(final FeedItem item);
    }

    /**
     * A recycleable representation of a View entity
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView titleTextView;
        private final TextView descriptionTextView;

        ViewHolder(final View view) {
            super(view);
            rootView = view;
            titleTextView = (TextView) view.findViewById(R.id.textView_title);
            descriptionTextView = (TextView) view.findViewById(R.id.textView_description);
        }

        /**
         * Bind a FeedItem data model to the View
         */
        void bindItem(final FeedItem feedItem, final OnFeedItemClickListener clickListener) {
            rootView.setOnClickListener(__ -> clickListener.onItemClicked(feedItem));
            titleTextView.setText(feedItem.getTitle());
            descriptionTextView.setText(feedItem.getDescription());
        }
    }
}
