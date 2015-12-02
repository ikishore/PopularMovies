package udacity.kishore.popularmovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import udacity.kishore.popularmovies.Constants;
import udacity.kishore.popularmovies.R;
import udacity.kishore.popularmovies.service.PopularMoviesService;
import udacity.kishore.popularmovies.utils.FragmentListener;

public class PopularMoviesList extends AppCompatActivity implements FragmentListener {

    private static final String TAG = "PopularMoviesList";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_movies_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sort_by) {
            //PopularMoviesListFragment lMoviesListFragement = (PopularMoviesListFragment) getFragmentManager().findFragmentById();
            SortPreferenceFragment lSortPrefFragment = new SortPreferenceFragment();
            lSortPrefFragment.show(getSupportFragmentManager(), "Dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(int aTxID, Bundle aData) {
        Intent lIntent;
        switch (aTxID) {
            case FragmentListener.TXN_TYPE_SELECT_MOVIE :
                // if Single pane, Start Movie Detail activity
                lIntent = new Intent(this, MovieDetailsActivity.class);
                lIntent.putExtra(Constants.KEY_MOVIE_DETAIL, aData);
                startActivity(lIntent);
                // If Dual pane, pass selected movie info to detail fragment
                break;
            case FragmentListener.TXN_TYPE_SORT_BY_CHANGED :
                // notify fragment to reload loader
                PopularMoviesListFragment lFragment = (PopularMoviesListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                lFragment.notifyPreferenceChange();
                break;

            default :
                break;
        }
        return;
    }
}
