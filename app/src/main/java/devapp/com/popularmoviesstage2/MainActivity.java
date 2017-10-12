package devapp.com.popularmoviesstage2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import devapp.com.popularmoviesstage2.data.NetworkUtils;

public class MainActivity extends AppCompatActivity implements DisplayMoviesAdapter.ItemClickListener, LoaderManager.LoaderCallbacks<Void> {

    public static ArrayList<String> movieNames = new ArrayList<>();
    public static ArrayList<String> moviePosterLinks = new ArrayList<>();
    public static ArrayList<String> movieDescription = new ArrayList<>();
    public static ArrayList<String> movieRating = new ArrayList<>();
    public static ArrayList<String> movieReleaseDate = new ArrayList<>();

    RecyclerView displayMoviesRecyclerView;
    GridLayoutManager gridLayoutManager;
    DisplayMoviesAdapter displayMoviesAdapter;
    ProgressBar progressBar;

    int ROWS_TO_DISPLAY = 2;

    public static final int MOVIE_LOADER_ID = 99;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);

        menuInflater.inflate(R.menu.display_movies_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Boolean originalSetting = NetworkUtils.searchByPopularity;

        if(item.getItemId() == R.id.menu_search_by_popularity){
            NetworkUtils.searchByPopularity = true;
        }else{
            NetworkUtils.searchByPopularity = false;
        }

        if(originalSetting != NetworkUtils.searchByPopularity){
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID,null,this);
        }

        return super.onOptionsItemSelected(item);
    }

    void initialize(){

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ROWS_TO_DISPLAY = 2;
        }
        else{ROWS_TO_DISPLAY = 4;}

        progressBar = (ProgressBar) findViewById(R.id.display_movies_progress_bar);

        displayMoviesRecyclerView = (RecyclerView) findViewById(R.id.display_movies_recycler_view);
        gridLayoutManager = new GridLayoutManager(this,ROWS_TO_DISPLAY);
        displayMoviesAdapter  = new DisplayMoviesAdapter(this,this);

        displayMoviesRecyclerView.setLayoutManager(gridLayoutManager);
        displayMoviesRecyclerView.setAdapter(displayMoviesAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        getSupportLoaderManager().initLoader(MOVIE_LOADER_ID,null,this);

    }

    @Override
    public void onItemClick(int position) {

        Intent in = new Intent(this,DetailActivity.class);
        in.putExtra("Position",position);
        startActivity(in);

    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this, "Started Loading", Toast.LENGTH_SHORT).show();

                forceLoad();
            }

            @Override
            public Void loadInBackground() {

                NetworkUtils.clearData();

                NetworkUtils.loadMovies(MainActivity.this);

                return null;

            }

            @Override
            protected void onStopLoading() {
                super.onStopLoading();

                progressBar.setVisibility(View.INVISIBLE);

            }
        };

    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {

        displayMoviesAdapter.notifyDataSetChanged();

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
