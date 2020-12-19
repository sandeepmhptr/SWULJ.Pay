package com.example.swuljpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton imgButton_scan_ticket;
    ImageButton imgButton_scan_and_pay;
    ImageButton imgButton_bank_transfer;
    EditText HTTPResult;
    String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButton_scan_and_pay =(ImageButton)findViewById(R.id.imageButton_scan_and_pay);
        imgButton_bank_transfer =(ImageButton)findViewById(R.id.imageButton_bank_transfer);
        HTTPResult = (EditText)findViewById(R.id.HTTP_response);

        imgButton_scan_and_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed2",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"You download is resumed1",Toast.LENGTH_LONG).show();
                //System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);
                Intent i=new Intent(MainActivity.this,
                        ScanActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        });


        imgButton_bank_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
            }
        });


    }
}