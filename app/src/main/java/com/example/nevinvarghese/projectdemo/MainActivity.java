package com.example.nevinvarghese.projectdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton androidImageButton1 ;
    ImageButton androidImageButton2;
    ImageButton androidImageButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidImageButton1=(ImageButton)findViewById(R.id.imageButton1);
        androidImageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ActivityStore.class);
                startActivity(i);



            }
        });

        androidImageButton2=(ImageButton)findViewById(R.id.imageButton2);
        androidImageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
                Toast.makeText(MainActivity.this,"FINDING NEARBY HOSPITALS AND PHARMACIES",Toast.LENGTH_LONG).show();
            }
        });

        androidImageButton3=(ImageButton)findViewById(R.id.imageButton3);
        androidImageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"LOADING CHATBOT",Toast.LENGTH_LONG).show();
            }
        });




    }
}
