package vn.edu.csc.dictionary.adapter;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import vn.edu.csc.dictionary.fragment.DetailFragment;
import vn.edu.csc.dictionary.fragment.NoteFragment;
import vn.edu.csc.dictionary.model.DetailRow;

public class VPAdapter extends FragmentStateAdapter {

    ArrayList<DetailRow> []fragmentArg;
    int wordID;

    public VPAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<DetailRow> []fragmentArg, int wordID) {
        super(fragmentActivity);
        this.fragmentArg = fragmentArg;
        this.wordID = wordID;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //NOTE
        if(position >= fragmentArg.length)
        {
            Fragment noteFragment = new NoteFragment();
            Bundle nbundle = new Bundle();
            nbundle.putInt("wordID", wordID);
            noteFragment.setArguments(nbundle);
            return noteFragment;
        }

        //WORD DETAIL
        Fragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        ArrayList<DetailRow> data = fragmentArg[position];
        bundle.putSerializable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        int count = fragmentArg.length;
        if(wordID > 0) count++; // extra tab for note
        return count;
    }
}
