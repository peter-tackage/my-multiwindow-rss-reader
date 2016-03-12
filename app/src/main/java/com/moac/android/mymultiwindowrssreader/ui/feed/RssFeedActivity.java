package com.moac.android.mymultiwindowrssreader.ui.feed;

import com.moac.android.mymultiwindowrssreader.MyRssFeedApplication;
import com.moac.android.mymultiwindowrssreader.R;
import com.moac.android.mymultiwindowrssreader.api.BbcRssApi;
import com.moac.android.mymultiwindowrssreader.model.RssFeedResponse;
import com.moac.android.mymultiwindowrssreader.ui.article.ArticleActivity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This is our main Activity, it will show a list item RSS feed items retrieved from the internet.
 */
public final class RssFeedActivity extends AppCompatActivity {

    private static final String TAG = RssFeedActivity.class.getSimpleName();

    private RecyclerView feedRecyclerView;

    private final FeedItemListAdapter.OnFeedItemClickListener onFeedItemClickListener
            = item -> ArticleActivity.launch(RssFeedActivity.this, item.getLink());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the Activity UI.
        setContentView(R.layout.activity_rss_feed);
        // Initialize the Feed UI.
        initFeedView();
        // Trigger the loading of the feed.
        loadFeed();
    }

    private void initFeedView() {
        // Get a reference to the RecyclerView widget.
        feedRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_rssFeed);
        feedRecyclerView.setHasFixedSize(true);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Loads an RSS Feed into the UI.
     */
    private void loadFeed() {
        // Use Retrofit to fetch the feed items - this is asynchronous, so supply a set of callbacks,
        // one for success and one for error.
        //
        // Responses are delivered on the Android main thread - this is required to modify the
        // view layout.
        getBccRssApi().getFeedItems(new Callback<RssFeedResponse>() {
            @Override
            public void success(final RssFeedResponse rssFeedResponse, final Response response) {
                // Report success to the UI using a "Snackbar".
                Snackbar.make(feedRecyclerView,
                              String.format(Locale.getDefault(),
                                            "We received %d items!", rssFeedResponse.getChannel()
                                                                                    .getFeedItems()
                                                                                    .size()),
                              Snackbar.LENGTH_SHORT)
                        .show();

                feedRecyclerView.setAdapter(
                        new FeedItemListAdapter(rssFeedResponse.getChannel().getFeedItems(),
                                                onFeedItemClickListener));
            }

            @Override
            public void failure(final RetrofitError error) {
                // Report failure to the UI using a "Snackbar" and log it.
                final Snackbar snackbar = Snackbar
                        .make(feedRecyclerView, "Error! " + error, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(getString(R.string.msg_retry),
                                   __ -> loadFeedAndDismissSnackbar(snackbar));
                snackbar.show();

                // Report the failure to application logs
                Log.e(TAG, "Error when retrieving feed", error);
            }

            private void loadFeedAndDismissSnackbar(final Snackbar snackbar) {
                loadFeed();
                snackbar.dismiss();
            }
        });

    }

    /**
     * A little helper method, every Activity class has access to the Application instance, use
     * that to get a reference to the API interface.
     *
     * @return the singleton instance of the BBC API service.
     */
    private BbcRssApi getBccRssApi() {
        return ((MyRssFeedApplication) getApplication()).getBccRssApi();
    }

}
