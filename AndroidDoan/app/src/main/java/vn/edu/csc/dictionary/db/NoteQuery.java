package vn.edu.csc.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NoteQuery {
    DBHelper dbHelper;

    final String TB_NAME = "NOTE";
    final String WORDID_COL = "WORDID";
    final String NOTE_COL = "NOTE";

    public NoteQuery(Context context){
        dbHelper = new DBHelper(context);
    }

    public String getNote(int wordID){
        String res = null;
        SQLiteDatabase dbo = dbHelper.openDB();
        String sqlq = "SELECT " + NOTE_COL
                + " FROM " + TB_NAME
                + " WHERE " + WORDID_COL + "=?";
        Cursor cur = dbo.rawQuery(sqlq, new String[]{wordID+""});
        if(cur.moveToFirst()) res = cur.getString(0);

        cur.close();
        dbHelper.closeDB(dbo);
        return res;
    }

    public boolean addNote(int wordID, String note){
        SQLiteDatabase dbo = dbHelper.openDB();
        ContentValues cv = new ContentValues();
        cv.put(WORDID_COL, wordID);
        cv.put(NOTE_COL, note);
        long added = dbo.insert(TB_NAME, null, cv);

        dbHelper.closeDB(dbo);
        return added > 0;
    }

    public boolean updateNote(int wordID, String note){
        SQLiteDatabase dbo = dbHelper.openDB();
        ContentValues cv = new ContentValues();
        cv.put(NOTE_COL, note);
        int updated = dbo.update(TB_NAME,cv, WORDID_COL+" = ?", new String[]{wordID+""});

        dbHelper.closeDB(dbo);
        return updated > 0;
    }

    public boolean deleteNote(int wordID){
        SQLiteDatabase dbo = dbHelper.openDB();
        int deleted =  dbo.delete(TB_NAME, WORDID_COL+" = ?", new String[]{wordID+""});

        dbHelper.closeDB(dbo);
        return deleted > 0;
    }

}
