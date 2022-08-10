package com.example.mycourses.ui.login.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mycourses.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Frag1 extends Fragment {
    View v;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("ChangeOver");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab2,container,false);

        CompoundButton.OnCheckedChangeListener checks = (new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChanged(buttonView.getTag().toString(),isChecked );
            }
        });

        ((Switch) v.findViewById(R.id.system_switch)).setOnCheckedChangeListener(checks);
        ((Switch) v.findViewById(R.id.mains_switch)).setOnCheckedChangeListener(checks);
        ((Switch) v.findViewById(R.id.keystate_switch)).setOnCheckedChangeListener(checks);
        ((Switch) v.findViewById(R.id.gen_switch)).setOnCheckedChangeListener(checks);

        return v;
    }

    public void checkChanged(String key,boolean s){
//        Log.d("key; ", key +"");
//        Log.d("switch; ", s.isChecked() +"");
        myRef.child(key).setValue(s);
        if(key.equals("offsystem")){
            myRef.child("systemOff").setValue(s);
        }
    }

    public void setText(TextView view, String text){
        view.setText(text);
    }

    @SuppressLint("ResourceType")
    public void setTextColor(TextView view, int col){
        view.setTextColor(col);
    }

    public void viewState(LinearLayout view, int vis){
        view.setVisibility(vis);
    }

    public void checkboxState(Switch view, boolean vis){
        view.setChecked(vis);
    }

    @SuppressLint("ResourceAsColor")
    public void setImageBkg(ImageView view, int color){
        view.setBackgroundResource(color);
    }

    public void setImageRes(ImageView view, int r){
        //Drawable res = getResources().getDrawable(r);
        view.setImageResource(r);
    }


    public void setProgressBar(ProgressBar load, int percent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            load.setProgress(percent,true);
        }
    }

}
