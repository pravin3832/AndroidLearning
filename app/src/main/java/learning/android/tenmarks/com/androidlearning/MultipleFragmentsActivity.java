package learning.android.tenmarks.com.androidlearning;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


/**
 * TODO read this document: http://developer.android.com/guide/components/fragments.html
 */
public class MultipleFragmentsActivity extends FragmentActivity implements FirstFragment.OnMovieClicked {

    private static final String TAG = "MultipleFragmentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_fragments);

        // TODO Pay Attention, we are checking for savedInstanceState to avoid
        // TODO adding this fragment repeatedly.

        if (savedInstanceState == null) {
            FirstFragment frag = FirstFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multiple_fragments, menu);
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
        Log.d("", "Item clicke with id: " + id);
        MovieDetailFragment frag = MovieDetailFragment.newInstance(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        trans.replace(R.id.container, frag);
        // TODO comment the addToBackStack and then see what happens
        trans.addToBackStack(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
        trans.commit();
    }
}
