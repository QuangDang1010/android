package vn.edu.csc.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import vn.edu.csc.dictionary.model.Word;

public class WordQuery {
    DBHelper dbHelper;
    final String TB_NAME = "WORD";
    final String ID_COL = "WORDID";
    final String WORD_COL = "WORD";

    public WordQuery(Context context){
        dbHelper = new DBHelper(context);
    }

    public Word searchWord(String keyword){
        SQLiteDatabase dbo = dbHelper.openDB();
        Word res = null;
        String sqlq = "SELECT *"
                + " FROM " + TB_NAME
                + " WHERE " + WORD_COL + " = ? "
                + " LIMIT 1" ;
        Cursor cur = dbo.rawQuery(sqlq, new String[]{keyword});
        if(cur.moveToNext()){
            int id = cur.getInt(0);
            String word = cur.getString(1);
            String lang = cur.getString(2);

            res = new Word(id,word,lang);
        }
        else res = new Word(-1, "Word not found", null);

        cur.close();
        dbHelper.closeDB(dbo);
        return res;
    }

    public ArrayList<String> autoComplete(String keyword){
        SQLiteDatabase dbo = dbHelper.openDB();
        ArrayList<String> res = new ArrayList<>();
        String sqlq = "SELECT " + WORD_COL
                + " FROM " + TB_NAME
                + " WHERE " + WORD_COL + " LIKE '"+  keyword + "%' "
                + " ORDER BY " + WORD_COL + " COLLATE NOCASE";
        Cursor cur = dbo.rawQuery(sqlq, null);
        while (cur.moveToNext()) res.add(cur.getString(0));

        cur.close();
        dbHelper.closeDB(dbo);
        return res;
    }

    public Word getWord(int wordID){
        SQLiteDatabase dbo = dbHelper.openDB();
        Word res = null;
        String sqlq = "SELECT *"
                + " FROM " + TB_NAME
                + " WHERE " + ID_COL + " = ?";
        Cursor cur = dbo.rawQuery(sqlq, new String[]{wordID+""});


        if(cur.moveToNext()){
            int id =  cur.getInt(0);
            String word = cur.getString(1);
            String lang = cur.getString(2);

            res = new Word(id, word, lang);
        }

        cur.close();
        dbHelper.closeDB(dbo);
        return res;
    }

    public ArrayList<Word> getFavoriteWords(){
        SQLiteDatabase dbo = dbHelper.openDB();
        ArrayList<Word> res = new ArrayList<>();
        String sqlq = "SELECT *"
                + " FROM "+TB_NAME
                + " WHERE " + ID_COL + " IN ( "
                + " SELECT " + FavoriteQuery.ID_FAVORITE
                + " FROM " + FavoriteQuery.TB_FAVORITE
                +")";
        Cursor cur = dbo.rawQuery(sqlq, null);

        while(cur.moveToNext()){
            int id =  cur.getInt(0);
            String word = cur.getString(1);
            String lang = cur.getString(2);

            res.add(new Word(id,word,lang));
        }

        dbHelper.closeDB(dbo);
        cur.close();
        return res;
    }

}
