package com.example.ibrahim.falldetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnFallDetection;
    private Button btnActivityRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFallDetection = (Button) findViewById(R.id.fallDetection);
        btnFallDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFall = new Intent(MainActivity.this,FallDetection.class);
                startActivity(toFall);
            }
        });

//        btnActivityRecord = (Button)findViewById(R.id.ActivitiesRecords);
//        btnActivityRecord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toActivies = new Intent(MainActivity.this,ActivityRecord.class);
//                startActivity(toActivies);
//            }
//        });
    }
}
