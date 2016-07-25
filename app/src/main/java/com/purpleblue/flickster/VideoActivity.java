package com.purpleblue.flickster;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ernest on 7/22/16.
 */
public class VideoActivity extends YouTubeBaseActivity {

    String videoKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Only ever call `setContentView` once right at the top
        setContentView(R.layout.activity_video);

        // Get video by movie id
        int movieId = getIntent().getIntExtra("movieId", 0);
        loadMovieViaApi(movieId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super. onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    // ****************
    // DATA / APIs

    private void loadMovieViaApi(int movie_id) {
        String url =  String.format("https://api.themoviedb.org/3/movie/%s/trailers", movie_id);
        // https://api.themoviedb.org/3/movie/209112/trailers?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("api_key", Constants.APIKEY_THEMOVIEDB);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray trailersJsonResults = null;
                try {
                    trailersJsonResults = response.getJSONArray("youtube");
                    videoKey = trailersJsonResults.getJSONObject(0).get("source").toString();

                    YouTubePlayerView youTubePlayerView =
                            (YouTubePlayerView) findViewById(R.id.youTubePlayer);

                    youTubePlayerView.initialize(Constants.APIKEY_YOUTUBE,
                            new YouTubePlayer.OnInitializedListener() {
                                @Override
                                public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                                    YouTubePlayer youTubePlayer, boolean b) {

                                    // do any work here to cue video, play video, etc.
                                    //youTubePlayer.cueVideo(videoKey);
                                    youTubePlayer.loadVideo(videoKey);
                                }
                                @Override
                                public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                                    YouTubeInitializationResult youTubeInitializationResult) {

                                }
                            });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getApplicationContext(), "Failure - status code: " + statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
