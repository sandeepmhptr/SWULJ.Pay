package com.example.swuljpay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class actionsActivity extends AppCompatActivity {

    ImageButton imgButton_bus_buy_ticket;
    ImageButton imgButton_bus_buy_monthly_pass;
    ImageButton imgButton_bus_buy_daily_pass;
    ImageButton imgButton_bus_buy_yearly_pass;
    EditText HTTPResult;
    String result;
    //QRResultData datum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);


        imgButton_bus_buy_ticket =(ImageButton)findViewById(R.id.imgButton_bus_buy_ticket);
        imgButton_bus_buy_monthly_pass = (ImageButton)findViewById(R.id.imgButton_bus_buy_monthly_pass);
        imgButton_bus_buy_daily_pass = (ImageButton)findViewById(R.id.imgButton_bus_buy_daily_pass);
        imgButton_bus_buy_yearly_pass = (ImageButton)findViewById(R.id.imgButton_bus_buy_yearly_pass);
        HTTPResult = (EditText)findViewById(R.id.HTTP_response);

        imgButton_bus_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
                Intent i=new Intent(actionsActivity.this,
                        QRScannerActivity2.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        });

        imgButton_bus_buy_monthly_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
            }
        });

        imgButton_bus_buy_daily_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
            }
        });

        imgButton_bus_buy_yearly_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
            }
        });
    }
}