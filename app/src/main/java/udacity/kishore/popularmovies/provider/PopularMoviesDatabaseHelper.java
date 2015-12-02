package udacity.kishore.popularmovies.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * Created by Kishore on 24/11/15.
 */
public class PopularMoviesDatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "PopularMoviesDatabaseHelper";
    private static final String DATABASE_NAME = "popular_movies";
    private static final int DATABASE_VERSION = 2;

    PopularMoviesDatabaseHelper (Context aContext) {
        super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public interface Tables {
         String MOVIES = "movies";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        LogUtil.log(LogUtil.LOG_TYPE_INFO, TAG, "Creating table: "+ Tables.MOVIES);

        sqLiteDatabase.execSQL("CREATE TABLE " + Tables.MOVIES + " (" +
            "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            MoviesContract.MoviesColumns.TITLE + " Text," +
            MoviesContract.MoviesColumns.MOVIE_ID + " INTEGER," +
            MoviesContract.MoviesColumns.OVERVIEW + " Text," +
            MoviesContract.MoviesColumns.POSTER_PATH + " TEXT," +
            MoviesContract.MoviesColumns.RELEASE_DATE + " TEXT," +
            MoviesContract.MoviesColumns.LANGUAGE + " TEXT," +
            MoviesContract.MoviesColumns.POPULARITY + " REAL," +
            MoviesContract.MoviesColumns.VOTE_COUNT + " INTEGER," +
            MoviesContract.MoviesColumns.VOTE_AVG + " REAL," +
            MoviesContract.MoviesColumns.IS_VIDEO + " INTEGER" +
            ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        LogUtil.log(LogUtil.LOG_TYPE_INFO, TAG, "onUpgrade() is called!, old version: "+ i + ", New version: "+i1);
    }
}
