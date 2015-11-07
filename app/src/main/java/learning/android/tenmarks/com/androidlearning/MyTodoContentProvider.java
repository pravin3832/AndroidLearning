package learning.android.tenmarks.com.androidlearning;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Talha on 11/6/15.
 */
public class MyTodoContentProvider extends ContentProvider {

    private TodoDatabaseHelper databaseHelper;
    private static final int TODOS = 10;
    private static final int TODO_ID = 20;

    private static final String AUTHORITY = "learning.android.tenmarks.com.androidlearning.contentprovider";
    private static final String BASE_PATH = "todos";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY+ "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/todos";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/todo";

    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    // just a normal java static block to initialize the Uri Matcher pre-hand;
    static {
        sUriMatcher.addURI(AUTHORITY, BASE_PATH, TODOS);
        sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TODO_ID);
    }

    @Override
    public boolean onCreate() {
        databaseHelper = new TodoDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // validating that an existing columns is requested
        checkColumns(projection);

        queryBuilder.setTables(TodoTable.TABLE_TODO);

        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case TODOS:
                break;
            case TODO_ID:
                queryBuilder.appendWhere(TodoTable._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case TODOS:
                id = databaseHelper.getWritableDatabase().insert(TodoTable.TABLE_TODO, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + "id");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = databaseHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case TODOS:
                rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO, selection,
                        selectionArgs);
                break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO,
                            TodoTable._ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO,
                            TodoTable._ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase sqlDB = databaseHelper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case TODOS:
                rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO,
                        values,
                        selection,
                        selectionArgs);
                break;
            case TODO_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO,
                            values,
                            TodoTable._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO,
                            values,
                            TodoTable._ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    /**
     * Validates that an existing column is requested.
     * @param projection
     */
    private void checkColumns(String[] projection) {
        String[] available = {TodoTable.COLUMN_CATEGORY, TodoTable.COLUMN_DESCRIPTION, TodoTable.COLUMN_SUMMARY, TodoTable._COUNT};
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(projection));
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
