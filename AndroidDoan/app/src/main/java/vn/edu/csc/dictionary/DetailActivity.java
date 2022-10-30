package vn.edu.csc.dictionary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import vn.edu.csc.dictionary.adapter.VPAdapter;
import vn.edu.csc.dictionary.db.ExampleQuery;
import vn.edu.csc.dictionary.db.FavoriteQuery;
import vn.edu.csc.dictionary.db.HistoryQuery;
import vn.edu.csc.dictionary.db.MeaningQuery;
import vn.edu.csc.dictionary.db.NoteQuery;
import vn.edu.csc.dictionary.db.WordQuery;
import vn.edu.csc.dictionary.model.DetailRow;
import vn.edu.csc.dictionary.model.Example;
import vn.edu.csc.dictionary.model.Meaning;
import vn.edu.csc.dictionary.model.Word;

public class DetailActivity extends AppCompatActivity {

    ViewPager2 vpPager;
    TabLayout tlTabs;
    FavoriteQuery favoriteQuery;

    Word word;
    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("DETAIL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vpPager = findViewById(R.id.vpPager);
        tlTabs = findViewById(R.id.tlTabs);
        favoriteQuery = new FavoriteQuery(this);
        Bundle extras = getIntent().getExtras();
        word = (Word) extras.getSerializable("word");

        if(word.getWordId() > 0) addToHistory(word.getWordId());

        ArrayList<DetailRow>[] data = new ArrayList[0];
        if(word.getLang() == null){
            data = new ArrayList[1];
            data[0] = setData(word, null);
        }else if(word.getLang().equals("vn")){
            data = new ArrayList[1];
            data[0] = setData(word, "en");
        }else if(word.getLang().equals("en")){
            data = new ArrayList[2];
            data[0] = setData(word ,"vn");
            data[1] = setData(word, "en");
        }

        String note = getNote();
        setAdapter(data);
    }

    private String getNote() {
        NoteQuery noteQuery = new NoteQuery(this);
        String res = noteQuery.getNote(word.getWordId());
        return res;
    }

    boolean addToHistory(int wordID){
        HistoryQuery historyQuery = new HistoryQuery(DetailActivity.this);
        historyQuery.deleteHistory(wordID);
        long res = historyQuery.insertHistory(wordID);

        return res > 0;
    }

    private void setAdapter(ArrayList<DetailRow>[] data) {
        VPAdapter adapter = new VPAdapter(DetailActivity.this, data, word.getWordId());
        vpPager.setAdapter(adapter);
        new TabLayoutMediator(tlTabs, vpPager,
                (tab, position) -> {
                    //tab.setText("OBJECT " + (position + 1));
                    String title = "";

                    if(word.getWordId() < 0) title = "NOT FOUND";
                    else if(word.getLang().equals("vn")){
                        switch (position){
                            case 0:
                                title = "Việt - Anh";
                                break;
                            case 1:
                                title = "Ghi Chú";
                                break;
                            default: title = "Unknown";
                        }
                    }else if(word.getLang().equals("en")){
                        switch (position){
                            case 0:
                                title = "Anh - Việt";
                                break;
                            case 1:
                                title = "Anh - Anh";
                                break;
                            case 2:
                                title = "Ghi Chú";
                                break;
                            default: title = "Unknown";
                        }
                    } else title = "Unknown";
                    tab.setText(title);
                }
        ).attach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    Menu optionMenu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(word.getWordId()<0) return true;

        optionMenu = menu;
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        checkFavorite();
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.icFavorite){
            if(isFavorite){
                isFavorite = !favoriteQuery.delete(word.getWordId());
                Toast.makeText(this, isFavorite?"action failed":"removed from favorite", Toast.LENGTH_SHORT).show();
                Log.d("fav", isFavorite?"action failed":"removed from favorite");
            }else{
                isFavorite = favoriteQuery.add(word.getWordId());
                Toast.makeText(this, isFavorite?"added to favorite":"action failed", Toast.LENGTH_SHORT).show();
                Log.d("fav", isFavorite?"added to favorite":"action failed");
            }

            updateFavoriteState();
        }
        return super.onOptionsItemSelected(item);
    }

    void updateFavoriteState(){
        int icon = isFavorite?R.drawable.ic_favorite_gold:R.drawable.ic_favorite;
        optionMenu.getItem(0).setIcon(icon);
    }

    void checkFavorite(){
        isFavorite = favoriteQuery.isFavorite(word.getWordId());
        updateFavoriteState();
    }

    public ArrayList<DetailRow> setData(Word word , String lang){
        ArrayList<DetailRow> lst = new ArrayList<>();

        lst.add(new DetailRow(word.getName(), DetailRow.WORD_TYPE));
        MeaningQuery meaningQuery = new MeaningQuery(this);
        ExampleQuery exampleQuery = new ExampleQuery(this);

        ArrayList<Meaning> meanings = new ArrayList<>();
        if(lang!=null) meanings =  meaningQuery.getWordMeaning(word.getWordId(), lang);
        for(int i=0; i<meanings.size(); i++){
            Meaning m = meanings.get(i);
            if(m.getClasses()!=null) lst.add(new DetailRow(m.getClasses(), DetailRow.CLASS_TYPE));
            else if(m.getPhrase()!=null)lst.add(new DetailRow(m.getPhrase(), DetailRow.PHRASE_TYPE));
            lst.add(new DetailRow(m.getMeaning(), DetailRow.MEANING_TYPE));

            ArrayList<Example> examples = exampleQuery.getMeaningExample(m.getMeaningID());
            for(int j=0; j < examples.size(); j++){
                Example e = examples.get(j);
                lst.add(new DetailRow(e.getExample(), DetailRow.EXAMPLE_TYPE));
                lst.add(new DetailRow(e.getExplain(), DetailRow.EXPLAIN_TYPE));
            }
        }

        return lst;
    }

}