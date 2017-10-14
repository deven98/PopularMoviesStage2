package devapp.com.popularmoviesstage2.data;

import android.net.Uri;
import android.provider.BaseColumns;


public class FavouritesContract {

    public static final String AUTHORITY = "devapp.com.popularmoviesstage2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_FAVOURITES = "favourites";

    public static final class FavouritesEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        public static final String TABLE_NAME = "favourites";

        public static final String MOVIE_NAME = "name";

        public static final String MOVIE_POSTER_LINK = "link";

        public static final String MOVIE_RELEASE_DATE = "date";

        public static final String MOVIE_CONTENT_DESCRIPTION = "description";

        public static final String MOVIE_RATING = "rating";

        public static final String MOVIE_ID = "id";

    }

}
