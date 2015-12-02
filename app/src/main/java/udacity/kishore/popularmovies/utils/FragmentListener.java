package udacity.kishore.popularmovies.utils;

import android.os.Bundle;

/**
 * Created by Kishore on 30/11/15.
 */
public interface FragmentListener {

    public static final int TXN_TYPE_SELECT_MOVIE = 1;

    public static final int TXN_TYPE_SORT_BY_CHANGED = 2;

    void onFragmentInteraction(int aTrasactionType, Bundle aData);
}