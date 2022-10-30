package vn.edu.csc.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FavoriteQuery {
    DBHelper dbHelper;

    final String TB_NAME = "FAVORITE";
    final String WORDID_COL = "WORDID";
    public static final String TB_FAVORITE = "FAVORITE";
    public static final String ID_FAVORITE = "WORDID";

    public FavoriteQuery(Context context){
        dbHelper = new DBHelper(context);
    }

    public boolean isFavorite(int wordID){
        SQLiteDatabase dbo = dbHelper.openDB();
        String sqlq = "SELECT * FROM "+TB_NAME + " WHERE " + WORDID_COL +" = ?";
        Cursor cursor = dbo.rawQuery(sqlq, new String[]{wordID+""});
        boolean favorite = cursor.moveToFirst();

        cursor.close();
        dbHelper.closeDB(dbo);
        return favorite;
    }

    public boolean delete(int wordID){
        SQLiteDatabase dbo = dbHelper.openDB();
        int deleted = dbo.delete(TB_NAME,WORDID_COL+" = ?", new String[]{wordID+""});

        dbHelper.closeDB(dbo);
        return deleted > 0;
    }

    public boolean add(int wordID){
        SQLiteDatabase dbo = dbHelper.openDB();
        ContentValues cv = new ContentValues();
        cv.put(WORDID_COL, wordID);
        long added = dbo.insert(TB_NAME,null, cv);

        dbHelper.closeDB(dbo);
        return added > 0;
    }

}
