package com.example.swuljpay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ActivityNavigator;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

public class DestinationStopActivity extends AppCompatActivity {
    String BUS_NUMBER = "EXTRA_BUS_NUMBER";
    String Id = "ID";
    String Source = "SOURCE";
    String Destination = "DESTINATION";
    String Fare = "FARE";
    String busNumber;
    String Param = "PARAM";
    String Stop = "STOP";
    int count_stops = 0;
    String Stops[];
    Button next_page;
    String ID_details;
    String source;
    String url = "https://swulj.000webhostapp.com/bus_fetch_fares.php";
    TextView HTTPResult;
    PaymentData datum = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_stop);

        next_page = (Button) findViewById(R.id.btn1);
        HTTPResult = (TextView) findViewById(R.id.HTTPResult) ;

        Intent i = getIntent();

        busNumber = i.getStringExtra(BUS_NUMBER);
        String temp = i.getStringExtra(Param);
        count_stops = Integer.valueOf(temp);
        source = i.getStringExtra(Source);
        ID_details = i.getStringExtra(Id);

        Stops = new String[count_stops];

        for (int x = 1; x <= count_stops; x++) {
            String temp2 = Stop + String.valueOf(x);
            Stops[x - 1] = i.getStringExtra(temp2);
        }


        // get reference to radio group in layout
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        // layout params to use when adding each radio button
        LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        // add 20 radio buttons to the group
        for (int u = 0; u < count_stops; u++) {
            RadioButton newRadioButton = new RadioButton(this);
            String label = Stops[u];
            newRadioButton.setText(label);
            newRadioButton.setId(u);
            radiogroup.addView(newRadioButton, layoutParams);
        }
        next_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
                int a = radiogroup.getCheckedRadioButtonId();
                //String ID = (String) id_input.getText().toString();
                if (a >= 0) {
                    datum = new PaymentData(source, Stops[a], busNumber, url, ID_details);
                    HTTPConnection1 conn = new HTTPConnection1();
                    conn.execute(datum);
                }

            }
        });
    }

    class HTTPConnection1 extends AsyncTask<PaymentData, Void, String> {
        String result;
        String url;
        String busNumber;
        String ID;
        String Source;
        String Destination;

        @Override
        protected String doInBackground(PaymentData... params) {
            PaymentData data = params[0];
            url = data.url;
            busNumber = data.busNumber;
            ID = data.ID;
            Source = data.Source;
            Destination = data. Destination;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
                nameValuePairs.add(new BasicNameValuePair("busNumber", busNumber));
                nameValuePairs.add(new BasicNameValuePair("ID", ID));
                nameValuePairs.add(new BasicNameValuePair("Source", Source));
                nameValuePairs.add(new BasicNameValuePair("Destination", Destination));
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

                while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
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
            parseAndNewIntent(result);
        }
    }
    void parseAndNewIntent(String result)
    {
        String delim = "fare =";
        String fare ="";
        //String s ="";
        int i = result.indexOf(delim);
        int k = i + 6;
        int j = result.indexOf('<',i);
        fare = result.substring(k,j);
        /*s +="i = ";
        s += result.charAt(i);
        s += ", k = ";
        s += result.charAt(k);*/

        Intent inte=new Intent(DestinationStopActivity.this,
                PayActivity.class);
        //Intent is used to switch from one activity to another.
        inte.putExtra(BUS_NUMBER, datum.busNumber);
        inte.putExtra(Source,datum.Source);
        inte.putExtra(Destination, datum.Destination);
        inte.putExtra(Fare,fare);
        inte.putExtra(Id,ID_details);
        HTTPResult.setText(result);
        startActivity(inte);
        //invoke the SecondActivity.

        finish();
    }
}