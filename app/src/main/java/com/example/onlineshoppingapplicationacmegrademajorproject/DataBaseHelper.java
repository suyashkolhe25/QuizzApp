package com.example.onlineshoppingapplicationacmegrademajorproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private static final String CREATE_USER_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+COLUMN_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COLUMN_USER_NAME+" TEXT, "+COLUMN_USER_EMAIL+" TEXT, "+COLUMN_USER_PASSWORD+" TEXT )";

    private static final String DROP_USER_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;



    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DROP_USER_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String email) {
        String[] columns = {COLUMN_USER_ID};

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";

        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        boolean result = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return result;
    }

    public boolean checkUsernamePassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        boolean result = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return result;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(COLUMN_USER_NAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_USER_EMAIL);

            // Check if the column indices are valid
            if (nameIndex >= 0 && emailIndex >= 0) {
                String name = cursor.getString(nameIndex);
                String userEmail = cursor.getString(emailIndex);

                // Create a new User object with the retrieved data
                user = new User(name, userEmail);
            }
        }
        cursor.close();
        db.close();

        return user;
    }
}
