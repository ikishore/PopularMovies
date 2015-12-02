package udacity.kishore.popularmovies;

import android.app.Application;

import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * Created by Kishore on 12/11/15.
 */
public class PopularMoviesApp extends Application {
    private static final String TAG = "PopularMoviesApp";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Application is created!");
    }
}
