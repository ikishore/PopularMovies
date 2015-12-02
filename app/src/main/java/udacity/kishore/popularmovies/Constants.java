package udacity.kishore.popularmovies;

import android.os.Build;

/**
 * Created by Kishore on 24/11/15.
 */
public class Constants {
    public static final String SERVICE_REQ_TYPE = "service_request_type";

    public static final int REQ_TYPE_DEFAULT = -1;
    public static final int REQ_TYPE_GET_POP_MOVIES = 1;
    public static final int REQ_TYPE_GET_MOVIES_GENRES = 2;


    public static final int HTTP_RESPONSE_SUCCESS = 200;

    public static final String MDB_API_KEY = BuildConfig.API_KEY;

    /* URL constant for fetching Popular Movies information */
    public static final String URL_GET_POP_MOVIES = "http://api.themoviedb.org/3/discover/movie";

    /* URL constant for fetching Genres information */
    public static final String URL_GET_GENRES = "http://api.themoviedb.org/3/genre/movie/list";

    public static final String IMAGE_BASE_URI = "http://image.tmdb.org/t/p/";

    public static final String IMAGE_SIZE = "w342";

    public static final String INTENT_DISPLAY_MOVIES = "kishore.intent.action.DISPLAY_POPULAR_MOVIES";

    public static final String KEY_MOVIE_DETAIL = "movie_details";

    public static final String PREF_KEY_SORT_BY = "pref_sort_by";
}
