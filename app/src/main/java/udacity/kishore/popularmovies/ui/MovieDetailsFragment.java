package udacity.kishore.popularmovies.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ConcurrentModificationException;

import udacity.kishore.popularmovies.R;
import udacity.kishore.popularmovies.provider.MoviesContract;
import udacity.kishore.popularmovies.utils.CommonUtils;
import udacity.kishore.popularmovies.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {
    private Bundle mMovieDetails;
    private TextView mTitleView = null;
    private TextView mReleaseDateView = null;
    private TextView mVoteAverageView = null;
    private TextView mVoteCountView = null;
    private TextView mOverviewView = null;
    private ImageView mMoviePosterView = null;

    private static final String TAG = "MovieDetailsFragment";

    public MovieDetailsFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    public static MovieDetailsFragment newInstance(Bundle aBundle) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        fragment.setArguments(aBundle);
        return fragment;
    }

    public void setmMovieDetails(Bundle mMovieDetails) {
        this.mMovieDetails = mMovieDetails;
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "Movie details :" + mMovieDetails);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View lRoot =  inflater.inflate(R.layout.fragment_movie_details, container, false);
        mTitleView = (TextView) lRoot.findViewById(R.id.movie_title);
        mReleaseDateView = (TextView) lRoot.findViewById(R.id.release_date);
        mVoteAverageView = (TextView) lRoot.findViewById(R.id.vote_average);
        mOverviewView = (TextView) lRoot.findViewById(R.id.overview);
        mMoviePosterView = (ImageView) lRoot.findViewById(R.id.movie_poster);
        mVoteCountView = (TextView) lRoot.findViewById(R.id.voters_count);
        return lRoot;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTitleView.setText(CommonUtils.getMovieTitle(mMovieDetails));
        mReleaseDateView.setText(CommonUtils.getMovieReleaseDate(mMovieDetails));
        mOverviewView.setText(CommonUtils.getMovieOverview(mMovieDetails));
        mVoteAverageView.setText(CommonUtils.getVoteAverage(mMovieDetails).toString() + "/10");
        mVoteCountView.setText(CommonUtils.getVoteCount(mMovieDetails).toString());
        String lImageUri = CommonUtils.getImageUri(CommonUtils.getMoviePosterPath(mMovieDetails));
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "lImageUri: " + lImageUri);
        Picasso.with(getActivity()).load(lImageUri).resizeDimen(R.dimen.movie_details_img_width, R.dimen.movie_details_img_height).into(mMoviePosterView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.log(LogUtil.LOG_TYPE_DEBUG, TAG, "onAttach() is called!");
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


}
