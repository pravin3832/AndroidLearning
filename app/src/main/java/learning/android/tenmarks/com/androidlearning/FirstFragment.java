package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import learning.android.tenmarks.com.androidlearning.responses.DiscoverResponse;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

// TODO Read the following comments:

/**
 * @author Talha October 26th 2015
 * A fragment represents a portion of an Activity. Fragments were introduced in
 * Android 3.0 (API Level 11), that was time when tablets started to came out
 * and there was a need of a more dynamic UI.
 * Fragment can be declared in the XML file of the activiyt or can be added via code.
 * Fragment has its own life cycle and shares some events with the Activity holding it.
 * Fragment must be added to an Activity, can't be used without that.
 * Fragment lives inside a ViewGroup in an Activity.
 */
public class FirstFragment extends ListFragment {

    private static final String TAG = "FirstFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnMovieClicked mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FirstFragment.
     */
    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // called when the fragment is about to draw itself.
        // Inflate the layout for this fragment - similar as we do in setContent for activity
        // Also take a look at the Activity class LearningFragmentsActivity, its parent is now FragmentActivity
        // return inflater.inflate(R.layout.fragment_first, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * This is the callback you get when the view is created, you can
     * reference the UI Widgets you might have declared in XML files. And put in some logic
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO pay attention, in activity we would simply do findViewById, here we need to use this view
        // TODO passed in arguments to onViewCreated. All views will be referenced though this view
        /*TextView labelTextView = (TextView) view.findViewById(R.id.text_view_label);
        labelTextView.setText("You are now seeing a FRAGMENT");*/


        // so let's just add the request to fetch some movies data as we did in MovieDbDiscoverActivity
        // TODO pay attention, to get context in a Fragment we call getActivity().
        // TODO By the way, we should move this volley request queue to global in app. ASSIGNMENT
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        Request<DiscoverResponse> request = DiscoverResponse.getDiscoverRequest(new Response.Listener<DiscoverResponse>() {
            @Override
            public void onResponse(DiscoverResponse response) {
                Log.d(TAG, "Got response from movie db discover api");
                if (response != null) {
                    Log.d(TAG, "Got response with size: " + response.results.size());
                    MoviesAdapter adapter = new MoviesAdapter(getActivity(), response.results);
                    // TODO pay attention: I am calling getListView here and not doing the typical findViewById
                    // getListView().setAdapter(adapter);
                    setListAdapter(adapter);
                    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.d(TAG, "ListItemClicked at position: " + position);
                            if (mListener != null) {
                                mListener.onMovieClicked(id);
                            }
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Some error in movie db discover api", error);
            }
        });
        requestQueue.add(request);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMovieClicked) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMovieClicked");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnMovieClicked {
        public void onMovieClicked(long id);
    }

}
