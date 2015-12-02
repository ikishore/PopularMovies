package udacity.kishore.popularmovies.ui;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import udacity.kishore.popularmovies.Constants;
import udacity.kishore.popularmovies.R;
import udacity.kishore.popularmovies.utils.FragmentListener;
import udacity.kishore.popularmovies.utils.LogUtil;

public class SortPreferenceFragment extends DialogFragment {

    SharedPreferences mPrefs;
    private Activity mActivity;
    FragmentListener mListener;
    private static final String TAG = "SortPreferenceFragment";

    public SortPreferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        mListener = (FragmentListener) activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        CharSequence[] lItems = new CharSequence[]{getString(R.string.sort_by_popularity), getString(R.string.sort_by_highest_rating)};
        AlertDialog.Builder lDialog = new AlertDialog.Builder(mActivity);
        lDialog.setTitle(R.string.action_sort_preference);
        lDialog.setPositiveButton(R.string.action_cancel, new PositiveButtonClickListener());
        lDialog.setSingleChoiceItems(lItems, mPrefs.getInt(Constants.PREF_KEY_SORT_BY, 0), selectItemListener);
        return lDialog.create();
    }

    class PositiveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface aDialog, int which) {
            LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Sort preference dialog is Cancelled!");
            aDialog.dismiss();
        }
    }

    DialogInterface.OnClickListener selectItemListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface aDialog, int which) {
            LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Selected sort preference: "+which);
            mPrefs.edit().putInt(Constants.PREF_KEY_SORT_BY, which).apply();
            mListener.onFragmentInteraction(FragmentListener.TXN_TYPE_SORT_BY_CHANGED, null);
            aDialog.dismiss();
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        mListener = null;
        mPrefs = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroyView();
        mActivity = null;
        mListener = null;
        mPrefs = null;
    }
}
