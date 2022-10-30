package vn.edu.csc.dictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import vn.edu.csc.dictionary.R;
import vn.edu.csc.dictionary.model.DetailRow;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailVH> {

    ArrayList<DetailRow> rows;

    public DetailAdapter(ArrayList<DetailRow> rows) {
        this.rows = rows;
    }

    @NonNull
    @Override
    public DetailVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        switch (viewType){
            case DetailRow.WORD_TYPE:
                layout = R.layout.detail_row_word;
                break;
            case DetailRow.CLASS_TYPE:
                layout = R.layout.detail_row_class;
                break;
            case DetailRow.MEANING_TYPE:
                layout = R.layout.detail_row_meaning;
                break;
            case DetailRow.EXAMPLE_TYPE:
                layout = R.layout.detail_row_example;
                break;
            default:
                layout = R.layout.detail_row_text;
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new DetailVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailVH holder, int position) {
        DetailRow row = rows.get(position);
        holder.tvContent.setText(row.getContent());
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    @Override
    public int getItemViewType(int position) {
        DetailRow row = rows.get(position);
        return row.getType();
    }

    class DetailVH extends RecyclerView.ViewHolder{

        TextView tvContent;

        public DetailVH(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}
