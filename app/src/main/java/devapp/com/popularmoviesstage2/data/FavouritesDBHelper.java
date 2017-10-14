package devapp.com.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouritesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favouritesDb.db";

    public static final int DB_VERSION = 3;

    public FavouritesDBHelper(Context context) {
        super(context, DATABASE_NAME , null , DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE "  + FavouritesContract.FavouritesEntry.TABLE_NAME + " (" +
                FavouritesContract.FavouritesEntry._ID       + " INTEGER PRIMARY KEY, " +
                FavouritesContract.FavouritesEntry.MOVIE_NAME + " TEXT NOT NULL," +
                FavouritesContract.FavouritesEntry.MOVIE_POSTER_LINK + " TEXT NOT NULL," +
                FavouritesContract.FavouritesEntry.MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                FavouritesContract.FavouritesEntry.MOVIE_RATING + " TEXT NOT NULL," +
                FavouritesContract.FavouritesEntry.MOVIE_ID + " TEXT NOT NULL, " +
                FavouritesContract.FavouritesEntry.MOVIE_CONTENT_DESCRIPTION + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FavouritesContract.FavouritesEntry.TABLE_NAME);
        onCreate(db);

    }

}
