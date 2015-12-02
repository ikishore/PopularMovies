package udacity.kishore.popularmovies.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import udacity.kishore.popularmovies.Constants;
import udacity.kishore.popularmovies.provider.MoviesContract.MoviesColumns;
import udacity.kishore.popularmovies.provider.PopularMoviesProvider;
import udacity.kishore.popularmovies.utils.CommonUtils;
import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * Created by Kishore on 12/11/15.
 */
public class PopularMoviesService extends IntentService {
    private static final String TAG = "PopularMoviesService";

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;


    public PopularMoviesService() {
        super(PopularMoviesService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onHandleIntent() is called!");

        final ResultReceiver lResultReceiver = intent.getParcelableExtra("receiver");

        int lReqType = intent.getIntExtra(Constants.SERVICE_REQ_TYPE, Constants.REQ_TYPE_DEFAULT);

        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Service request type: "+lReqType);
        String lHttpUrl;
        String lResult;

        switch (lReqType) {
            case Constants.REQ_TYPE_GET_POP_MOVIES :
                getContentResolver().delete(PopularMoviesProvider.CONTENT_URI_MOVIES, null, null);
                lHttpUrl = CommonUtils.getUri(Constants.REQ_TYPE_GET_POP_MOVIES);
                lResultReceiver.send(STATUS_RUNNING, Bundle.EMPTY);
                try {
                    lResult = downloadData(lHttpUrl);
                    if (!TextUtils.isEmpty(lResult)) {
                        parsePopularMoviesResult(lResult);
                    }
                } catch (Exception e) {
                    handleException(lResultReceiver, e);
                    return;
                }
                break;

            //TODO: Required only GENRES need to be displayed
            case Constants.REQ_TYPE_GET_MOVIES_GENRES :
                lHttpUrl = CommonUtils.getUri(Constants.REQ_TYPE_GET_MOVIES_GENRES);
                lResultReceiver.send(STATUS_RUNNING, Bundle.EMPTY);
                try {
                    lResult = downloadData(lHttpUrl);
                } catch (Exception e) {
                    handleException(lResultReceiver, e);
                    return;
                }
                break;

            case Constants.REQ_TYPE_DEFAULT :
            default:
                LogUtil.log(LogUtil.LOG_TYPE_ERROR, TAG, "Invalid Service request type: "+lReqType);
                break;
        }

        lResultReceiver.send(STATUS_FINISHED, Bundle.EMPTY);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Stopping the Service!");
    }


    private void handleException(ResultReceiver receiver, Exception e) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "handleException() is called!");
        e.printStackTrace();
        Bundle bundle = new Bundle();
        bundle.putString(Intent.EXTRA_TEXT, e.toString());
        receiver.send(STATUS_ERROR, bundle);
    }

    private String downloadData(String requestUrl) throws IOException, DownloadException {
        InputStream inputStream;
        HttpURLConnection urlConnection;
        String lResponse = null;

        /* forming th java.net.URL object */
        URL url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        /* optional request header */
        urlConnection.setRequestProperty("Content-Type", "application/json");
        /* optional request header */
        urlConnection.setRequestProperty("Accept", "application/json");
        /* for Get request */
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();

        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "HTTP status: "+statusCode);

        if (Constants.HTTP_RESPONSE_SUCCESS == statusCode) {
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            lResponse = convertInputStreamToString(inputStream);

        } else {
            LogUtil.log(LogUtil.LOG_TYPE_ERROR, TAG, "HTTP request failed with error:" + statusCode);
            throw new DownloadException("Failed to fetch data!!");
        }
        return lResponse;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        if (null != inputStream) {
            inputStream.close();
        }

        return result;
    }

    private void parsePopularMoviesResult(String result) {
        ArrayList<ContentProviderOperation> lProviderOPerations = new ArrayList<ContentProviderOperation>();

        try {
            JSONObject lResponse = new JSONObject(result);
            JSONArray lList = lResponse.optJSONArray("results");
            for (int i = 0; i < lList.length(); i++) {
                JSONObject lCurrentMovie = lList.optJSONObject(i);
                lProviderOPerations.add(fillMoviesContentProviderOp(lCurrentMovie));
            }
            getContentResolver().applyBatch(PopularMoviesProvider.PROVIDER_NAME, lProviderOPerations);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ContentProviderOperation fillMoviesContentProviderOp(JSONObject lCurrentMovie) {
        ContentProviderOperation.Builder lCurrent = ContentProviderOperation.newInsert(PopularMoviesProvider.CONTENT_URI_MOVIES);

        try {
            lCurrent.withValue(MoviesColumns.MOVIE_ID, lCurrentMovie.getInt("id"));
            lCurrent.withValue(MoviesColumns.LANGUAGE, lCurrentMovie.getString(MoviesColumns.LANGUAGE));
            lCurrent.withValue(MoviesColumns.OVERVIEW, lCurrentMovie.getString(MoviesColumns.OVERVIEW));
            lCurrent.withValue(MoviesColumns.POPULARITY, lCurrentMovie.getDouble(MoviesColumns.POPULARITY));
            lCurrent.withValue(MoviesColumns.POSTER_PATH, lCurrentMovie.getString(MoviesColumns.POSTER_PATH));
            lCurrent.withValue(MoviesColumns.RELEASE_DATE, lCurrentMovie.getString(MoviesColumns.RELEASE_DATE));
            lCurrent.withValue(MoviesColumns.TITLE, lCurrentMovie.getString(MoviesColumns.TITLE));
            lCurrent.withValue(MoviesColumns.VOTE_AVG, lCurrentMovie.getDouble(MoviesColumns.VOTE_AVG));
            lCurrent.withValue(MoviesColumns.VOTE_COUNT, lCurrentMovie.getInt(MoviesColumns.VOTE_COUNT));
            lCurrent.withValue(MoviesColumns.IS_VIDEO, (lCurrentMovie.getBoolean(MoviesColumns.IS_VIDEO)) ? 1 : 0);
        } catch (Exception e) {
            LogUtil.log(LogUtil.LOG_TYPE_ERROR, TAG, "Error while parsing JSon response!");
            e.printStackTrace();
        }
        return lCurrent.build();
    }

    public class DownloadException extends Exception {

        public DownloadException(String message) {
            super(message);
        }

        public DownloadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
