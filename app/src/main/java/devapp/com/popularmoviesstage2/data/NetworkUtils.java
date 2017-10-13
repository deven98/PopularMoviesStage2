package devapp.com.popularmoviesstage2.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import devapp.com.popularmoviesstage2.DetailActivity;
import devapp.com.popularmoviesstage2.MainActivity;

public class NetworkUtils {

    private static String API_KEY = "7ed6c9e0c8221f0764db55ce52e1cfda";

    private static String POPULAR_BASE_URL = "http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY;

    private static String RATING_BASE_URL= "http://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY;

    private static String IMAGE_BASE_URL =  "http://image.tmdb.org/t/p/w185/";

    private static String ID_CHOSEN = "";

    //Add ID retrieved from API to create the complete URL;
    public static String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    public static boolean searchByPopularity = true;

    public static void clearData(){

        MainActivity.movieDescription.clear();
        MainActivity.movieNames.clear();
        MainActivity.moviePosterLinks.clear();
        MainActivity.movieRating.clear();
        MainActivity.movieReleaseDate.clear();
        MainActivity.movieId.clear();

    }

    public static void loadMovies(Context context){

        Log.d("TAG","Started loading movies");

        String URLToUse = null;

        if(searchByPopularity){URLToUse = POPULAR_BASE_URL;}
        else if(!searchByPopularity){URLToUse = RATING_BASE_URL;}

        Uri uri = Uri.parse(URLToUse).buildUpon().build();
        String s = null;
        try{
            s = getResponseFromHttpUrl(new URL(uri.toString()));}
        catch (Exception e){
            e.printStackTrace();
        }
        JSONSeperator(context,s);

    }

    public static ArrayList<String> JSONSeperator(Context c, String s){

        try {

            JSONObject j = new JSONObject(s);

            JSONArray movies = j.getJSONArray("results");

            for(int i = 0; i<movies.length()-1 ; i++) {

                JSONObject movie = movies.getJSONObject(i);

                String title = movie.getString("title");
                MainActivity.movieNames.add(title);

                String posterLink = movie.getString("poster_path");
                MainActivity.moviePosterLinks.add(IMAGE_BASE_URL+posterLink);

                String movieOverview = movie.getString("overview");
                MainActivity.movieDescription.add(movieOverview);

                String release = movie.getString("release_date");
                MainActivity.movieReleaseDate.add(release);

                String rating = movie.getString("vote_average");
                MainActivity.movieRating.add(rating);

                String id = movie.getString("id");
                MainActivity.movieId.add(id);

                Log.d("pos",posterLink);

                Log.d("res", title);
            }

        }catch (Exception e){

            e.printStackTrace();

        }
        return null;

    }

    public static void getTrailers(){

        DetailActivity.trailerIDs.clear();

        ID_CHOSEN = MainActivity.movieId.get(DetailActivity.POSITION_CHOSEN);

        String TRAILER_BASE_URL = "http://api.themoviedb.org/3/movie/" + ID_CHOSEN + "/videos?api_key=" + API_KEY;

        Uri uri = Uri.parse(TRAILER_BASE_URL).buildUpon().build();

        String result = null;

        try{
            result = getResponseFromHttpUrl(new URL(uri.toString()));}
        catch (Exception e){
            e.printStackTrace();
        }

        parseTrailers(result);

    }

    private static void parseTrailers(String result){

        try {
            JSONObject j = new JSONObject(result);

            JSONArray trailers = j.getJSONArray("results");

            for(int i = 0; i<trailers.length()-1; i++){

                DetailActivity.trailerIDs.add(trailers.getJSONObject(i).getString("key"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
