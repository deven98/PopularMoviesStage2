package devapp.com.popularmoviesstage2;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static int POSITION_CHOSEN = 0;

    ImageView posterImageView;
    TextView titleTextView;
    TextView ratingTextView;
    TextView timeTextView;
    TextView yearTextView;
    TextView descriptionTextView;

    void initialize(){

        posterImageView = (ImageView) findViewById(R.id.detail_poster_image_view);
        titleTextView = (TextView) findViewById(R.id.detail_title_text_view);
        ratingTextView = (TextView) findViewById(R.id.detail_rating_text_view);
        timeTextView = (TextView) findViewById(R.id.detail_time_text_view);
        yearTextView = (TextView) findViewById(R.id.detail_year_text_view);
        descriptionTextView = (TextView) findViewById(R.id.detail_description_text_view);

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

    }
}
