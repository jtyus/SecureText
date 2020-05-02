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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class decrypt extends AppCompatActivity {

    Button goHome;
    Button decrypt;
    String filename="";

    private static final int pswdIterations = 10;
    private static final int keySize = 128;
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
            KeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt.getBytes(), pswdIterations, keySize);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    private String readFile(String str) {
        File fileEvents = new File(decrypt.this.getFilesDir()+"/" + str);
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
        setContentView(R.layout.activity_decrypt);
        View someView = findViewById(R.id.d);
        View root = someView.getRootView();
        decrypt = (Button)findViewById(R.id.decrypt);
        decrypt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //EditText getText = (EditText) findViewById(R.id.textencrypt);
                EditText getFile = (EditText) findViewById(R.id.filename2);
                //String str = getText.getText().toString();
                filename = getFile.getText().toString();

                Log.d("Encrypted Text:",readFile(filename));
                String str3 ="";
                try {
                    str3 += decrypt(readFile(filename));
                    FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                    fos.write(str3.getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("Decrypted Text",str3);

                Intent filelist = new Intent(decrypt.this, filelist.class);
                startActivity(filelist);




            }
        });

        goHome = (Button)findViewById(R.id.goBack2);
        goHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view)
            {


                Intent goBack = new Intent(decrypt.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }
}
