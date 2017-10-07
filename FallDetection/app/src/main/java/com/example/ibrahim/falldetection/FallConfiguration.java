package com.example.ibrahim.falldetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This class manipulate the contacts interface.
 * It reads the contacts information from local storage
 * and use a ListView to show all these people.
 */
public class FallConfiguration extends AppCompatActivity {

    private Button btnAdd,btnClean;
    private FileService service;
    private TextView txt_empty;
    private FileService fileService;
    //for test
//    public static int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fall_configuration);
//        i++;
//        System.out.println("Create的activity是----------------->"+i);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnAdd = (Button) findViewById(R.id.AddContact);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAdd = new Intent(FallConfiguration.this,AddContact.class);
                startActivity(toAdd);
            }
        });

        fileService = new FileService(this);
        btnClean = (Button) findViewById(R.id.CleanContact);
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileService.cleanContent("Name.txt");
                fileService.cleanContent("Phone.txt");
                fileService.cleanContent("contact.txt");
                Intent intent = new Intent(FallConfiguration.this, FallConfiguration.class);
                startActivity(intent);
                finish();

//               System.out.println(">>>>>>>>>>>>>>>>>>>>>Name"+fileService.readContentFromFile("Name.txt").length());
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>Phone"+fileService.readContentFromFile("Phone.txt").length());
//               System.out.println(">>>>>>>>>>>>>>>>>>>>>Contact"+fileService.readContentFromFile("contact.txt").length());

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
//        System.out.println("Start的activity是----------------->"+i);
        service = new FileService(this);
        String name = service.readContentFromFile("Name.txt");
        String[] nameList={};
        if (name.length()>0){
            nameList = name.split("\\.");
        }

//        System.out.println("----------->nameList"+Arrays.toString(nameList)+"-------->"+nameList.length);
        String phone = service.readContentFromFile("Phone.txt");
        String[] phoneList = {};
        if (phone.length()>0){
            phoneList = phone.split("\\.");
        }

//        System.out.println("----------->PhoneList"+Arrays.toString(phoneList)+"-------->"+phoneList.length);

        String[] strs = new String[nameList.length];
//        System.out.println("----------->PhoneList"+Arrays.toString(strs)+"-------->"+strs.length);
        for(int i=0;i<nameList.length;i++){
                strs[i] = nameList[i] + "  :  " + phoneList[i];
            }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1 ,strs);
        ListView list_view = (ListView) findViewById(R.id.CurrentContacts);
        list_view.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        txt_empty = (TextView) findViewById(R.id.txt_empty);
        txt_empty.setText("No Contacts Now ~");
        list_view.setEmptyView(txt_empty);
    }

//    public void refresh() {
//        onCreate(null);
//    }


    @Override
    protected void onResume() {
        super.onResume();
//        System.out.println("Resume的activity是----------------->"+i);
    }

    @Override
    protected void onPause() {
//        System.out.println("Pause的activity是----------------->"+i);
        super.onPause();
    }

    @Override
    protected void onStop() {
//        System.out.println("Stop的activity是----------------->"+i);
        super.onStop();
    }

    @Override
    protected void onRestart() {
//        System.out.println("Restart的activity是----------------->"+i);
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
//        System.out.println("Destroy的activity是----------------->"+i);
        super.onDestroy();
    }
}
