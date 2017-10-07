package com.example.ibrahim.falldetection;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.ibrahim.falldetection.Service.FallMonitorService;

import java.util.ArrayList;
import java.util.List;

/**
 * This class manipulate the second activity.
 * There are two buttons in this page.
 * One is used to control the service on/off.
 * The other will direct to contacts configuration page.
 */

public class FallDetection extends AppCompatActivity {

//implements ServiceConnection
    private Button btnConfiguration;
    private ToggleButton toggleButton;
    private FallMonitorService fallMonitorService = null;
    private Intent intentservice;
    private boolean serviceIsRunning = false;
    private Context mContext= FallDetection.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_detection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        serviceIsRunning = isServiceRunning(mContext,"com.example.ibrahim.falldetection.Service.FallMonitorService");
        intentservice = new Intent(FallDetection.this,FallMonitorService.class);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        if(serviceIsRunning){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);
        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    startService(intentservice);
                    //bindService(intentservice,FallDetection.this, Context.BIND_AUTO_CREATE);
                    Toast.makeText(FallDetection.this,"Start Monitor",Toast.LENGTH_SHORT).show();

                }else{
                    stopService(intentservice);
                    //unbindService(FallDetection.this);
                    Toast.makeText(FallDetection.this,"Close Monitor",Toast.LENGTH_SHORT).show();

                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnConfiguration = (Button) findViewById(R.id.btnConfiguration);
        btnConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toConfiguration = new Intent(FallDetection.this,FallConfiguration.class);
                startActivity(toConfiguration);
            }
        });
    }




    //判断服务是否在运行
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        int size = serviceList.size();
        if (!(size > 0)) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("---------------------->"+"activity关闭");
    }
}
