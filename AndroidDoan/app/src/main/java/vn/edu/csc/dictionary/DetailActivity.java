package vn.edu.csc.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tvWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setTitle("DETAIL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String word = extras.getString("word", "no word");

        tvWord = findViewById(R.id.tvWord);
        tvWord.setText(word);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}