package com.example.mycourses.ui.login;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import com.example.mycourses.R;
import com.example.mycourses.ui.login.ui.main.Frag1;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;


import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.mycourses.ui.login.ui.main.SectionsPagerAdapter;

public class courseActivity extends AppCompatActivity {

    //FOR BLUETOOTH
        private static final String TAG = "BlueTest5-Controlling";
        private int mMaxChars = 50000;//Default//change this to string..........
        private UUID mDeviceUUID;
        private BluetoothSocket mBTSocket;
        private ReadInput mReadThread = null;

        private boolean mIsUserInitiatedDisconnect = false;
        private boolean mIsBluetoothConnected = false;


        private Button mBtnDisconnect;
        private BluetoothDevice mDevice;

        final static String on="92";//on
        final static String off="551";//off


        private ProgressDialog progressDialog;
        ToggleButton On_Off;
        SectionsPagerAdapter sectionsPagerAdapter;
        String[] x = {"ON","GEN","120","0","240","5","90","125"};

        Frag1 frag1;
        Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        try{
            ActivityHelper.initialize(this);
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            mDevice = b.getParcelable(HomeActivity.DEVICE_EXTRA);
            mDeviceUUID = UUID.fromString(b.getString(HomeActivity.DEVICE_UUID));
            mMaxChars = b.getInt(HomeActivity.BUFFER_SIZE);
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
        final FloatingActionButton fab = findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your Convenience is our Satisfaction\nCustomer Care: detunj@gmail.com", Snackbar.LENGTH_LONG)
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


    public void updateValue(String conn,String mainsV,String invV,String genV,String ibl,String fl,String pow){
        int batLevel = Integer.parseInt(ibl);
//        batLevel-=10;
//        batLevel/=4;
//        batLevel*=100;
//        Log.d(TAG,"batLevel:"+batLevel);
//        if (batLevel<0){
//            batLevel=0;
//        }
        frag1.setText((TextView) findViewById(R.id.fl),fl+"%");
        frag1.setText((TextView) findViewById(R.id.bl),(int)batLevel+"%");
        frag1.setText((TextView) findViewById(R.id.mainV),mainsV+" Volt");
        frag1.setText((TextView) findViewById(R.id.genV),genV+" Volt");
        frag1.setText((TextView) findViewById(R.id.invV),invV+" Volt");
        frag1.setText((TextView) findViewById(R.id.conn),conn);
        frag1.setText((TextView) findViewById(R.id.power),pow+"W");
//        int bat = (int)batLevel*10);
            frag1.setProgressBar((ProgressBar)findViewById(R.id.flBar),Integer.parseInt(fl));
            frag1.setProgressBar((ProgressBar)findViewById(R.id.blBar),batLevel);

    }

    private class ReadInput implements Runnable {

        private boolean bStop = false;
        private Thread t;

        public ReadInput() {

            t = new Thread(this, "Input Thread");
            t.start();
        }



        public boolean isRunning() {
            return t.isAlive();
        }

        @SuppressLint("WrongConstant")
        @Override
        public void run() {
            InputStream inputStream;

            try {
                inputStream = mBTSocket.getInputStream();
                inputStream.skip(inputStream.available());
                while (!bStop) {
//                    Log.d(TAG,"here");
//                    ((TextView)findViewById(R.id.fl)).setText("-55%");
                    byte[] buffer = new byte[256];

                    if (inputStream.available() > 0) {
                        inputStream.read(buffer);
//                        System.out.println(inputStream.read());

//                        Log.d(TAG," beffer here");
                        int i = 0;
                        /*
                         * This is needed because new String(buffer) is taking the entire buffer i.e. 256 chars on Android 2.3.4 http://stackoverflow.com/a/8843462/1287554
                         */
                        for (i = 0; i < buffer.length && buffer[i] != 0; i++) {
                        }
                        final String strInput = new String(buffer, 0, i);
                        Log.d(TAG, "run: "+strInput);
                        if (strInput.length()>0){
                            System.out.println(strInput);

//                            String set="";
                            x = strInput.split(";");
                            try {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
//                                        Log.d(TAG, "runeXit: "+x[6]);
                                        if (x.length==8) {
//                                            Log.d(TAG, "runX: "+x[6]);
                                            updateValue(x[1], x[2], x[3], x[4], x[6], x[5], x[7].trim());
                                        }else
                                            Log.d(TAG,"length too small");
                                    }
                                });
                            }catch (Exception e){
                                Log.d(TAG,"ERROR HERE O");
                            }

//                            for (int v=0;v<x.length;v++)
//                                System.out.println("L:"+x.length+"----"+x[v]);
//                            updateValue(x[2],x[4],x[3],x[1],x[6],x[5],x[7]);

//                            Frag1 frag1 = (Frag1) sectionsPagerAdapter.getItem(0);
//                            frag1.setText((TextView) findViewById(R.id.fl),"hdhhdd%");

//                            setText((TextView) v.findViewById(R.id.fl),"90%");
//                            ((TextView)findViewById(R.id.fl)).setText(strInput);

                        }

                        /*
                       /  * If checked then receive text, better design would probably be to stop thread if unchecked and free resources, but this is a quick fix
                         */



                    }
                    Thread.sleep(100);
                }
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }catch (Exception w){

            }

        }

        public void stop() {
            bStop = true;
        }

    }


    private class DisConnectBT extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {//cant inderstand these dotss

            if (mReadThread != null) {
                mReadThread.stop();
                while (mReadThread.isRunning())
                    ; // Wait until it stops
                mReadThread = null;

            }

            try {
                mBTSocket.close();
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mIsBluetoothConnected = false;
            if (mIsUserInitiatedDisconnect) {
                finish();
            }
        }

    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (mBTSocket != null && mIsBluetoothConnected) {
            new DisConnectBT().execute();
        }
        Log.d(TAG, "Paused");
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mBTSocket == null || !mIsBluetoothConnected) {
            new ConnectBT().execute();
        }
        Log.d(TAG, "Resumed");
        super.onResume();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopped");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
// TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }


    //Cbt
    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean mConnectSuccessful = true;

        @Override
        protected void onPreExecute() {

            progressDialog = ProgressDialog.show(courseActivity.this, "Hold on", "Connecting");// http://stackoverflow.com/a/11130220/1287554

        }

        @Override
        protected Void doInBackground(Void... devices) {

            try {
                if (mBTSocket == null || !mIsBluetoothConnected) {
                    mBTSocket = mDevice.createInsecureRfcommSocketToServiceRecord(mDeviceUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    mBTSocket.connect();
                }
            } catch (IOException e) {
// Unable to connect to device`
                // e.printStackTrace();
                mConnectSuccessful = false;



            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (!mConnectSuccessful) {
                Toast.makeText(getApplicationContext(), "Could not connect to device.Please turn on your Hardware", Toast.LENGTH_LONG).show();
                finish();
            } else {
                msg("Connected to device");
                mIsBluetoothConnected = true;
                mReadThread = new ReadInput(); // Kick off input reader
            }

            progressDialog.dismiss();
        }

    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}