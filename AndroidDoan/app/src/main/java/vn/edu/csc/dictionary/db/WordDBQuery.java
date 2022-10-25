package vn.edu.csc.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.edu.csc.dictionary.model.Word;

public class WordDBQuery {
    SQLiteDatabase dbo;
    DBHelper dbHelper;
    final String TB_NAME = "WORD";
    final String ID_COLUMN = "WORDID";
    final String WORD_COLUMN = "WORD";

    public WordDBQuery(Context context){
        dbHelper = new DBHelper(context);
        dbo = dbHelper.openDB();
    }

    //sample
    public int searchWord(String keyword){
        int res = -1;
        String sqlq = "SELECT " + ID_COLUMN
                + " FROM " + TB_NAME
                + " WHERE " + WORD_COLUMN + " LIKE '?%'"
                + " LIMIT 1";
        Cursor cur = dbo.rawQuery(sqlq, new String[]{keyword});
        if(cur.moveToNext()) res = cur.getInt(0);

        return res;

    }

    //missing meaning, example
    public Word getWordDetail(int wordID){
        Word res = null;
        String sqlq = "SELECT " + WORD_COLUMN
                + " FROM " + TB_NAME
                + " WHERE " + ID_COLUMN + " = ?";
        Cursor cur = dbo.rawQuery(sqlq, new String[]{wordID+""});

        if(cur.moveToNext()){
            int id =  cur.getInt(0);
            String word = cur.getString(1);
            String lang = cur.getString(2);

            res = new Word(id, word, lang);
        }

        return res;
    }

    public ArrayList<Word> getSampleData(int n){
        ArrayList<Word> res = new ArrayList<>();
        n = Math.max(n, 1);
        String sqlq = "SELECT * FROM "+TB_NAME + " LIMIT " + n;
        Cursor cur = dbo.rawQuery(sqlq, null);

        while(cur.moveToNext()){
            int id =  cur.getInt(0);
            String word = cur.getString(1);
            String lang = cur.getString(2);

            res.add(new Word(id,word,lang));
        }

        return res;
    }

}
