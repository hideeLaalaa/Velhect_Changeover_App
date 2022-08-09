package com.example.mycourses.ui.login.ui.main;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
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
