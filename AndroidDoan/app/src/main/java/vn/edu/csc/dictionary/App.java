package vn.edu.csc.dictionary;

import android.app.Application;

import vn.edu.csc.dictionary.db.DBHelper;


public class App extends Application {
    DBHelper dbHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DBHelper(this);
        dbHelper.createDB();
        //dbHelper.copyDB();
    }
}
