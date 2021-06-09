package com.example.mycourses.ui.login.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mycourses.R;

public class Frag1 extends Fragment {
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab2,container,false);
        return v;
    }

    public void setText(TextView view, String text){
        view.setText(text);
    }


    public void setProgressBar(ProgressBar load, int percent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            load.setProgress(percent,true);
        }
    }

}
