package com.example.edu.readwriteexternalstorage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonFileReadOutter = findViewById(R.id.buttonFileReadOutter);
        Button buttonFileWriteOutter = findViewById(R.id.buttonFileWriteOutter);

        buttonFileReadOutter.setOnClickListener(this);
        buttonFileWriteOutter.setOnClickListener(this);
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
        }
    }
}