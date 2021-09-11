package com.example.swuljpay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QRScannerActivity2 extends AppCompatActivity implements View.OnClickListener{
    Button scanBtn;
    TextView messageText, messageFormat, messageText2, messageFormat2, HTTPResult;
    String Result = null;
    QRResultData datum;
    String myServer = "https://swulj.000webhostapp.com/bus_fetch.php";
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String Param = "PARAM";
    String Stop = "STOP";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_scanner2);
        scanBtn = findViewById(R.id.scanBtn);
        messageText = findViewById(R.id.textContent);
        messageFormat = findViewById(R.id.textFormat);
        messageText2 = findViewById(R.id.textContent2);
        messageFormat2 = findViewById(R.id.textFormat2);
        HTTPResult = findViewById(R.id.HTTPResult);
        // adding listener to the button
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setPrompt("Scan a barcode or QR Code");
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                Result = intentResult.getContents();
                datum = parseResult(Result, myServer);
                //messageText.setText(Result);
                //messageFormat.setText(intentResult.getFormatName());
                messageText.setText(datum.seatNumber);
                messageFormat.setText(datum.busNumber);
                messageText2.setText(datum.original);
                messageFormat2.setText(Result);
                //Toast.makeText(getApplicationContext(),"You download is resumed2",Toast.LENGTH_LONG).show();
                HTTPConnection1 conn = new HTTPConnection1();
                conn.execute(datum);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public QRResultData parseResult(String Result, String urlArg)
    {
        QRResultData data = new QRResultData();
        /*StringTokenizer multiTokenizer = new StringTokenizer(Result, ";");

        if(multiTokenizer.hasMoreTokens())
        {
            data.busNumber = multiTokenizer.nextToken();
        }

        if(multiTokenizer.hasMoreTokens())
        {
            data.seatNumber = multiTokenizer.nextToken();
        }*/
        data.url = urlArg;
        data.original = Result;
        String[] split = Result.split(";");
        data.busNumber = split[0];
        if (split.length > 1) {
            data.seatNumber = split[1];
        }
        return data;
    }

    class HTTPConnection1  extends AsyncTask<QRResultData, Void, String> {
        String result;
        String url;
        String busNumber;
        @Override
        protected String doInBackground(QRResultData... params) {
            QRResultData data= params[0];
            url = data.url;
            busNumber = data.busNumber;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                nameValuePairs.add(new BasicNameValuePair("busNumber", busNumber));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse httpResponse = httpclient.execute(httppost);
                InputStream inputStream = httpResponse.getEntity().getContent();
                //HTTPResult.setText("result3");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                //HTTPResult.setText("result4");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                //HTTPResult.setText("result5");
                StringBuilder stringBuilder = new StringBuilder();
                //HTTPResult.setText("result6");
                String bufferedStrChunk = null;

                while((bufferedStrChunk = bufferedReader.readLine()) != null){
                    stringBuilder.append(bufferedStrChunk);
                }


                result = stringBuilder.toString();
                //result= "Sandeep";

            } catch (ClientProtocolException e) {
                result = "ClientProtocolException";
                // TODO Auto-generated catch block
            } catch (IOException e) {
                result = "IOException";
                // TODO Auto-generated catch block
            }
            return null;
        }
        @Override
        protected void onPostExecute(String bitmap) {
            super.onPostExecute(bitmap);
            HTTPResult.setText(result);
            //parseAndNewIntent(result);
        }

    }
    void parseAndNewIntent(String result)
    {
        List<String> list = new ArrayList<>();
        int count_stops = 0;
        String delim = "stops are";
        int i = result.indexOf(delim);
        i = i + 9;
        int j = result.indexOf(';',i);
        list.add(result.substring(i,j));
        String a = "";
        while( result.charAt(j+1) != '<')
        {
            i = j + 1;
            j = result.indexOf(';',i);
            list.add(result.substring(i,j));
        }
        count_stops = list.size();
        for(int x=0; x < count_stops; x++)
        {
            a += list.get(x) + ";";
        }
        HTTPResult.setText(a);

        Intent inte=new Intent(QRScannerActivity2.this,
                SelectStopsActivity.class);
        //Intent is used to switch from one activity to another.
        inte.putExtra(BUS_NUMBER, datum.busNumber);
        inte.putExtra(Param,String.valueOf(count_stops));

        for(int u = 1; u <= count_stops; u++){
            String temp = Stop + String.valueOf(u);
            inte.putExtra(temp, list.get(u-1));
        }
        startActivity(inte);
        //invoke the SecondActivity.

        finish();
    }
}