package com.example.nevinvarghese.projectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityStore extends AppCompatActivity {

    Spinner sp;
    String item;
    EditText symptoms;
    List<String>arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        arrayList=new ArrayList<>();
        Toast.makeText(ActivityStore.this,"lgjhgkjh",Toast.LENGTH_LONG).show();
        sp=(Spinner)findViewById(R.id.spinner);
        symptoms=(EditText) findViewById(R.id.symptoms);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item=sp.getSelectedItem().toString();
                arrayList.add(item);

                symptoms.setText(TextUtils.join(",",arrayList));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
