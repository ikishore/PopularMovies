package udacity.kishore.popularmovies.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import udacity.kishore.popularmovies.utils.CallbackUtil;
import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * Created by Kishore on 24/11/15.
 */
public class PopularMoviesResultReceiver extends ResultReceiver {
    private static final String TAG = "PopularMoviesResultReceiver";
    private CallbackUtil mCallback;


    public PopularMoviesResultReceiver(Handler handler, CallbackUtil aCallBack) {
        super(handler);
        mCallback = aCallBack;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onReceiveResult() is called!, resultCode: "+resultCode);
        if (null != mCallback) {
            mCallback.Callback(resultCode, resultData);
        }
    }
}
