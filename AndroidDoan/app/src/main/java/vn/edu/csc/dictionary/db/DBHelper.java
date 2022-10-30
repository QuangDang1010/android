package vn.edu.csc.dictionary.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper {

    public static final String DB_NAME = "mDictionary.sqlite";
    public static final Integer DB_VERSION = 1;
    String DB_PATH;
    Context context;

    public DBHelper(Context context) {
        //super(context, DB_NAME, null, DB_VERSION);
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.context = context;
    }

     public void copyDB() {
        try{
            InputStream is = context.getAssets().open(DB_NAME);
            OutputStream os = new FileOutputStream(DB_PATH+DB_NAME);

            byte []buffer  = new byte[1024];
            while(is.read(buffer) > 0)  {
                os.write(buffer);
            }

            os.flush();
            os.close();
            is.close();
            Log.d("copyDB", "copyDB successfully");
        }catch (Exception e){
            Toast.makeText(context, "CopyDB failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
     }

    public SQLiteDatabase openDB(){
        //return context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public boolean checkDB(){
        SQLiteDatabase db = null;
        try{
            db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLException e) {
            //e.printStackTrace();
            return false;
        }
        db.close();
        return true;
    }

    public void createDB(){
        if(!checkDB()){
            copyDB();
            //context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        }

        Log.d("createDB", "" + checkDB());
    }

    public void closeDB(SQLiteDatabase db){
        db.close();
    }

}
