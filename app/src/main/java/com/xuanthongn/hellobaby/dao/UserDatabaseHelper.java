package com.xuanthongn.hellobaby.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xuanthongn.hellobaby.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "users.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROLE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    // Phương thức thêm một user mới vào cơ sở dữ liệu
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ROLE, user.getRole());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Phương thức lấy danh sách tất cả các user từ cơ sở dữ liệu
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
                int roleIndex = cursor.getColumnIndex(COLUMN_ROLE);

                if (idIndex != -1) {
                    user.setId(cursor.getInt(idIndex));
                }
                if (emailIndex != -1) {
                    user.setEmail(cursor.getString(emailIndex));
                }
                if (passwordIndex != -1) {
                    user.setPassword(cursor.getString(passwordIndex));
                }
                if (roleIndex != -1) {
                    user.setRole(cursor.getString(roleIndex));
                }

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // Phương thức cập nhật thông tin của một user trong cơ sở dữ liệu
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ROLE, user.getRole());
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }

    // Phương thức xóa một user khỏi cơ sở dữ liệu
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        db.close();
    }
}
