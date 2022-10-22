package vn.edu.csc.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements WordAdapter.WordCallBack {
RecyclerView rvListC;
ArrayList<Word> lstword;
WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        lstword=new ArrayList<>();
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add((new Word("02","Fuck","Con me may")));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add((new Word("02","Fuck","Con me may")));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
        lstword.add(new Word("01","Love","Tinh Yeu"));
    }

    //    @Override
//    public void onItemClick(String name) {
//
//    }
}