package udacity.kishore.popularmovies.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;

import java.util.HashMap;

import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * Created by Kishore on 12/11/15.
 */
public class PopularMoviesProvider extends ContentProvider {

    private static final String TAG = "PopularMoviesProvider";
    private SQLiteDatabase db;

    private static HashMap<String, String> MOVIES_PROJECTION_MAP;

    public static final String PROVIDER_NAME = "udacity.kishore.popularmovies.provider";
    public static final Uri CONTENT_URI_MOVIES = Uri.parse("content://" + PROVIDER_NAME + "/" + PopularMoviesDatabaseHelper.Tables.MOVIES);

    static final UriMatcher sUriMatcher;

    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;

    static{
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(PROVIDER_NAME, "movies", MOVIES);
        sUriMatcher.addURI(PROVIDER_NAME, "movies/#", MOVIES_ID);
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        PopularMoviesDatabaseHelper dbHelper = new PopularMoviesDatabaseHelper(context);

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
        return super.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PopularMoviesDatabaseHelper.Tables.MOVIES);

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                qb.setProjectionMap(MOVIES_PROJECTION_MAP);
                break;

            case MOVIES_ID:
                qb.appendWhere( "_ID" + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == "") {
            sortOrder = MoviesContract.MoviesColumns.POPULARITY;
        }
        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Inserted call on ["+uri+"], with values: "+contentValues);
        long rowID = db.insert(PopularMoviesDatabaseHelper.Tables.MOVIES, "", contentValues);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI_MOVIES, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int count = 0;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                count = db.delete(PopularMoviesDatabaseHelper.Tables.MOVIES, s, strings);
                break;

            case MOVIES_ID:
                //TODO: will be implemented in phase 2
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
