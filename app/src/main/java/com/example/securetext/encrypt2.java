package com.example.securetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class encrypt2 extends AppCompatActivity {

    Button goHome;
    Button hash;
    String str2="";

    public static String hash (String textToHash) throws Exception {

        byte[] message = textToHash.getBytes();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(message);

        return digest.toString();
    }
    //MessageDigest from https://developer.android.com/guide/topics/security/cryptography#java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt2);
        View someView = findViewById(R.id.e2);
        View root = someView.getRootView();
        //root.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        hash = (Button)findViewById(R.id.hash);
        hash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText getText = (EditText) findViewById(R.id.texthash);
                EditText getFile = (EditText) findViewById(R.id.filename3);
                String str = getText.getText().toString();
                String filename = getFile.getText().toString();

                try {
                    str2 = hash(str);
                    Log.d("Encrypted Message", str2);
                    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    fos.write(str2.getBytes());
                    fos.close();

                } catch (Exception e) {}
                Intent filelist = new Intent(encrypt2.this, filelist.class);
                startActivity(filelist);
            }
        });

        goHome = (Button)findViewById(R.id.goBack7);
        goHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                Intent goBack = new Intent(encrypt2.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }
}
