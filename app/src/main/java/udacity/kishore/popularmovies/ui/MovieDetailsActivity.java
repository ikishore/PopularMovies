package udacity.kishore.popularmovies.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import udacity.kishore.popularmovies.Constants;
import udacity.kishore.popularmovies.R;
import udacity.kishore.popularmovies.utils.CommonUtils;
import udacity.kishore.popularmovies.utils.LogUtil;

public class MovieDetailsActivity extends AppCompatActivity {
    Bundle mMovieDetails;
    private static final String TAG = "MovieDetailsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null == savedInstanceState) {
            mMovieDetails = getIntent().getBundleExtra(Constants.KEY_MOVIE_DETAIL);
        } else {
            // restore movie details info from saved state
            mMovieDetails = CommonUtils.getMovieDetailsBundleFromSavedState(savedInstanceState);
        }
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Movie details: " + mMovieDetails);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MovieDetailsFragment lFragment = (MovieDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        lFragment.setmMovieDetails(mMovieDetails);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Take movie details as backup
        CommonUtils.setMovieDetailsBundleInSavedState(outState, mMovieDetails);
    }

    @Override
    protected void onResume() {
        super.onResume();
        android.app.Fragment lDetailsFragment = getFragmentManager().findFragmentById(R.id.detail_fragment);
    }

}
