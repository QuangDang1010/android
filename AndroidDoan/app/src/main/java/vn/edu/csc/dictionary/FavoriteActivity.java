package vn.edu.csc.dictionary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.csc.dictionary.adapter.FavoriteAdapter;
import vn.edu.csc.dictionary.db.FavoriteQuery;
import vn.edu.csc.dictionary.db.WordQuery;
import vn.edu.csc.dictionary.model.Word;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.EventListener {

    RecyclerView rvFavorite;
    Button btnSelect, btnDelete;
    TextView tvMessage;

    FavoriteAdapter favoriteAdapter;
    WordQuery wordQuery;
    FavoriteQuery favoriteQuery;
    ArrayList<Word> words = new ArrayList<>();
    ArrayList<Integer> selectedWords = new ArrayList<>();
    String orderBy;
    boolean asc;


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
        orderBy = FavoriteQuery.ID_FAVORITE;
        asc = false;

        btnDelete.setOnClickListener(view -> delete());
        btnSelect.setOnClickListener(view -> selectAll());
        setAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.icSort){
            sortDialog();
        }
        
        return super.onOptionsItemSelected(item);
    }

    void sortDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SORT");

        View view = getLayoutInflater().inflate(R.layout.sort_dialog, null);
        Spinner spOrder = view.findViewById(R.id.spOrder);
        RadioGroup rgAsc = view.findViewById(R.id.rgAsc);

        ArrayList<String> orderLst = new ArrayList<>();
        orderLst.add("Time");
        orderLst.add("Alphabetical");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, orderLst);
        spOrder.setAdapter(adapter);

        dialog.setView(view);
        dialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String order =  spOrder.getSelectedItem().toString();
                if(order.equals(orderLst.get(0))) orderBy = FavoriteQuery.ID_FAVORITE;
                else orderBy = WordQuery.WORD;
                asc = rgAsc.getCheckedRadioButtonId() == R.id.rbAsc;
                loadData();

                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }

    private void setAdapter() {
        favoriteAdapter = new FavoriteAdapter(words, this);
        rvFavorite.setAdapter(favoriteAdapter);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    void loadData(){
        ArrayList<Word> newWords = getData();
        words.clear();
        words.addAll(newWords);
        if(words.size() == 0) tvMessage.setText("Your favorite words will be saved here");
        else tvMessage.setText("");
        favoriteAdapter.notifyDataSetChanged();
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
        ArrayList<Word> res = wordQuery.getFavoriteWords(orderBy, asc);
        return res;
    }

    void updateSelectState(){
        btnSelect.setText(selectedWords.size() < words.size()? "Select All": "Unselect ALL");
        btnDelete.setBackgroundColor(Color.parseColor(selectedWords.size()>0?"#D60D0D":"#808080"));
    }

    void delete(){
        if(selectedWords.size() == 0) return;

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REMOVE");
        dialog.setMessage("Remove "+selectedWords.size()+" from Favorite?");
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int deletedCount = 0;
                for(int j=0;j<selectedWords.size();j++){
                    boolean deleted = favoriteQuery.delete(selectedWords.get(j));
                    if(deleted) deletedCount++;
                }
                Toast.makeText(FavoriteActivity.this, "removed "+deletedCount+" items from favorite", Toast.LENGTH_SHORT).show();
                loadData();
                favoriteAdapter.selectAll(false);
                dialogInterface.dismiss();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.show();

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
}