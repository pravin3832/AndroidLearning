package learning.android.tenmarks.com.androidlearning;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.support.v4.widget.SimpleCursorAdapter;

/**
 * Created by Talha on 11/5/15.
 */
public class UserDictionaryActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getListView(); Always gives you a default list view.

        // TODO notice that UserDictionary is in android.provider package
        String[] projection = {
                    UserDictionary.Words._ID,
                    UserDictionary.Words.WORD,
                    UserDictionary.Words.LOCALE
                    };

        // getContentResolver.query

        // TODO here UserDictionary is the provider
        // TODO Words is the table and CONTENT_URI contains the Content URI of the "words" table.
        // TODO there are 3 things in a content uri
        // TODO 1 the scheme which is content:// in this case
        // TODO 2 the authority which is user_dictionary
        // TODO 3 the path which is the table "words"
        // TODO the uri looks something like content://user_dictionary/words
        Cursor cursor = getContentResolver().query(UserDictionary.Words.CONTENT_URI,
                                                    projection,
                                                    null,
                                                    null,
                                                    null);

        String[] wordListColumns = {
                UserDictionary.Words.WORD,
                UserDictionary.Words.LOCALE
        };

        int[] wordListItems = {R.id.dictWord, R.id.dictLocale};


        if (cursor != null && cursor.getCount() > 0) {
            getListView().setAdapter(new SimpleCursorAdapter(this, R.layout.row_dictionary_word,
                    cursor, wordListColumns, wordListItems, 0));
        }
    }
}
