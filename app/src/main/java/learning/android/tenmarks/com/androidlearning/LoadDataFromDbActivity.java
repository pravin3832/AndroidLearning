package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import learning.android.tenmarks.com.androidlearning.database.LearnDatabaseHelper;


public class LoadDataFromDbActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data_from_db);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_data_from_db, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_load_data_from_db, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            ListView listView = (ListView) view.findViewById(R.id.listView);
            LearnDatabaseHelper dbHelper = new LearnDatabaseHelper(getActivity());
            Cursor cursor = dbHelper.fetchAllData();
            if (cursor != null) {
                DataBaseAdapter adapter = new DataBaseAdapter(getActivity(), cursor, false);
                listView.setAdapter(adapter);
            }
        }


        public static class DataBaseAdapter extends CursorAdapter {

            LayoutInflater mInflater = null;

            public DataBaseAdapter(Context context, Cursor cursor, boolean autoQuery) {
                super(context, cursor, autoQuery);

                mInflater = LayoutInflater.from(context);
            }

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return mInflater.inflate(R.layout.row_data_from_db, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                ((TextView) view.findViewById(R.id.labelName)).setText(cursor.getString(cursor.getColumnIndexOrThrow("Name")));
                ((TextView) view.findViewById(R.id.labelAddress)).setText(cursor.getString(cursor.getColumnIndexOrThrow("Address")));
            }

        }
    }
}
