package com.example.securetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    Button encrypt;
    Button hash;
    Button decrypt;
    Button filelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View someView = findViewById(R.id.m);
        View root = someView.getRootView();
        setTitle("");
        encrypt = (Button)findViewById(R.id.encrypt);
        hash = (Button)findViewById(R.id.shahash);
        decrypt = (Button)findViewById(R.id.decrypt);
        filelist = (Button)findViewById(R.id.filelist);

        encrypt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                Intent encrypt = new Intent(MainActivity.this, encrypt.class);
                startActivity(encrypt);
            }
        });

        hash.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                Intent hash = new Intent(MainActivity.this, encrypt2.class);
                startActivity(hash);
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                Intent decrypt = new Intent(MainActivity.this, decrypt.class);
                startActivity(decrypt);
            }
        });

        filelist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {

                Intent filelist = new Intent(MainActivity.this, filelist.class);
                startActivity(filelist);
            }
        });

    }


}
