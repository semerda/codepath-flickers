package com.purpleblue.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by ernest on 7/22/16.
 */
public class DetailActivity extends AppCompatActivity {

    // Strings binding
    @BindString(R.string.blockbuster) String sr_blockbuster;
    @BindString(R.string.popularity_index) String sr_popularity_index;

    // Activity View Lookup binding
    @BindView(R.id.tvGenres) TextView tvGenres;
    @BindView(R.id.rbVoteAverage) RatingBar rbVoteAverage;
    @BindView(R.id.tvVoteAverage) TextView tvVoteAverage;
    @BindView(R.id.pbPopularity) ProgressBar pbPopularity;
    @BindView(R.id.tvPopularity) TextView tvPopularity;
    @BindView(R.id.ivMovieImage) ImageView ivMovieImage;
    @BindView(R.id.tvOverview) TextView tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Only ever call `setContentView` once right at the top
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getIntent().getStringExtra("title"));

        setupView();
    }

    public void setupView() {
        // Genres
        tvGenres.setText(getIntent().getStringExtra("genres"));

        // Rating bar
        float voteAverage = getIntent().getFloatExtra("voteAverage", 0);
        rbVoteAverage.setRating(voteAverage);
        tvVoteAverage.setText(String.format(" %s/10", voteAverage));

        // Popularity
        float popularity = getIntent().getFloatExtra("popularity", 0);
        float popularityPerc = ((popularity * 100)/40); // Anything over 40 is a blockbuster
        pbPopularity.setProgress(Math.round(popularityPerc > 100 ? 100 : popularityPerc));
        tvPopularity.setText((popularityPerc >= 100) ?
                sr_blockbuster :
                String.format(" %s: %s", sr_popularity_index, Math.round(popularity)));

        // Load the appropriate image
        Picasso.with(DetailActivity.this).load(getIntent().getStringExtra("movieImage"))
                .placeholder(R.drawable.ic_loader)
                .error(R.drawable.ic_imgerror_futurama)
                .transform(new RoundedCornersTransformation(8, 8))
                .into(ivMovieImage);

        // Set overview
        tvOverview.setText(getIntent().getStringExtra("overview"));
    }

    // Make play image clickable to open a new activity and start video
    @OnClick(R.id.ivPlayMovie)
    public void onClickPlayMovie(View view) {
        Intent i = new Intent(DetailActivity.this, VideoActivity.class);
        i.putExtra("movieId", getIntent().getIntExtra("movieId", 0));
        startActivity(i);
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
}
