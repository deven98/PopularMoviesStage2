package devapp.com.popularmoviesstage2;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import devapp.com.popularmoviesstage2.data.FavouritesContract;
import devapp.com.popularmoviesstage2.data.NetworkUtils;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>{

    public static int POSITION_CHOSEN = 0;

    public static int LOADER_ID = 49;

    public static ArrayList<String> trailerIDs = new ArrayList<>();
    public static ArrayList<String> reviews = new ArrayList<>();

    ImageView posterImageView;
    TextView titleTextView;
    TextView ratingTextView;
    TextView yearTextView;
    TextView descriptionTextView;

    RecyclerView trailerRecyclerView;
    LinearLayoutManager trailerLinearLayoutManager;
    TrailerAdapter trailerAdapter;

    RecyclerView reviewRecyclerView;
    ReviewAdapter reviewAdapter;
    LinearLayoutManager reviewLinearLayoutManager;

    Button favouriteButton;

    void initialize(){

        posterImageView = (ImageView) findViewById(R.id.detail_poster_image_view);
        titleTextView = (TextView) findViewById(R.id.detail_title_text_view);
        ratingTextView = (TextView) findViewById(R.id.detail_rating_text_view);
        yearTextView = (TextView) findViewById(R.id.detail_year_text_view);
        descriptionTextView = (TextView) findViewById(R.id.detail_description_text_view);

        favouriteButton = (Button) findViewById(R.id.detail_favourite_button);

        if(NetworkUtils.displayFavourites){
            favouriteButton.setText(getString(R.string.display_remove_favourites));
        }

        trailerRecyclerView = (RecyclerView) findViewById(R.id.detail_trailer_recycler_view);
        trailerLinearLayoutManager = new LinearLayoutManager(this);
        trailerAdapter = new TrailerAdapter(this, new TrailerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.YOUTUBE_BASE_URL + trailerIDs.get(position)));
                startActivity(videoIntent);
            }
        });

        trailerRecyclerView.setNestedScrollingEnabled(false);
        trailerRecyclerView.setLayoutManager(trailerLinearLayoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);

        reviewRecyclerView = (RecyclerView) findViewById(R.id.detail_review_recycler_view);
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewAdapter = new ReviewAdapter(this);
        reviewRecyclerView.setAdapter(reviewAdapter);
        reviewLinearLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(reviewLinearLayoutManager);

    }

    void setDetails(){

        Picasso.with(this).load(MainActivity.moviePosterLinks.get(POSITION_CHOSEN)).into(posterImageView);
        titleTextView.setText(MainActivity.movieNames.get(POSITION_CHOSEN));
        ratingTextView.setText(MainActivity.movieRating.get(POSITION_CHOSEN) + getString(R.string.total_rating) + "");
        yearTextView.setText(MainActivity.movieReleaseDate.get(POSITION_CHOSEN));
        descriptionTextView.setText(MainActivity.movieDescription.get(POSITION_CHOSEN));

    }

    void setOnClickListeners(){

        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!NetworkUtils.displayFavourites) {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(FavouritesContract.FavouritesEntry.MOVIE_NAME, MainActivity.movieNames.get(POSITION_CHOSEN));
                    contentValues.put(FavouritesContract.FavouritesEntry.MOVIE_POSTER_LINK, MainActivity.moviePosterLinks.get(POSITION_CHOSEN));
                    contentValues.put(FavouritesContract.FavouritesEntry.MOVIE_RELEASE_DATE, MainActivity.movieReleaseDate.get(POSITION_CHOSEN));
                    contentValues.put(FavouritesContract.FavouritesEntry.MOVIE_CONTENT_DESCRIPTION, MainActivity.movieDescription.get(POSITION_CHOSEN));
                    contentValues.put(FavouritesContract.FavouritesEntry.MOVIE_RATING, MainActivity.movieRating.get(POSITION_CHOSEN));
                    contentValues.put(FavouritesContract.FavouritesEntry.MOVIE_ID, MainActivity.movieId.get(POSITION_CHOSEN));

                    getContentResolver().insert(FavouritesContract.FavouritesEntry.CONTENT_URI, contentValues);

                    finish();
                }else{

                    Uri uri = FavouritesContract.FavouritesEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(MainActivity.DBIDs.get(POSITION_CHOSEN)).build();

                    getContentResolver().delete(uri,null,null);

                    MainActivity.displayMoviesAdapter.notifyDataSetChanged();

                    finish();

                }

            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();
        setOnClickListeners();

        Intent in = getIntent();

        if(in.hasExtra("position")){

            POSITION_CHOSEN = in.getIntExtra("position",0);

        }

        setDetails();

        getSupportLoaderManager().initLoader(LOADER_ID,null,this);

    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }

            @Override
            public Void loadInBackground() {

                NetworkUtils.getTrailers();
                NetworkUtils.getReviews();

                return null;
            }

            @Override
            protected void onStopLoading() {
                super.onStopLoading();
                trailerAdapter.notifyDataSetChanged();
                reviewAdapter.notifyDataSetChanged();
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        trailerAdapter.notifyDataSetChanged();
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
