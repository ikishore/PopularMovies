package udacity.kishore.popularmovies.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import udacity.kishore.popularmovies.Constants;
import udacity.kishore.popularmovies.provider.MoviesContract;

/**
 * Created by Kishore on 24/11/15.
 */
public class CommonUtils {
    private static final String TAG = "CommonUtils";


    public static String getUri (final int aReqType) {
        String lReturnValue = null;
        Uri.Builder builder = new Uri.Builder();

        switch (aReqType) {
            case Constants.REQ_TYPE_GET_POP_MOVIES :
                // http://api.themoviedb.org/3/discover/movie?api_key=xxxx&sort_by=popularity.desc
                builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("api_key", Constants.MDB_API_KEY)
                        .appendQueryParameter("sort_by", "popularity.desc");
                lReturnValue = builder.build().toString();
                break;

            case Constants.REQ_TYPE_GET_MOVIES_GENRES :
                // http://api.themoviedb.org/3/genre/movie/list?api_key=xxxx
                builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("genre")
                        .appendPath("movie")
                        .appendPath("list")
                        .appendQueryParameter("api_key", Constants.MDB_API_KEY);
                lReturnValue = builder.build().toString();
                break;

            case Constants.REQ_TYPE_DEFAULT :
            default:
                break;
        }

        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "getUri() for request: "+aReqType+" is: "+lReturnValue);

        return lReturnValue;
    }

    public static String getImageUri(String aImageName) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "getImageUri(), Image name: "+aImageName);
        return Constants.IMAGE_BASE_URI + Constants.IMAGE_SIZE + aImageName;
    }

    public static Bundle prepareBundleWithMovieDetails(Cursor aCusrsor) {
        Bundle lBundle =  new Bundle();
        lBundle.putString(MoviesContract.MoviesColumns.TITLE, aCusrsor.getString(MoviesContract.INDEX_TITLE));
        lBundle.putString(MoviesContract.MoviesColumns.OVERVIEW, aCusrsor.getString(MoviesContract.INDEX_OVERVIEW));
        lBundle.putString(MoviesContract.MoviesColumns.RELEASE_DATE, aCusrsor.getString(MoviesContract.INDEX_RELEASE_DATE));
        lBundle.putFloat(MoviesContract.MoviesColumns.VOTE_AVG, aCusrsor.getFloat(MoviesContract.INDEX_VOTE_AVG));
        lBundle.putString(MoviesContract.MoviesColumns.POSTER_PATH, aCusrsor.getString(MoviesContract.INDEX_POSTER_PATH));
        lBundle.putInt(MoviesContract.MoviesColumns.VOTE_COUNT, aCusrsor.getInt(MoviesContract.INDEX_VOTE_COUNT));
        return lBundle;
    }


    public static String getMovieTitle(Bundle aMovieDetailBundle) {
        return aMovieDetailBundle.getString(MoviesContract.MoviesColumns.TITLE);
    }

    public static String getMovieOverview(Bundle aMovieDetailBundle) {
        return aMovieDetailBundle.getString(MoviesContract.MoviesColumns.OVERVIEW);
    }

    public static String getMovieReleaseDate(Bundle aMovieDetailBundle) {
        return aMovieDetailBundle.getString(MoviesContract.MoviesColumns.RELEASE_DATE);
    }

    public static String getMoviePosterPath(Bundle aMovieDetailBundle) {
        return aMovieDetailBundle.getString(MoviesContract.MoviesColumns.POSTER_PATH);
    }

    public static Float getVoteAverage(Bundle aMovieDetailBundle) {
        return aMovieDetailBundle.getFloat(MoviesContract.MoviesColumns.VOTE_AVG);
    }

    public static Integer getVoteCount(Bundle aMovieDetailBundle) {
        return aMovieDetailBundle.getInt(MoviesContract.MoviesColumns.VOTE_COUNT);
    }

    public static Bundle getMovieDetailsBundleFromSavedState(Bundle savedInstanceState) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "getMovieDetailsBundleFromSavedState() is called!");
        Bundle lBundle =  new Bundle();
        lBundle.putString(MoviesContract.MoviesColumns.TITLE, getMovieTitle(savedInstanceState));
        lBundle.putString(MoviesContract.MoviesColumns.OVERVIEW, getMovieOverview(savedInstanceState));
        lBundle.putString(MoviesContract.MoviesColumns.RELEASE_DATE, getMovieReleaseDate(savedInstanceState));
        lBundle.putFloat(MoviesContract.MoviesColumns.VOTE_AVG, getVoteAverage(savedInstanceState));
        lBundle.putString(MoviesContract.MoviesColumns.POSTER_PATH, getMoviePosterPath(savedInstanceState));
        lBundle.putInt(MoviesContract.MoviesColumns.VOTE_COUNT, getVoteCount(savedInstanceState));
        return lBundle;
    }

    public static void setMovieDetailsBundleInSavedState(Bundle aOutState, Bundle aMovieDetails) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "setMovieDetailsBundleInSavedState() is called!");
        aOutState.putString(MoviesContract.MoviesColumns.TITLE, getMovieTitle(aMovieDetails));
        aOutState.putString(MoviesContract.MoviesColumns.OVERVIEW, getMovieOverview(aMovieDetails));
        aOutState.putString(MoviesContract.MoviesColumns.RELEASE_DATE, getMovieReleaseDate(aMovieDetails));
        aOutState.putFloat(MoviesContract.MoviesColumns.VOTE_AVG, getVoteAverage(aMovieDetails));
        aOutState.putString(MoviesContract.MoviesColumns.POSTER_PATH, getMoviePosterPath(aMovieDetails));
        aOutState.putInt(MoviesContract.MoviesColumns.VOTE_COUNT, getVoteCount(aMovieDetails));
    }
}
