package vn.edu.csc.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;

import java.util.ArrayList;

import vn.edu.csc.dictionary.adapter.WordAdapter;
import vn.edu.csc.dictionary.db.HistoryQuery;
import vn.edu.csc.dictionary.db.WordQuery;
import vn.edu.csc.dictionary.model.History;
import vn.edu.csc.dictionary.model.Word;

public class MainActivity extends AppCompatActivity implements WordAdapter.WordCallBack {
    RecyclerView rvListC;
    ArrayList<Word> lstword;
    AutoCompleteTextView etSearch;
    TextView tvMessage;
    Button btFav;

    WordAdapter historyAdapter;
    WordQuery wordQuery;
    ArrayList<String> hints;
    ArrayAdapter<String> hintAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordQuery = new WordQuery(this);

        rvListC=findViewById(R.id.rvHistory);
        etSearch = findViewById(R.id.etSearch);
        tvMessage = findViewById(R.id.tvMessage);
        btFav = findViewById(R.id.btFav);

        //history recyclerview
        lstword = new ArrayList<>();
        setHistoryAdapter();

        //autocomplete search
        setHintAdapter();
        setOnSearchChangeEvent();
        setOnSearchEnterEvent();
        setOnFavoriteEvent();

    }

    private void setOnFavoriteEvent() {
        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setOnSearchEnterEvent() {
        etSearch.setOnKeyListener(new View.OnKeyListener() { //pc keyboard enter
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    onSearch();
                    return true;
                }
                return false;
            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() { //phone keyboard enter
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_NEXT){
                    onSearch();
                    return true;
                }

                return false;
            }
        });
    }

    private void setHistoryAdapter() {
        historyAdapter = new WordAdapter(lstword,this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvListC.setAdapter(historyAdapter);
        rvListC.setLayoutManager(linearLayoutManager);
    }

    private void setOnSearchChangeEvent() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                ArrayList<String> newHints = wordQuery.autoComplete(str);
                hintAdapter.clear();
                hintAdapter.addAll(newHints);
                hintAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }

    private void setHintAdapter() {
        hints = new ArrayList<>();
        hintAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, hints);
        etSearch.setAdapter(hintAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadData();
    }

    void onSearch(){
        String keyword = etSearch.getText().toString();
        Word word = wordQuery.searchWord(keyword);
        Intent i =new Intent(MainActivity.this, DetailActivity.class);
        i.putExtra("word", word);
        startActivity(i);
    }

    @Override
    public void onItemClick(Word word) {
        Intent i =new Intent(this, DetailActivity.class);
        i.putExtra("word", word);
        startActivity(i);
    }

    void LoadData(){
        //lstword = wordQuery.getSampleData(50);
        HistoryQuery historyQuery = new HistoryQuery(MainActivity.this);
        ArrayList<History> history = historyQuery.getAllHistory(HistoryQuery.ID_COL, false);
        lstword = new ArrayList<>();
        for(int i=0; i < history.size(); i++){
            Word word = wordQuery.getWord(history.get(i).getWordID());
            lstword.add(word);
        }
        setHistoryAdapter();

        tvMessage.setText(lstword.size()==0? "Your search history will be saved here": "");
    }

}