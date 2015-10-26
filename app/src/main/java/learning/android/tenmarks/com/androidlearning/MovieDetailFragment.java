package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import learning.android.tenmarks.com.androidlearning.responses.MovieResponse;

public class MovieDetailFragment extends Fragment {

    private static final String TAG = "MovieDetailFragment";
    private static final String ARG_MOVIE_ID = "movie_id";
    private long mMovieId;
    private ImageLoader imageLoader;

    private TextView mTextViewReview;
    private TextView mTextViewTitle;
    private NetworkImageView mImageView;
    RequestQueue requestQueue = null;


    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(long id) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MOVIE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // TODO Pay Attention will throw an exception if you don't do it.
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieId = getArguments().getLong(ARG_MOVIE_ID, 0);
        }

        if (mMovieId == 0) {
            // TODO id was null & I don't wanna proceed further
            getActivity().getSupportFragmentManager().popBackStack();
        }

        requestQueue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewTitle = (TextView) view.findViewById(R.id.text_view_title);
        mTextViewReview = (TextView) view.findViewById(R.id.text_view_review);
        mImageView = (NetworkImageView) view.findViewById(R.id.poster);
        imageLoader = new ImageLoader(Volley.newRequestQueue(getActivity()),
                new LruBitmapCache(getDefaultLruCacheSize()));


        showDetails(mMovieId);
    }

    public int getDefaultLruCacheSize() {
        final int maxMemory =
                (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    public void showDetails(long id) {
        mMovieId = id;
        Request<MovieResponse> request = MovieResponse.getMovieDetailRequest(mMovieId, new Response.Listener<MovieResponse>() {
            @Override
            public void onResponse(MovieResponse response) {
                if (response != null) {
                    Log.d(TAG, "Inside onResponse: " + response.title);
                    mTextViewTitle.setText(response.title);
                    mTextViewReview.setText(response.overview);
                    String posterPath = "http://image.tmdb.org/t/p/w342" + response.poster_path;
                    mImageView.setImageUrl(posterPath, imageLoader);
                    Log.d(TAG, "Inside onResponse: " + posterPath);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Some error while getting response", error);
            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
