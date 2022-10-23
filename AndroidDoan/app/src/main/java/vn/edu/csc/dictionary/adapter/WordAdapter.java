package vn.edu.csc.dictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.csc.dictionary.R;
import vn.edu.csc.dictionary.model.Word;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
ArrayList<Word>lstWord;
Context context;
WordCallBack wordCallBack;

    public WordAdapter(ArrayList<Word> lstWord, WordCallBack wordCallBack) {
        this.lstWord = lstWord;
        this.wordCallBack=wordCallBack;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        //nap lay out cho view
        View view=inflater.inflate(R.layout.layout_history,parent,false);
        WordViewHolder wordViewHolder=new WordViewHolder(view);
        return wordViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word item=lstWord.get(position); // lay tung tu cua du lieu
        //gan vao item cua view
        holder.tvWord.setText(item.getName());
        holder.tvMeaning.setText(item.getLang());
        //lay su kien
        holder.itemView.setOnClickListener(view -> wordCallBack.onItemClick(item.getName()));
        holder.itemView.setOnClickListener(view -> wordCallBack.onItemClick(item.getLang()));

    }

    @Override
    public int getItemCount() {return lstWord.size();}

    class WordViewHolder extends RecyclerView.ViewHolder{
            TextView tvWord;
            TextView tvMeaning;
        public WordViewHolder(@NonNull View itemview){
            super(itemview);
            tvWord=itemview.findViewById(R.id.tvWord);
            tvMeaning=itemview.findViewById(R.id.tvMeaning);
        }

    }

    public  interface WordCallBack{
        void onItemClick(String name);
    }

}
//

//
