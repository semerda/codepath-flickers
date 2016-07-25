package com.purpleblue.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.purpleblue.flickster.DetailActivity;
import com.purpleblue.flickster.R;
import com.purpleblue.flickster.VideoActivity;
import com.purpleblue.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by ernest on 7/19/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    // Using Heterogenous ListView
    // Ref: http://guides.codepath.com/android/Implementing-a-Heterogenous-ListView

    public enum ViewTypes {
        DEFAULT, FULL_BACKDROP
    }

    // View lookup cache
    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
        //RatingBar rbVoteAverage;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_expandable_list_item_1, movies);
    }

    // Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
    @Override
    public int getViewTypeCount() {
        // Returns the number of types of Views that will be created by this adapter
        // Each type represents a set of views that can be converted

        return ViewTypes.values().length; // Count of different layouts
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        // Return an integer here representing the type of View.
        // Note: Integers must be in the range 0 to getViewTypeCount() - 1
        Integer viewId = ViewTypes.DEFAULT.ordinal();

        // Get the data item for position
        Movie movie = getItem(position);
        if (getItem(position).isMoviePopular()) {
            viewId = ViewTypes.FULL_BACKDROP.ordinal();;
        }

        return viewId;
    }

    // Get a View that displays the data at the specified position in the data set.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // View should be created based on the type returned from `getItemViewType(int position)`
        // convertView is guaranteed to be the "correct" recycled type

        // Get the data item for position
        Movie movie = getItem(position);

        // Get the data item type for this position
        int type = getItemViewType(position);

        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        // Check the existing view being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            //viewHolder.rbVoteAverage = (RatingBar) convertView.findViewById(R.id.rbVoteAverage);

            if (type == ViewTypes.DEFAULT.ordinal()) {
                viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
                viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            }

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate data
        //viewHolder.rbVoteAverage.setRating(movie.getVoteAverage());
        if (type == ViewTypes.DEFAULT.ordinal()) {
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOverview.setText(movie.getOverview());
        }

        // Get device orientation so we can display the correct image
        int orientation = getContext().getResources().getConfiguration().orientation;

        // Android doesn't have a way to load images so we use Picasso
        Picasso.with(getContext()).load(movie.getImagePath(orientation)) //.fit().centerCrop()
                .placeholder(R.drawable.ic_loader)
                .error(R.drawable.ic_imgerror_futurama)
                .transform(new RoundedCornersTransformation(8, 8))
                .into(viewHolder.ivImage);

        // Make more info button clickable to open a new activity
        Button moreInfoButton = (Button) convertView.findViewById(R.id.btnMoreInfo);
        moreInfoButton.setTag(movie); // store position in the button
        moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie thisMovie = (Movie) view.getTag();

                Intent i = new Intent(getContext(), DetailActivity.class);
                i.putExtra("movieId", thisMovie.getMovieId());
                i.putExtra("title", thisMovie.getOriginalTitle());
                i.putExtra("overview", thisMovie.getOverview());
                i.putExtra("voteAverage", thisMovie.getVoteAverage());
                i.putExtra("popularity", thisMovie.getPopularity());
                try {
                    i.putExtra("genres", thisMovie.getGenres(getContext()));
                }
                catch(JSONException je) { je.printStackTrace(); }

                int orientation = getContext().getResources().getConfiguration().orientation;
                i.putExtra("movieImage", thisMovie.getImagePath(orientation));

                getContext().startActivity(i);
            }
        });

        // We only give ability to play movies that are popular, otherwise from detail view
        if (movie.isMoviePopular()) {
            // Make play image clickable to open a new activity and start video
            ImageView playMovieImageView = (ImageView) convertView.findViewById(R.id.ivPlayMovie);
            playMovieImageView.setTag(movie); // store position in the button
            playMovieImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Movie thisMovie = (Movie) view.getTag();

                    Intent i = new Intent(getContext(), VideoActivity.class);
                    i.putExtra("movieId", thisMovie.getMovieId());
                    getContext().startActivity(i);
                }
            });
        } else {
            ImageView playMovieImageView = (ImageView) convertView.findViewById(R.id.ivPlayMovie);
            playMovieImageView.setVisibility(4); // Invisible Play icon
        }

        // Return the view
        return convertView;
    }
}
