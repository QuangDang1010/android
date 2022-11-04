package vn.edu.csc.dictionary.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.csc.dictionary.R;
import vn.edu.csc.dictionary.model.Word;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.favoriteVH> {

    ArrayList<Word> favoriteList;
    EventListener eventListener;
    boolean selectAll = false;
    boolean unSelectAll = false;

    public FavoriteAdapter(ArrayList<Word> favoriteList, EventListener eventListener) {
        this.favoriteList = favoriteList;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public favoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_row,parent,false);
        return new favoriteVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull favoriteVH holder, int position) {
        Word word = favoriteList.get(position);
        holder.tvWord.setText(word.getName());

        if(selectAll) holder.cbSelect.setChecked(true);
        if(unSelectAll) holder.cbSelect.setChecked(false);
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox)view;
                eventListener.onSelectWord(word.getWordId(), cb.isChecked());
            }
        });
        holder.tvWord.setOnClickListener(view -> eventListener.onClick(word));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public void selectAll(boolean select){
        selectAll = select;
        unSelectAll = !select;
        notifyDataSetChanged();
    }

    class favoriteVH extends RecyclerView.ViewHolder{

        CheckBox cbSelect;
        TextView tvWord;

        public favoriteVH(@NonNull View itemView) {
            super(itemView);

            cbSelect = itemView.findViewById(R.id.cbSelect);
            tvWord = itemView.findViewById(R.id.tvWord);
        }
    }

    public interface EventListener{
        void onSelectWord(int wordID, boolean select);
        void onClick(Word word);
    }
}
