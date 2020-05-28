package com.assign.tarang.inventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    //initalize variables and database name
    public static final String DATABASE_NAME = "Inventory.db";
    public static final String TABLE_NAME = "Inventory_DATA";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "LONGITUDE";
    public static final String COL_3 = "LATITUDE";

    public static final String COL_4 = "DATETIME";
    public static final String COL_5 = "INVOICE_NUMBER";
    public static final String COL_6 = "QUALITY";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    //passing query
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(USERNAME TEXT,LONGITUDE TEXT,LATITUDE TEXT,DATATIME TEXT,INVOICE_NUMBER TEXT,QUALITY TEXT)");

    }

    //passing query for new line
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    //getting all Data
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    //updating data
    public boolean updateData(String name, String longitude, String latitude, String date_time,String inv_Number,String quality) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, name);
        contentValues.put(COL_2, longitude);
        contentValues.put(COL_3, latitude);
        contentValues.put(COL_4, date_time);
        contentValues.put(COL_5, inv_Number);
        contentValues.put(COL_6, quality);
        db.update(TABLE_NAME, contentValues, "name = ?", new String[]{name});
        return true;
    }

    //delete data
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "name = ?", new String[]{id});
    }
}
