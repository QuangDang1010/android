package vn.edu.csc.dictionary.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;

import vn.edu.csc.dictionary.R;

public class AboutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    TextView tvTitle;
    TextView tvContent;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTitle = view.findViewById(R.id.tvTitle);
        tvContent = view.findViewById(R.id.tvContent);

        try{
            InputStream is = getResources().openRawResource(R.raw.info);
            byte []b = new byte[is.available()];
            is.read(b);
            tvContent.setText(new String(b));

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "Error reading resource", Toast.LENGTH_SHORT).show();
        }
    }
}