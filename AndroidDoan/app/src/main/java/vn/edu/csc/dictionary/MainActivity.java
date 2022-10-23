package vn.edu.csc.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.csc.dictionary.adapter.WordAdapter;
import vn.edu.csc.dictionary.db.DBHelper;
import vn.edu.csc.dictionary.db.WordDBQuery;
import vn.edu.csc.dictionary.model.Word;

public class MainActivity extends AppCompatActivity implements WordAdapter.WordCallBack {
    RecyclerView rvListC;
    ArrayList<Word> lstword;
    WordAdapter wordAdapter;

    WordDBQuery wordDBQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("LOOOOOOOOOL", "main activity");
        wordDBQuery = new WordDBQuery(this);

        rvListC=findViewById(R.id.rvHistory);
        //
        LoadData();
        wordAdapter =new WordAdapter(lstword,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvListC.setAdapter(wordAdapter);
        rvListC.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClick(String name) {
        Intent i =new Intent(this, DetailActivity.class);
        i.putExtra("word",name);
        startActivity(i);
    }



    void LoadData(){
        lstword = wordDBQuery.getSampleData(20);
        /*lstword = new ArrayList<>();
        lstword.add(new Word(1,"asd", "wqd"));*/

    }

    //    @Override
//    public void onItemClick(String name) {
//
//    }
}