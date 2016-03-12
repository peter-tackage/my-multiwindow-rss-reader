package com.moac.android.mymultiwindowrssreader.api;

import com.moac.android.mymultiwindowrssreader.model.RssFeedResponse;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * A Retrofit interface for making API calls.
 *
 * @see <a href="http://square.github.io/retrofit/">Retrofit</a>
 */
public interface BbcRssApi {

    @GET("/news/rss.xml")
    void getFeedItems(Callback<RssFeedResponse> feedItemsCallback);
}
