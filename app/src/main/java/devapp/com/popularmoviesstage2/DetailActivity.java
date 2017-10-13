package devapp.com.popularmoviesstage2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import devapp.com.popularmoviesstage2.data.NetworkUtils;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>{

    public static int POSITION_CHOSEN = 0;

    public static int LOADER_ID = 49;

    public static ArrayList<String> trailerIDs = new ArrayList<>();

    ImageView posterImageView;
    TextView titleTextView;
    TextView ratingTextView;
    TextView timeTextView;
    TextView yearTextView;
    TextView descriptionTextView;

    RecyclerView trailerRecyclerView;
    LinearLayoutManager linearLayoutManager;
    TrailerAdapter trailerAdapter;

    void initialize(){

        posterImageView = (ImageView) findViewById(R.id.detail_poster_image_view);
        titleTextView = (TextView) findViewById(R.id.detail_title_text_view);
        ratingTextView = (TextView) findViewById(R.id.detail_rating_text_view);
        timeTextView = (TextView) findViewById(R.id.detail_time_text_view);
        yearTextView = (TextView) findViewById(R.id.detail_year_text_view);
        descriptionTextView = (TextView) findViewById(R.id.detail_description_text_view);

        trailerRecyclerView = (RecyclerView) findViewById(R.id.detail_trailer_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        trailerAdapter = new TrailerAdapter(this, new TrailerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent videoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(NetworkUtils.YOUTUBE_BASE_URL + trailerIDs.get(position)));
                startActivity(videoIntent);
            }
        });

        trailerRecyclerView.setNestedScrollingEnabled(false);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);

    }

    void setDetails(){

        Picasso.with(this).load(MainActivity.moviePosterLinks.get(POSITION_CHOSEN)).into(posterImageView);
        titleTextView.setText(MainActivity.movieNames.get(POSITION_CHOSEN));
        ratingTextView.setText(MainActivity.movieRating.get(POSITION_CHOSEN));
        yearTextView.setText(MainActivity.movieReleaseDate.get(POSITION_CHOSEN));
        descriptionTextView.setText(MainActivity.movieDescription.get(POSITION_CHOSEN));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initialize();

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

                return null;
            }

            @Override
            protected void onStopLoading() {
                super.onStopLoading();
                trailerAdapter.notifyDataSetChanged();
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        trailerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
