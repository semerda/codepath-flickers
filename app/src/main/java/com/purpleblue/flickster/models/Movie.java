package com.purpleblue.flickster.models;

import android.content.Context;
import android.content.res.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ernest on 7/19/16.
 */
public class Movie {

    int movieId;
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    float voteAverage;
    float popularity;
    JSONArray genreIds;

    public String getImagePath(int orientation) {
        // Image sizes: https://www.themoviedb.org/talk/53c11d4ec3a3684cf4006400
        /*
            "backdrop_sizes": [
              "w300",
              "w780",
              "w1280",
              "original"
            ],
            "poster_sizes": [
              "w92",
              "w154",
              "w185",
              "w342",
              "w500",
              "w780",
              "original"
            ],
         */

        // Show backdrop only for popular movies
        if (isMoviePopular()) {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        }

        String urlImage = null;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            urlImage = String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            urlImage = String.format("https://image.tmdb.org/t/p/w1280/%s", backdropPath);
        }

        //Log.d("DEBUG", String.valueOf(orientation));
        return urlImage;
    }

    public int getMovieId() {
        return movieId;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getOverview() {
        return overview;
    }
    public float getVoteAverage() { return voteAverage; }
    public float getPopularity() { return popularity; }

    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    public String getGenres(Context context) throws JSONException {
        String genres = "";

        if (genreIds != null) {
            for (int i=0;i<genreIds.length();i++){
                //Log.d("DEBUG", genreIds.get(i).toString());
                genres += context.getString(getStringIdentifier(context, "genre_" + genreIds.get(i).toString())) + ", ";
                //Log.d("DEBUG", genres);
            }
        }

        // Remove last ", " from string and return the genres as readable string
        return genres.substring(0, genres.length()-2);
    }

    public Boolean isMoviePopular() {
        return (this.voteAverage > 5 ? Boolean.TRUE : Boolean.FALSE);
    }

    // Constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        this.movieId = jsonObject.getInt("id");
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.voteAverage = Float.valueOf(jsonObject.getString("vote_average"));
        this.popularity = Float.valueOf(jsonObject.getString("popularity"));
        this.genreIds = jsonObject.getJSONArray("genre_ids");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) throws JSONException {
        ArrayList<Movie> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
