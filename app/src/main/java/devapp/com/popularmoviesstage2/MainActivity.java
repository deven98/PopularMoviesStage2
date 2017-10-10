package devapp.com.popularmoviesstage2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> movieNames = new ArrayList<>();
    public static ArrayList<String> moviePosterLinks = new ArrayList<>();
    public static ArrayList<String> movieDescription = new ArrayList<>();
    public static ArrayList<String> movieRating = new ArrayList<>();
    public static ArrayList<String> movieReleaseDate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
