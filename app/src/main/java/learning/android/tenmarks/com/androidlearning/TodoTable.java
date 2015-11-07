package learning.android.tenmarks.com.androidlearning;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by Talha on 11/6/15.
 *
 * This class represents a table in our database.
 *
 */
public class TodoTable implements BaseColumns {

    // TODO advantage of implementing the interface BaseColumns
    // TODO is that you get the _ID column by default which serves
    // TODO as primary key...and is required in most of the stuff in Android

    // Database table
    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_DESCRIPTION = "description";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_TODO
            + "("
            + _ID + " integer primary key autoincrement, "
            + COLUMN_CATEGORY + " text not null, "
            + COLUMN_SUMMARY + " text not null,"
            + COLUMN_DESCRIPTION
            + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        // TODO just displaying a warning in logs
        Log.w(TodoTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(database);
    }

}
