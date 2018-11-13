package com.example.edu.readwriteexternalstorage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    File file = null;
    public static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonFileReadOutter = findViewById(R.id.buttonFileReadOutter);
        Button buttonFileWriteOutter = findViewById(R.id.buttonFileWriteOutter);

        buttonFileReadOutter.setOnClickListener(this);
        buttonFileWriteOutter.setOnClickListener(this);


        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }
        @Override
        public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_CODE:
                    if (grantResults.length > 0 || grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.i("", "Permission has been granted by user");
                    }
            }
        }



    @Override
    public void onClick(View v) {
        EditText editTextInput = findViewById(R.id.editTextInput);

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        switch (v.getId()) {
            case R.id.buttonFileReadOutter:
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME);
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[0];
                try {
                    buffer = new byte[fileInputStream.available()];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fileInputStream.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                editTextInput.setText(new String(buffer));
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.buttonFileWriteOutter:
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME);

                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                if (!dir.exists()) dir.mkdirs();
                file = new File(dir, "bbb.txt");


                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fileOutputStream.write(editTextInput.getText().toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                editTextInput.setText("");
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.buttonFileReadFromInner:
                try {
                    fileInputStream = openFileInput("test.txt");
                    byte[] buffer1 = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer1);
                    editTextInput.setText(new String(buffer1));

                    fileInputStream.close();
                    break;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            case R.id.buttonFileWriteFromInner:
                try {
                    fileOutputStream = openFileOutput("test.txt", Context.MODE_PRIVATE);
                    fileOutputStream.write(editTextInput.getText().toString().getBytes());
                    editTextInput.setText("");
                    fileOutputStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }

    }
}