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
        }catch (Exception e){
            Toast.makeText(context, "Copy DB failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.e("DBFAIL", e.getMessage() );
        }
     }

    public SQLiteDatabase openDB(){
        return context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
    }

    public void closeDB(SQLiteDatabase db){
        db.close();
    }

}
