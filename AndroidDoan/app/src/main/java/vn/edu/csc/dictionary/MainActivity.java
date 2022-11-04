package vn.edu.csc.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabItem;

import java.util.ArrayList;

import vn.edu.csc.dictionary.adapter.WordAdapter;
import vn.edu.csc.dictionary.db.HistoryQuery;
import vn.edu.csc.dictionary.db.WordQuery;
import vn.edu.csc.dictionary.fragment.AboutFragment;
import vn.edu.csc.dictionary.fragment.HomeFragment;
import vn.edu.csc.dictionary.model.History;
import vn.edu.csc.dictionary.model.Word;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView navBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBottom = findViewById(R.id.navBottom);

        setNavigationEvent();
        navBottom.setSelectedItemId(R.id.itemHome);
    }

    void setNavigationEvent(){
        navBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment target;
                switch (item.getItemId()){
                    case R.id.itemAbout:
                        target = new AboutFragment();
                        break;
                    default:
                        target = new HomeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, target).commit();
                return true;
            }
        });
    }

}