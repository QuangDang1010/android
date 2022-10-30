package vn.edu.csc.dictionary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import vn.edu.csc.dictionary.model.Example;

public class ExampleQuery {
    DBHelper dbHelper;
    final String TB_NAME = "EXAMPLE";
    final String MEANINGID_COL = "MEANINGID";

    public ExampleQuery(Context context){
        dbHelper = new DBHelper(context);
    }

    public ArrayList<Example> getMeaningExample(int meaningID){
        ArrayList<Example> res = new ArrayList<>();
        SQLiteDatabase dbo = dbHelper.openDB();
        String sqlq = "SELECT *"
                + " FROM " + TB_NAME
                + " WHERE "+ MEANINGID_COL + " = ?";
        Cursor cur = dbo.rawQuery(sqlq, new String[]{meaningID+""});
        while(cur.moveToNext()){
            String example = cur.getString(2);
            String explain = cur.getString(3);

            res.add(new Example(example, explain));
        }

        cur.close();
        dbHelper.closeDB(dbo);
        return res;
    }
}
