package com.example.photos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.photos.model.Photo;

import java.util.ArrayList;

public class BDSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database";
    private static final String TABLE_NAME = "photos";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";
    private static final String[] COLUMNS = {ID, TITLE, DESCRIPTION, URL};

    public BDSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE photos ("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT,"+
                "description TEXT,"+
                "url TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS photos");
        this.onCreate(db);
    }

    public int addPhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, photo.getTitle());
        values.put(DESCRIPTION, photo.getDescription());
        values.put(URL, photo.getUrl());
        int id = (int) db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public Photo getPhoto(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                null, // columns filter ( null to return all )
                " id = ?", // filters
                new String[] { String.valueOf(id) }, // filters parameters
                null, // group by
                null, // having
                null, // order by
                null); // limit

        if (cursor == null) {
            return null;
        } else {
            cursor.moveToFirst();
            return cursorToPhoto(cursor);
        }
    }

    private Photo cursorToPhoto(Cursor cursor) {
        Photo photo = new Photo();
        photo.setId(Integer.parseInt(cursor.getString(0)));
        photo.setTitle(cursor.getString(1));
        photo.setDescription(cursor.getString(2));
        photo.setUrl(cursor.getString(3));
        return photo;
    }

    public ArrayList<Photo> getAllPhotos() {
        ArrayList<Photo> photoList = new ArrayList<Photo>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Photo photo = cursorToPhoto(cursor);
                photoList.add(photo);
            } while (cursor.moveToNext());
        }
        return photoList;
    }

    public int updatePhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE, photo.getTitle());
        values.put(DESCRIPTION, photo.getDescription());
        values.put(URL, photo.getUrl());
        int i = db.update(TABLE_NAME,
                values,
                ID + " = ?",
                new String[] { String.valueOf(photo.getId()) });
        db.close();
        return i;
    }

    public int deletePhoto(Photo photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME,
                ID+" = ?",
                new String[] { String.valueOf(photo.getId()) });
        db.close();
        return i;
    }

}
