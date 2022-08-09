package com.example.mycourses.ui.login.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycourses.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Frag2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1,container,false);

        String[] secs = new String[]{"0","1 Second", "2 Seconds", "3 Seconds","4 Seconds", "5 Seconds", "6 Seconds","7 Seconds", "8 Seconds", "9 Seconds"};
        String[] hrs = new String[]{"0","1 Hour", "2 Hours", "3 Hours","4 Hours", "5 Hours", "6 Hours","7 Hours", "8 Hours", "9 Hours"};

        Spinner spinner = (Spinner) v.findViewById(R.id.gen_crank_time);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, secs);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner = (Spinner) v.findViewById(R.id.gen_up_time);
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, hrs);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner = (Spinner) v.findViewById(R.id.inverter_time);
        adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, hrs);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        return v;
    }

    public void setInput(EditText view, String text){
        view.setText(text);
    }

    public void setSpinner(Spinner view, int pos){
        view.setSelection(pos);
    }

}
