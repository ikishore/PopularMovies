package udacity.kishore.popularmovies.ui;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import udacity.kishore.popularmovies.Constants;
import udacity.kishore.popularmovies.R;
import udacity.kishore.popularmovies.service.PopularMoviesService;
import udacity.kishore.popularmovies.utils.CallbackUtil;
import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * Created by Kishore on 26/11/15.
 */
public class InitializationFragment extends Fragment implements CallbackUtil {
    private static final String TAG = "InitializationFragment";
    private Activity mActivity = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onCreate() is called!");
        setHasOptionsMenu(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onCreateView() is called!");
        return inflater.inflate(R.layout.fragment_initilization_view, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onAttach() is called!");
        mActivity = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onResume() is called!");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onActivityCreated() is called!");

        PopularMoviesResultReceiver mReceiver = new PopularMoviesResultReceiver(new Handler(), this);
        Intent intent = new Intent(mActivity, PopularMoviesService.class);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra(Constants.SERVICE_REQ_TYPE, Constants.REQ_TYPE_GET_POP_MOVIES);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Starting service to fetch popular movies information!");
        mActivity.startService(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onPause() is called!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onDetach() is called!");
        mActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onDestroyView() is called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onDestroy() is called!");
    }

    @Override
    public void Callback(int aStatus, Bundle aResultData) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Callback() is called!, aStatus: "+aStatus);

        switch (aStatus) {
            case PopularMoviesService.STATUS_RUNNING :
                // Continue showing progressbar
                break;

            case PopularMoviesService.STATUS_FINISHED :
                // Move to PopularMoviesListFragment
                displayPopularMoviesList();
                break;

            case PopularMoviesService.STATUS_ERROR :
            default :
                LogUtil.log(LogUtil.LOG_TYPE_ERROR, TAG, "Error while fetching Movies list: "+aStatus);
                Toast.makeText(mActivity, R.string.error_no_data_cnxn, Toast.LENGTH_SHORT).show();
                mActivity.finish();
                //TODO Show error message and close
                break;
        }
    }

    private void displayPopularMoviesList() {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "displayPopularMoviesList() is called!");
        Intent lIntent = new Intent(mActivity, PopularMoviesList.class);
        mActivity.startActivity(lIntent);
    }
}
