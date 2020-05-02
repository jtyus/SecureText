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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
public class encrypt extends AppCompatActivity {

    Button goHome;
    Button encrypt;
    Button encryptFile;
    String str2="";
    String fileName="";
    
    private static final int pwIts = 10;
    private static final int keyLen = 128;
    private static final String cypherInstance = "AES/CBC/PKCS5Padding";
    private static final String secretKeyInstance = "PBKDF2WithHmacSHA1";
    private static final String plainText = "sampleText";
    private static final String AESSalt = "exampleSalt";
    private static final String initializationVector = "8119745113154120";

    public static String encrypt(String textToEncrypt) throws Exception {

        SecretKeySpec skeySpec = new SecretKeySpec(getRaw(plainText, AESSalt), "AES");
        Cipher cipher = Cipher.getInstance(cypherInstance);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(initializationVector.getBytes()));
        byte[] encrypted = cipher.doFinal(textToEncrypt.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    public static String decrypt(String textToDecrypt) throws Exception {

        byte[] encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT);
        SecretKeySpec skeySpec = new SecretKeySpec(getRaw(plainText, AESSalt), "AES");
        Cipher cipher = Cipher.getInstance(cypherInstance);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(initializationVector.getBytes()));
        byte[] decrypted = cipher.doFinal(encryted_bytes);
        return new String(decrypted, "UTF-8");
    }

    private static byte[] getRaw(String plainText, String salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(secretKeyInstance);
            KeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt.getBytes(), pwIts, keyLen);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    //Encrypt and Decrypt from https://developer.android.com/guide/topics/security/cryptography#java

    private String readFile(String str) {
        File fileEvents = new File(encrypt.this.getFilesDir()+"/" + str);
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
        setContentView(R.layout.activity_encrypt);
        View someView = findViewById(R.id.encryptLayout);
        View root = someView.getRootView();
        encrypt = (Button)findViewById(R.id.encrypt2);
        encrypt.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                EditText getText = (EditText) findViewById(R.id.textencrypt);
                EditText getFile = (EditText) findViewById(R.id.filename);
                String str = getText.getText().toString();
                String filename = getFile.getText().toString();
                try {
                    str2 = encrypt(str);
                    Log.d("Encrypted Message", str2);
                    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    fos.write(str2.getBytes());
                    fos.close();

                } catch (Exception e) {}

                String[] files = encrypt.this.fileList();

                for (int i =0; i< files.length;i++)
                {
                    Log.d("Files", files[i]);
                }
                Intent filelist = new Intent(encrypt.this, filelist.class);
                startActivity(filelist);


            }
        });

        encryptFile = (Button)findViewById(R.id.encryptfile);
        encryptFile.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                EditText getFile = (EditText) findViewById(R.id.filename);
                fileName = getFile.getText().toString();

                Log.d("Text:",readFile(fileName));
                String str3 ="";
                try {
                    str3 += encrypt(readFile(fileName));
                    FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                    fos.write(str3.getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("Encrypted Text",str3);

                Intent filelist = new Intent(encrypt.this, filelist.class);
                startActivity(filelist);


            }
        });


        goHome = (Button)findViewById(R.id.goBack4);
        goHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {
                String str3 ="";
                try {
                    str3 += decrypt(str2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent goBack = new Intent(encrypt.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }

}


