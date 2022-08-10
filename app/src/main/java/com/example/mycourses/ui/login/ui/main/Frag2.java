package com.example.mycourses.ui.login.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mycourses.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class Frag2 extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("ChangeOver");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1,container,false);
        final Button save = v.findViewById(R.id.save_settings);


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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText s = (EditText)v.findViewById(R.id.ssid);
                myRef.child("wifiName").setValue(s.getText().toString());
                s = (EditText)v.findViewById(R.id.ssid_password);
                myRef.child("wifiPassword").setValue(s.getText().toString());

                int val = ctts( ((Spinner) v.findViewById(R.id.gen_up_time)).getSelectedItem()+"","h" );
                myRef.child("genTimerLim").setValue( ctts( ((Spinner) v.findViewById(R.id.inverter_time)).getSelectedItem()+"","h" ) );
                myRef.child("genUpTimeLim").setValue( ctts( ((Spinner) v.findViewById(R.id.gen_up_time)).getSelectedItem()+"","h" ) );
                myRef.child("crankTime").setValue( ctts( ((Spinner) v.findViewById(R.id.gen_crank_time)).getSelectedItem()+"","s" ) );

            }
        });

        return v;
    }

    public  int ctts(String s,String t){

        if(t == "s"){
            s =s.replace("Seconds","");
            s =s.replace("Second","");
            s =s.replace(" ","");
            int val = Integer.parseInt(s);
            return val;
        }else{
            s =s.replace("Hours","");
            s =s.replace("Hour","");
            s =s.replace(" ","");
            int val = Integer.parseInt(s);
            return val*3600;
        }

    }

    public void setInput(EditText view, String text){
        view.setText(text);
    }

    public void setSpinner(Spinner view, int pos){
        view.setSelection(pos);
    }

}
