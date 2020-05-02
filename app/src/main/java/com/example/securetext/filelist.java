package com.example.securetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Normalizer2;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import com.example.securetext.encrypt;

import static com.example.securetext.R.id.openfile;

public class filelist extends AppCompatActivity {

    Button goHome;
    Button viewFile;
    Button decryptFile;
    Button goBack;
    Button deleteFile;


    private String readFile(String str) {
        File fileEvents = new File(filelist.this.getFilesDir()+"/" + str);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filelist);
        View someView = findViewById(R.id.fl);
        View root = someView.getRootView();
        setTitle("Files Avaliable:");
        final TextView txtView = (TextView)findViewById(R.id.myfiles);

        String[] files = filelist.this.fileList();

        for (int i =0; i< files.length;i++)
        {
            Log.d("Files", files[i]);
            txtView.append(i+1 +". "+ files[i] +"\n");
        }
        goHome = (Button)findViewById(R.id.goBack5);
        goHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {

                Intent goHome = new Intent(filelist.this, MainActivity.class);

                startActivity(goHome);
            }
        });

        goBack = (Button)findViewById(R.id.goBack3);
        goBack.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {


                Intent goBack = new Intent(filelist.this, filelist.class);

                startActivity(goBack);
            }
        });


        viewFile = (Button)findViewById(R.id.viewfile);
        viewFile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                EditText openFile = (EditText) findViewById(openfile);
                String fileName = openFile.getText().toString();
                String fileContent = readFile(fileName);
                txtView.setText("Contents of "+ fileName + ":\n"+fileContent);
            }
        });

        deleteFile = (Button)findViewById(R.id.deletefile);
        deleteFile.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                EditText openFile = (EditText) findViewById(openfile);
                String fileName = openFile.getText().toString();
                try{
                    deleteFile(fileName);
                } catch (Exception e) {}


                Intent goBack = new Intent(filelist.this, filelist.class);
                startActivity(goBack);
            }
        });

        decryptFile = (Button)findViewById(R.id.decrypt2);
        decryptFile.setOnClickListener(new View.OnClickListener() {

            EditText edit = (EditText) findViewById(R.id.filename2);
            public void onClick(View view)
            {
                EditText openFile = (EditText) findViewById(openfile);
                Intent decrypt = new Intent(filelist.this, decrypt.class);
                decrypt.putExtra ( "Textbox", openFile.getText().toString() );
                startActivity(decrypt);
            }
        });

    }
}
