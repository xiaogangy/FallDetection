package com.example.ibrahim.falldetection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    private EditText nameText,phoneText;
    private Button btnsave,btncancel;
    private FileService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        service = new FileService(this);
        nameText =(EditText) findViewById(R.id.EditName);
        phoneText = (EditText) findViewById(R.id.EditPhone);
        btnsave = (Button)findViewById(R.id.btnSave);
        btncancel = (Button) findViewById(R.id.btnCancle);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameText.getText().toString().trim();
                String phone = phoneText.getText().toString().trim();
                String item = name + phone +"\n";
                boolean flag = service.saveContentToFile("contact.txt", Context.MODE_APPEND,item.getBytes());
                if (name!=""){
                    service.saveContentToFile("Name.txt", Context.MODE_APPEND,(name+".").getBytes());
                }else{
                    service.saveContentToFile("Name.txt", Context.MODE_APPEND,name.getBytes());
                }
                if (phone!=""){
                    service.saveContentToFile("Phone.txt", Context.MODE_APPEND,(phone+".").getBytes());
                }else{
                    service.saveContentToFile("Phone.txt", Context.MODE_APPEND,phone.getBytes());
                }


                if(flag){
                    Toast.makeText(AddContact.this,"Save Success",Toast.LENGTH_SHORT).show();
                }

                AddContact.this.finish();



            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContact.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        service = new FileService(this);
//        String neirong = service.readContentFromFile("contact.txt");
//        System.out.println(neirong);
    }
}
