package vn.edu.csc.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.edu.csc.dictionary.model.Meaning;

public class MeaningQuery {
    DBHelper dbHelper;

    final String TB_NAME = "MEANING";
    final String ID_COL = "MEANINGID";
    final String WORDID_COL = "WORDID";
    final String PHRASE_COL = "PHRASE";
    final String LANG_COL = "LANG";
    final String CLASS_COL = "CLASS";

    public MeaningQuery(Context context){
        dbHelper = new DBHelper(context);
    }

    public Meaning getMeaning(int meaningID){
        return null;
    }

    public ArrayList<Meaning> getWordMeaning(int wordID, String lang){
        ArrayList<Meaning> res = new ArrayList<>();
        SQLiteDatabase dbo = dbHelper.openDB();
        String sqlq = "SELECT *"
                + " FROM " + TB_NAME
                + " WHERE " + WORDID_COL + " = ? AND " + LANG_COL + " = ? " ;
        Cursor cur = dbo.rawQuery(sqlq, new String[]{wordID+"", lang});
        while(cur.moveToNext()){
            String p  = null,c = null;

            int mID = cur.getInt(0);
            int wID = cur.getInt(1);
            String m = cur.getString(2);
            if (!cur.isNull(3)) p = cur.getString(3);
            String l = cur.getString(4);
            if(!cur.isNull(5)) c = cur.getString(5);

            res.add(new Meaning(mID, wID, m, p , l, c));
        }

        dbHelper.closeDB(dbo);
        return res;
    }
}
