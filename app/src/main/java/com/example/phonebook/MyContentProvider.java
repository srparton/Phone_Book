package com.example.phonebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    // defining authority so that other application can access it
    private static final String AUTHORITY = "com.example.phonebook.MyContentProvider";
    // for creating database object to perform query
    private static final String DATABASE_NAME = "phone_bookDB.db";
    // database table name
    private static final String TABLE_CONTACTS = "contacts";
    // for defining column names of the table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FNAME = "fName";
    public static final String COLUMN_LNAME = "lName";
    public static final String COLUMN_PHONENUM = "quantity";
    // declaring version of the database
    private static final int DATABASE_VERSION = 1;
    // constant used for accessing the table
    public static final int CONTACTS = 1;
    // constant used for access a particular row of table
    public static final int CONTACTS_ID = 2;
    //creating a URI and parsing it
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +TABLE_CONTACTS);
    private SQLiteDatabase sqlDB;
    private UriMatcher uriMatcher;
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;
        if(uriType == CONTACTS){
            rowsDeleted = sqlDB.delete(TABLE_CONTACTS,selection,selectionArgs);
        }
        else{
            throw new UnsupportedOperationException("Not yet implemented");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        long id = 0;
        if (uriType == CONTACTS){
            id = sqlDB.insert(TABLE_CONTACTS,null,values);
        }
        else{
            throw new UnsupportedOperationException("Unknown URI: "+uri + "is not supported");
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return uri.parse(TABLE_CONTACTS + "/" + id);
    }

    @Override
    public boolean onCreate() {
        //step 1 statement to match the content URI
        // every time user access table under content provider
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //step 2 statement to access whole table
        uriMatcher.addURI(AUTHORITY, TABLE_CONTACTS,CONTACTS );
        //step 3 statement to access a particular row of the table
        uriMatcher.addURI(AUTHORITY,TABLE_CONTACTS + "/#" ,CONTACTS_ID );
        //create a database
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        if(sqlDB != null)
            return true;
        else
            return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_CONTACTS);
        int uriType = uriMatcher.match(uri);
        Cursor cursor = null;
        if (uriType == CONTACTS){
            cursor = queryBuilder.query(sqlDB, projection, selection,
                    selectionArgs, null, null, sortOrder);
        }
        else{
            throw new UnsupportedOperationException("Unknown URI: "+uri + "is not supported");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowsUpdated = 0;
        if (uriType == CONTACTS)
            rowsUpdated = sqlDB.update(TABLE_CONTACTS,values,selection,selectionArgs);
        else
            throw new UnsupportedOperationException("Not yet implemented");
        return rowsUpdated;
    }

    // Creating a database
    private static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create_product_table = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_FNAME +
                    " TEXT, "  + COLUMN_LNAME +
                    " TEXT, " +COLUMN_PHONENUM + "INTEGER )";
            sqLiteDatabase.execSQL(create_product_table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACTS);
            onCreate(sqLiteDatabase);

        }
    }
}