package vn.edu.csc.dictionary.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.edu.csc.dictionary.R;
import vn.edu.csc.dictionary.db.NoteQuery;

public class NoteFragment extends Fragment {

    int wordID;
    String note;
    NoteQuery noteQuery;
    boolean noteExisted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            wordID = getArguments().getInt("wordID");
        }
        noteQuery = new NoteQuery(getContext());
        note = noteQuery.getNote(wordID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    EditText etNote;
    Button btnSave;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etNote = view.findViewById(R.id.etNote);
        btnSave = view.findViewById(R.id.btnSave);

        noteQuery = new NoteQuery(getContext());
        if(note != null) noteExisted = true;
        etNote.setText(note);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNote = etNote.getText().toString();
                if(noteExisted) updateNote(newNote);
                else addNote(newNote);
            }
        });
    }

    void updateNote(String newNote){
        boolean updated = true;
        if(! newNote.equals(note))
            updated = noteQuery.updateNote(wordID, newNote);
        Toast.makeText(getContext(), updated?"Note updated":"failed", Toast.LENGTH_SHORT).show();
    }

    void addNote(String newNote) {
        if(noteExisted) return;
        boolean added =  noteQuery.addNote(wordID, newNote);
        Toast.makeText(getContext(), added?"Note added":"failed", Toast.LENGTH_SHORT).show();
    }
}