package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.zip.Inflater;

import learning.android.tenmarks.com.androidlearning.responses.DiscoverResponse;


public class MovieDbDiscoverActivity extends Activity {

    private static final String TAG = "MovieDbDiscoverActivity";
    private TextView textViewPageNumber;
    private TextView textViewTotalResults;
    private ListView listViewMovies;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_db_discover);

        // get a reference to all the view we will be setting
        textViewPageNumber = (TextView) findViewById(R.id.textview_page_info);
        textViewTotalResults = (TextView) findViewById(R.id.textview_total_results);
        listViewMovies = (ListView) findViewById(R.id.listview_movies);
        mProgressBar = (ProgressBar) findViewById(android.R.id.empty);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Request<DiscoverResponse> request = DiscoverResponse.getDiscoverRequest(new Response.Listener<DiscoverResponse>() {
            @Override
            public void onResponse(DiscoverResponse response) {
                Log.d(TAG, "Got response from movie db");
                if (response != null) {
                    // just ignore the logs for now, but these are handy to see quickly what's going on behind the scene
                    Log.d(TAG, "Response page: " + response.page);
                    Log.d(TAG, "Response total_pages: " + response.total_pages);
                    Log.d(TAG, "Response total_pages: " + response.total_pages);
                    setUpUi(response);
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


    private void setUpUi(final DiscoverResponse response) {
        textViewPageNumber.setText("Pag: " + response.page + " / " + response.total_pages);
        textViewTotalResults.setText("Total " + response.total_results + " movies");
        listViewMovies.setAdapter(new MoviesAdapter(this, response.results));
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_db_discover, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class MoviesAdapter extends BaseAdapter {

        List<DiscoverResponse.DiscoverResult> mItems;
        Context mContext;
        LayoutInflater mInflater;


        public MoviesAdapter(Context context, List<DiscoverResponse.DiscoverResult> responseList) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
            mItems = responseList;
        }

        @Override
        public int getCount() {
            return (mItems != null) ? mItems.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return (mItems != null && position > -1 && position < mItems.size()) ? mItems.get(position) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;//should return ITEM UNIQUE ID, okay for now
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(
                        R.layout.row_movie_db, null);
                new ViewHolder(convertView);
            }

            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.tvTitle.setText(mItems.get(position).title);
            holder.tvOverview.setText(mItems.get(position).overview);
            return convertView;
        }

    }

    private static class ViewHolder {

        public TextView tvTitle;
        public TextView tvOverview;

        public ViewHolder(View parent) {
            tvTitle = (TextView) parent.findViewById(R.id.title);
            tvOverview = (TextView) parent.findViewById(R.id.overview);
            parent.setTag(ViewHolder.this);
        }
    }
}
