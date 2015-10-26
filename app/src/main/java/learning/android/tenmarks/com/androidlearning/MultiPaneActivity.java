package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.test.mock.MockDialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MultiPaneActivity extends FragmentActivity implements FirstFragment.OnMovieClicked {

    private static final String TAG = "MultiPaneActivity";

    boolean isDualPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_pane);

        View detailsFrame = findViewById(R.id.detail_container);
        isDualPane = (detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE);

        if (savedInstanceState == null) {
            FirstFragment fragment = FirstFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.list_container, fragment).commit();
        }

    }


    private void showDetails(long id) {
        if (isDualPane) {
            // MovieDetailFragment detailFragment = (MovieDetailFragment) find
            MovieDetailFragment detailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_container);
            if (detailFragment != null) {
                // // notice the id of the container view group
                detailFragment.showDetails(id);
                Log.d(TAG, "updated detail fragment");
            } else {
                addDetailFragment(R.id.detail_container, id);
                Log.d(TAG, "added detail fragment");
            }
            // TODO Assignment add a progress bar to the detail fragment when its loading.
            // TODO Assignment disable click on list fragment when the detail is loading.
            // TODO Assignment enable click on list fragment when the detail is done loading or fails.
        } else {
            // TODO Observe the behavior of app when the you click on list item and suddenly rotate the device.???
            addDetailFragment(R.id.list_container, id);
        }
    }


    private void addDetailFragment(int containerId, long movieId) {
        MovieDetailFragment detailFragment = MovieDetailFragment.newInstance(movieId);
        getSupportFragmentManager().beginTransaction().replace(containerId, detailFragment).addToBackStack(null).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_pane, menu);
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

    @Override
    public void onMovieClicked(long id) {
        showDetails(id);
    }
}
