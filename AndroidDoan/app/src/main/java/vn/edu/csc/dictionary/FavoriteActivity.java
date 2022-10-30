package vn.edu.csc.dictionary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.csc.dictionary.adapter.FavoriteAdapter;
import vn.edu.csc.dictionary.db.FavoriteQuery;
import vn.edu.csc.dictionary.db.WordQuery;
import vn.edu.csc.dictionary.model.Word;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.EventListener {

    RecyclerView rvFavorite;
    ArrayList<Word> words;
    ArrayList<Integer> selectedWords;
    Button btnSelect, btnDelete;
    TextView tvMessage;

    FavoriteAdapter favoriteAdapter;
    WordQuery wordQuery;
    FavoriteQuery favoriteQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        getSupportActionBar().setTitle("FAVORITE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvFavorite = findViewById(R.id.rvFavorite);
        btnDelete = findViewById(R.id.btnDelete);
        btnSelect = findViewById(R.id.btnSelect);
        tvMessage = findViewById(R.id.tvMessage);

        wordQuery = new WordQuery(this);
        favoriteQuery = new FavoriteQuery(this);

        selectedWords = new ArrayList<>();

        btnDelete.setOnClickListener(view -> delete());
        btnSelect.setOnClickListener(view -> selectAll());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    void loadData(){
        words = getData();
        if(words.size() == 0) tvMessage.setText("Your favorite words will be saved here");
        else tvMessage.setText("");
        favoriteAdapter = new FavoriteAdapter(words, this);
        rvFavorite.setAdapter(favoriteAdapter);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void selectAll() {
        boolean select = selectedWords.size() < words.size();
        favoriteAdapter.selectAll(select);
        if(select){
            selectedWords = new ArrayList<>();
            for(int i=0; i< words.size(); i++) selectedWords.add(words.get(i).getWordId());
        }
        else selectedWords = new ArrayList<>();
        updateSelectState();
    }

    ArrayList<Word> getData(){
        ArrayList<Word> res = wordQuery.getFavoriteWords();
        return res;
    }

    void updateSelectState(){
        btnSelect.setText(selectedWords.size() < words.size()? "Select All": "Unselect ALL");
        btnDelete.setBackgroundColor(Color.parseColor(selectedWords.size()>0?"#D60D0D":"#808080"));
    }

    @Override
    public void onSelectWord(int wordID, boolean select) {
        if(select){
            selectedWords.add(wordID);
        }else{
            int index = -1;
            for(int i=0; i<selectedWords.size(); i++){
                if(selectedWords.get(i) == wordID){
                    index = i;
                    break;
                }
            }
            if(index != -1) selectedWords.remove(index);
        }

        updateSelectState();
    }

    @Override
    public void onClick(Word word) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }

    void delete(){
        if(selectedWords.size() == 0) return;

        //alert dialog here

        int deletedCount = 0;
        for(int i=0;i<selectedWords.size();i++){
            boolean deleted = favoriteQuery.delete(selectedWords.get(i));
            if(deleted) deletedCount++;
        }

        Toast.makeText(this, "deleted "+deletedCount+" items", Toast.LENGTH_SHORT).show();
        Log.d("delete", "deleted "+deletedCount+" items");
    }
}