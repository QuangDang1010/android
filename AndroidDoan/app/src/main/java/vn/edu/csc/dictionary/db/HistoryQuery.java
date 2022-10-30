package vn.edu.csc.dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.csc.dictionary.model.History;

public class HistoryQuery {
    DBHelper dbHelper;
    final String TB_NAME = "HISTORY";
    static public final String ID_COL = "ID";
    static public final String WORDID_COL = "WORDID";

    public HistoryQuery(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<History> getAllHistory(String orderBy, boolean asc){
        ArrayList<History> res = new ArrayList<>();
        SQLiteDatabase dbo = dbHelper.openDB();
        if(orderBy == null) orderBy = ID_COL;
        String sqlq = "SELECT * FROM " + TB_NAME + " ORDER BY "+ orderBy;
        if(!asc) sqlq += " DESC";
        Cursor cur = dbo.rawQuery(sqlq, null);
        while(cur.moveToNext()){
            int id = cur.getInt(0);
            int word = cur.getInt(1);

            res.add(new History(id, word));
        }

        cur.close();
        dbHelper.closeDB(dbo);
        return res;
    }

    public long insertHistory(int wordID){
        SQLiteDatabase dbo = dbHelper.openDB();
        ContentValues cv = new ContentValues();
        cv.putNull(ID_COL);
        cv.put(WORDID_COL, wordID);
        long insertedID = dbo.insert(TB_NAME,null , cv);

        dbHelper.closeDB(dbo);
        return insertedID;
    }

    public int deleteHistory(int wordID){
        SQLiteDatabase dbo = dbHelper.openDB();
        int numberDeleted = dbo.delete(TB_NAME,WORDID_COL+" = ?", new String[]{wordID+""});

        dbHelper.closeDB(dbo);
        return numberDeleted;
    }

    public int clearHistory(){
        SQLiteDatabase dbo = dbHelper.openDB();
        int numberDeleted = dbo.delete(TB_NAME,null, null);

        dbHelper.closeDB(dbo);
        return numberDeleted;
    }

    public int getCount(){
        SQLiteDatabase dbo = dbHelper.openDB();
        String sqlq = "SELECT COUNT(*) FROM "+TB_NAME;
        Cursor cur = dbo.rawQuery(sqlq, null);
        int count = -1;
        if(cur.moveToNext())count = cur.getInt(0);

        cur.close();
        dbHelper.closeDB(dbo);
        return count;
    }
}
