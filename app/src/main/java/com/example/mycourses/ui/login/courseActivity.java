package com.example.mycourses.ui.login;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import com.example.mycourses.R;
import com.example.mycourses.ui.login.ui.main.Frag1;
import com.example.mycourses.ui.login.ui.main.Frag2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mycourses.ui.login.ui.main.SectionsPagerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class courseActivity extends AppCompatActivity {

    //FOR BLUETOOTH
        private static final String TAG = "BlueTest5-Controlling";
        private int mMaxChars = 50000;//Default//change this to string..........


        final static String on="92";//on
        final static String off="551";//off


        private ProgressDialog progressDialog;
        ToggleButton On_Off;
        SectionsPagerAdapter sectionsPagerAdapter;
        String[] x = {"ON","GEN","120","0","240","5","90","125"};

        Frag1 frag1;
        Frag2 frag2;

    Handler handler = new Handler();
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        try{
            ActivityHelper.initialize(this);
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            Log.d(TAG, "Ready");
        }catch (Exception e){
            Log.d(TAG,"Intent Error");
        }



        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        frag1 = (Frag1) sectionsPagerAdapter.getItem(0);
        frag2 = (Frag2) sectionsPagerAdapter.getItem(1);
        final FloatingActionButton fab = findViewById(R.id.fab);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("ChangeOver");
        myRef.keepSynced(true);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.toString();
//                Log.d(TAG, "Value is: " + dataSnapshot.getValue());
                JSONObject json = new JSONObject((Map) dataSnapshot.getValue());
                try {
                    String checkgen = json.getString("checkGen");
                    int crankTime = Integer.parseInt(""+json.get("crankTime"));
                    int dt = Integer.parseInt(json.getString("dt"));
                    String genAC = json.getString("genAC");
                    int genUpTimeLim = Integer.parseInt(""+json.get("genUpTimeLim"));
                        genUpTimeLim = ((genUpTimeLim)/3600);
                    int genTimerLim = Integer.parseInt(""+json.get("genTimerLim"));
                        genTimerLim = ((genTimerLim)/3600);
                    String keystate = json.getString("keyState");
                    String phase1 = json.getString("mains1")+"V";
                    String phase2 = json.getString("mains2")+"V";
                    String genOn = String.valueOf(json.get("manualGenOn"));
                    String mainsOn = json.getString("manualMainsOn");
//                    String offsystem = ""+json.get("systemOff");
                    String systemOff = json.getString("offsystem");
                    String wifiConnected = json.getString("wifiConnected");
                    String wifiName = json.getString("wifiName");
                    String wifiPassword = json.getString("wifiPassword");

                    Log.d(TAG, "DB Update");
                    frag1.setText((TextView) findViewById(R.id.phase1V), phase1);
                    frag1.setText((TextView) findViewById(R.id.phase2V),phase2);
                    if(wifiConnected == "true"){
                        frag1.setImageRes((ImageView) findViewById(R.id.wifi_connected),R.drawable.wifi_connected);
                        frag1.setImageBkg((ImageView) findViewById(R.id.wifi_connected),R.color.conn);
                    }else{
                        frag1.setImageRes((ImageView) findViewById(R.id.wifi_connected),R.drawable.wifi_off);
                        frag1.setImageBkg((ImageView) findViewById(R.id.wifi_connected),R.color.not_conn);
                    }
                    frag1.setText((TextView) findViewById(R.id.downtime_timer),getTimer(dt));
                    if(dt == 0){
                        frag1.setImageRes((ImageView) findViewById(R.id.downtime_icon),R.drawable.empty_downtime);
                        frag1.setTextColor((TextView) findViewById(R.id.downtime_label), ContextCompat.getColor(courseActivity.this, R.color.default_text ));
                        frag1.setTextColor((TextView) findViewById(R.id.downtime_timer),ContextCompat.getColor(courseActivity.this, R.color.default_text ));
                    }else{
                        frag1.setImageRes((ImageView) findViewById(R.id.downtime_icon),R.drawable.downtime_filled);
                        frag1.setTextColor((TextView) findViewById(R.id.downtime_label),ContextCompat.getColor(courseActivity.this, R.color.error ));
                        frag1.setTextColor((TextView) findViewById(R.id.downtime_timer),ContextCompat.getColor(courseActivity.this, R.color.error ));
                    }
                    frag1.checkboxState((Switch) findViewById(R.id.mains_switch),mainsOn=="false"?false:true);
                    frag1.checkboxState((Switch) findViewById(R.id.gen_switch),genOn=="false"?false:true);
                    frag1.checkboxState((Switch) findViewById(R.id.keystate_switch),keystate=="false"?false:true);
                    frag1.checkboxState((Switch) findViewById(R.id.system_switch),systemOff=="false"?false:true);

                    frag2.setSpinner((Spinner) findViewById(R.id.gen_crank_time), crankTime);
                    frag2.setSpinner((Spinner) findViewById(R.id.gen_up_time), genUpTimeLim );
                    frag2.setSpinner((Spinner) findViewById(R.id.inverter_time), genTimerLim);

                    frag2.setInput((EditText) findViewById(R.id.ssid),wifiName);
                    frag2.setInput((EditText) findViewById(R.id.ssid_password),wifiPassword);

                    if(checkgen == "true"){
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                R.anim.notify);
                        LinearLayout i = (LinearLayout) findViewById(R.id.checkgen_layout);
                        i.startAnimation(animation);
                        frag1.viewState((LinearLayout) findViewById(R.id.checkgen_layout),View.VISIBLE);
                    }else{
                        ((LinearLayout) findViewById(R.id.checkgen_layout)).clearAnimation();
                        frag1.viewState((LinearLayout) findViewById(R.id.checkgen_layout),View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), "error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your Convenience is our Satisfaction\nSupport Contact: folarinishola@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                updateValue(
//                        (new Random().nextInt(3)<1?"GEN":"MAINS"),
//                        ""+new Random().nextInt(240),
//                        ""+new Random().nextInt(240),
//                        ""+new Random().nextInt(240),
//                        ""+(10+new Random().nextInt(4)),
//                        ""+new Random().nextInt(100),
//                        ""+new Random().nextInt(124));
//                frag1.setText((TextView) findViewById(R.id.fl),"100%");
            }
        });



    }


    public String getTimer(int t){
        if(t > 0){
            int secs = t%60;
            String sec = secs<10?("0"+secs): String.valueOf(secs);
            int hrs = (int)(t/3600);
            String hr = hrs<10?("0"+(hrs)):String.valueOf(hrs);
            int mins = (int)((t%3600)/60);
            String min = mins<10?("0"+(mins)):String.valueOf(mins);
            return hr+":"+min+":"+sec;
        }else
            return "00:00:00";
////        int bat = (int)batLevel*10);
//            frag1.setProgressBar((ProgressBar)findViewById(R.id.flBar),Integer.parseInt(fl));
//            frag1.setProgressBar((ProgressBar)findViewById(R.id.blBar),batLevel);

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

}


    //Cbt
