package vn.edu.csc.dictionary.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import vn.edu.csc.dictionary.DetailActivity;
import vn.edu.csc.dictionary.FavoriteActivity;
import vn.edu.csc.dictionary.MainActivity;
import vn.edu.csc.dictionary.R;
import vn.edu.csc.dictionary.adapter.WordAdapter;
import vn.edu.csc.dictionary.db.HistoryQuery;
import vn.edu.csc.dictionary.db.WordQuery;
import vn.edu.csc.dictionary.model.History;
import vn.edu.csc.dictionary.model.Word;

public class HomeFragment extends Fragment implements WordAdapter.WordCallBack{


    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    RecyclerView rvListC;
    ArrayList<Word> lstword;
    AutoCompleteTextView etSearch;
    TextView tvMessage;
    Button btFav;
    BottomNavigationView bottomNav;
    TextView tvClear;

    WordAdapter historyAdapter;
    WordQuery wordQuery;
    ArrayList<String> hints;
    ArrayAdapter<String> hintAdapter;
    HistoryQuery historyQuery;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wordQuery = new WordQuery(getContext());

        rvListC= view.findViewById(R.id.rvHistory);
        etSearch = view.findViewById(R.id.etSearch);
        tvMessage = view.findViewById(R.id.tvMessage);
        btFav = view.findViewById(R.id.btFav);
        bottomNav = view.findViewById(R.id.navBottom);
        tvClear = view.findViewById(R.id.tvClear);

        //history recyclerview
        historyQuery = new HistoryQuery(context);
        lstword = new ArrayList<>();
        setHistoryAdapter();

        //autocomplete search
        setHintAdapter();
        setOnSearchChangeEvent();
        setOnSearchEnterEvent();
        setOnFavoriteEvent();
        setOnClearEvent();
    }

    void setOnClearEvent(){
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Clear History");
                dialog.setMessage("Remove "+lstword.size()+" items from Search history?");
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int deleted = historyQuery.clearHistory();
                        Toast.makeText(context, deleted+" items removed", Toast.LENGTH_SHORT).show();
                        LoadData();
                        dialogInterface.dismiss();
                    }
                });

                dialog.create();
                dialog.show();
            }
        });
    }

    private void setOnFavoriteEvent() {
        btFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FavoriteActivity.class);
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
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
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
        hintAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, hints);
        etSearch.setAdapter(hintAdapter);
    }

    void LoadData(){
        ArrayList<History> history = historyQuery.getAllHistory(HistoryQuery.ID_COL, false);
        lstword.clear();
        for(int i=0; i < history.size(); i++){
            Word word = wordQuery.getWord(history.get(i).getWordID());
            lstword.add(word);
        }
        historyAdapter.notifyDataSetChanged();

        tvMessage.setText(lstword.size()==0? "Your search history will be saved here": "");
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }

    void onSearch(){
        String keyword = etSearch.getText().toString();
        Word word = wordQuery.searchWord(keyword);
        Intent i =new Intent(context, DetailActivity.class);
        i.putExtra("word", word);
        startActivity(i);
        etSearch.setText("");
    }

    @Override
    public void onItemClick(Word word) {
        Intent i =new Intent(context, DetailActivity.class);
        i.putExtra("word", word);
        startActivity(i);
    }

    @Override
    public void onItemDelete(Word word) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Remove from history");
        dialog.setMessage("Remove '"+word.getName()+"' from Search history?");
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int deleted = historyQuery.deleteHistory(word.getWordId());
                if (deleted > 0){
                    Toast.makeText(context, "Removed successfully", Toast.LENGTH_SHORT).show();
                    LoadData();
                }else Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                dialogInterface.dismiss();
            }
        });

        dialog.create();
        dialog.show();
    }

}