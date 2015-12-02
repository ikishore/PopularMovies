package udacity.kishore.popularmovies.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import udacity.kishore.popularmovies.R;
import udacity.kishore.popularmovies.provider.MoviesContract;
import udacity.kishore.popularmovies.provider.PopularMoviesProvider;
import udacity.kishore.popularmovies.utils.CommonUtils;
import udacity.kishore.popularmovies.utils.FragmentListener;
import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {

    private static final String TAG = "PopularMoviesListFragment";
    private Activity mActivity = null;
    private static final int LOAD_POPULAR_MOVIES = 1;
    private GridView mMoviesGrid = null;
    private CursorAdapter mMoviesAdapter = null;
    private Cursor mCursor = null;

    private FragmentListener mListener;

    public PopularMoviesListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onCreateView() is called!");
        View lRoot =  inflater.inflate(R.layout.fragment_popular_movies_list, container, false);
        mMoviesGrid = (GridView) lRoot.findViewById(R.id.movie_list);

        mMoviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCursor.moveToPosition(position);
                mListener.onFragmentInteraction(FragmentListener.TXN_TYPE_SELECT_MOVIE, CommonUtils.prepareBundleWithMovieDetails(mCursor));
            }
        });

        mMoviesAdapter = new CursorAdapter(mActivity, null, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.grid_movie_item, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView lTitle = null;
                lTitle = (TextView) view.findViewById(R.id.movie_title);
                lTitle.setText(cursor.getString(MoviesContract.INDEX_TITLE));
                String lImageUri = CommonUtils.getImageUri(cursor.getString(MoviesContract.INDEX_POSTER_PATH));
                LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "lImageUri: " + lImageUri);

                ImageView lImageView = (ImageView) view.findViewById(R.id.movie_poster);
                Picasso.with(context).load(lImageUri).resizeDimen(R.dimen.grid_movies_item_width, R.dimen.grid_movies_item_height).into(lImageView);
            }
        };
        return lRoot;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onAttach() is called!");
        mActivity = getActivity();
        mListener = (FragmentListener) mActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onStart() is called!");
        getLoaderManager().initLoader(LOAD_POPULAR_MOVIES, null, this);
        mActivity.setTitle(R.string.app_name);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onResume() is called!");
        mMoviesGrid.setAdapter(mMoviesAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onPause() is called!");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onActivityCreated() is called!");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onStop() is called!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onDestroyView() is called!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onDetach() is called!");
        mActivity = null;
        mActivity = null;
        mCursor = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onDestroy() is called!");
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onCreateLoader() is called!, id: " + id);
        Loader lResult = null;
        switch (id) {
            case LOAD_POPULAR_MOVIES :
                int lSortOrder = PreferenceManager.getDefaultSharedPreferences(mActivity).getInt("pref_sort_by", 0);
                String lSortParam = (0 == lSortOrder) ? MoviesContract.MoviesColumns.POPULARITY + " DESC" : MoviesContract.MoviesColumns.VOTE_AVG + " DESC";
                lResult = new CursorLoader(
                        this.getActivity(),
                        PopularMoviesProvider.CONTENT_URI_MOVIES, null, null, null, lSortParam);
                break;

            default:
                break;
        }
        return lResult;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onLoadFinished() is called!, id: "+getId());
        mMoviesAdapter.swapCursor(data);
        mCursor = data;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onLoaderReset() is called!, id: "+getId());
        mMoviesAdapter.swapCursor(null);
        mCursor = null;
    }

    public void notifyPreferenceChange() {
        getLoaderManager().restartLoader(LOAD_POPULAR_MOVIES, null, this);
    }
}
