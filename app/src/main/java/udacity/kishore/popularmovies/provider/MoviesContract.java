package udacity.kishore.popularmovies.provider;

/**
 * Created by Kishore on 12/11/15.
 */
public class MoviesContract {

    public static final String AUTHORITY = "com.android.contacts";

    public static final String MOVIES_TABLE_NAME = "movies";


    public static final int INDEX_TITLE = 1;
    public static final int INDEX_MOVIE_ID = 2;
    public static final int INDEX_OVERVIEW = 3;
    public static final int INDEX_POSTER_PATH = 4;
    public static final int INDEX_RELEASE_DATE = 5;
    public static final int INDEX_LANGUAGE = 6;
    public static final int INDEX_POPULARITY = 7;
    public static final int INDEX_VOTE_COUNT = 8;
    public static final int INDEX_VOTE_AVG = 9;
    public static final int INDEX_IS_VIDEO = 10;
    public static final int INDEX_IS_FAV = 11;


    public interface MoviesColumns {
        String TITLE = "title";

        String MOVIE_ID = "movie_id";

        String RELEASE_DATE = "release_date";

        String POSTER_PATH = "poster_path";

        String VOTE_AVG = "vote_average";

        String VOTE_COUNT = "vote_count";

        String IS_VIDEO = "video";

        String POPULARITY = "popularity";

        String OVERVIEW = "overview";

        String LANGUAGE = "original_language";

        String IS_FAV = "favorite";
    }



}
