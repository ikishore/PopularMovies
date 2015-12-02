package udacity.kishore.popularmovies.utils;

import android.util.Log;

import udacity.kishore.popularmovies.BuildConfig;

/**
 * Created by Kishore on 12/11/15.
 *
 * Log utility to control all logs
 */
public class LogUtil {

    private static final String DEFAULT_TAG = "Popular Movies App";

    // Flag to group all app logs with single TAG
    private static final boolean USE_DEFAULT_TAG_ALWAYS = false;

    // Control debug/info logs based on this flag
    private static final boolean DEBUG_MODE = BuildConfig.ENABLE_DEBUG;

    public static final int LOG_TYPE_DEBUG = 1;
    public static final int LOG_TYPE_WARNING = 2;
    public static final int LOG_TYPE_ERROR = 3;
    public static final int LOG_TYPE_INFO = 4;


    public static void log (final int aLogType, String aTAG, final String aLogMsg) {

        if (USE_DEFAULT_TAG_ALWAYS) {
            aTAG = DEFAULT_TAG;
        }

        switch (aLogType) {
            case LOG_TYPE_INFO :
                if (DEBUG_MODE) {
                    Log.i(aTAG, aLogMsg);
                }
                break;

            case LOG_TYPE_DEBUG :
                if (DEBUG_MODE) {
                    Log.d(aTAG, aLogMsg);
                }
                break;

            case LOG_TYPE_ERROR :
                Log.e(aTAG, aLogMsg);
                break;

            case LOG_TYPE_WARNING :
                Log.w(aTAG, aLogMsg);
                break;

            default :
                Log.e(DEFAULT_TAG, "Wrong TAG used to log!");
                break;
        }
    }
}
