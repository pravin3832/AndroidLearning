package learning.android.tenmarks.com.androidlearning.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Talha on 11/4/15.
 */
public class LearnDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Email";
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String ADDRESS = "Address";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ", ";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY," +
                    NAME + TEXT_TYPE + COMMA_SEP +
                    ADDRESS + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LearnAndroid.db";

    public LearnDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO not necessarily you delete db here, you can do what's required.
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long addNameAddress(String name, String address) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, name);
        cv.put(ADDRESS, address);
        SQLiteDatabase db = this.getWritableDatabase();
        long rowId = db.insert(TABLE_NAME, null, cv);
        return rowId;
    }


    public Cursor fetchAllData() {
        String[] projection = {
                ID,
                NAME,
                ADDRESS
        };

        String sortOrder = ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(
                TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return c;
    }
}
