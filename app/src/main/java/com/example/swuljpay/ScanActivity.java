package com.example.swuljpay;

import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {
    ImageButton imgButton_bus;
    ImageButton imgButton_hotel;
    ImageButton imgButton_library;
    ImageButton imgButton_shop;
    ImageButton imgButton_theatre;
    EditText HTTPResult;
    String myServer="https://swulj.000webhostapp.com/test.php";
    String stackServer = "https://stackoverflow.com/";
    String paramBusNumber = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        imgButton_bus = (ImageButton)findViewById(R.id.imageButton_bus);
        imgButton_hotel = (ImageButton)findViewById(R.id.imageButton_hotel);
        imgButton_library = (ImageButton)findViewById(R.id.imageButton_library);
        imgButton_shop = (ImageButton)findViewById(R.id.imageButton_shop);
        imgButton_theatre = (ImageButton)findViewById(R.id.imageButton_theatre);
        HTTPResult = (EditText)findViewById(R.id.HTTP_response);


        imgButton_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"You download is resumed1",Toast.LENGTH_LONG).show();
                //HTTPConnection1 conn = new HTTPConnection1();
                //conn.execute(stackServer);
                Intent i=new Intent(ScanActivity.this,
                        QRScannerActivity2.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
            }
        });

        imgButton_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed2",Toast.LENGTH_LONG).show();
                HTTPConnection1 conn = new HTTPConnection1();
                conn.execute(myServer);
            }
        });


        imgButton_library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
            }
        });

        imgButton_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed2",Toast.LENGTH_LONG).show();
            }
        });


        imgButton_theatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You download is resumed3",Toast.LENGTH_LONG).show();
            }
        });
    }

    class HTTPConnection1  extends AsyncTask<String, Void, String> {
        String result;
        String url;
        @Override
        protected String doInBackground(String... params) {
            url = params[0];
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", "12345"));
                nameValuePairs.add(new BasicNameValuePair("message", "msg"));
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
        }

    }
}

/*HttpClient httpClient = new DefaultHttpClient();

                // In a POST request, we don't pass the values in the URL.
                //Therefore we use only the web page URL as the parameter of the HttpPost argument
                HttpPost httpPost = new HttpPost("https://stackoverflow.com/");//()"http://192.168.43.217:8080/abc/abc.jsp/");

                // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
                //uniquely separate by the other end.
                //To achieve that we use BasicNameValuePair
                //Things we need to pass with the POST request
                BasicNameValuePair busNumberBasicNameValuePair = new BasicNameValuePair("paramBusNumber", paramBusNumber);
                //BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("paramPassword", paramPassword);

                // We add the content that we want to pass with the POST request to as name-value pairs
                //Now we put those sending details to an ArrayList with type safe of NameValuePair
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
                nameValuePairList.add(busNumberBasicNameValuePair);
                //nameValuePairList.add(passwordBasicNameValuePAir);

                try {
                    // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
                    //This is typically useful while sending an HTTP POST request.
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

                    // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
                    httpPost.setEntity(urlEncodedFormEntity);

                    try {
                        // HttpResponse is an interface just like HttpPost.
                        //Therefore we can't initialize them
                        HTTPResult.setText("result1");
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        HTTPResult.setText("result2");
                        // According to the JAVA API, InputStream constructor do nothing.
                        //So we can't initialize InputStream although it is not an interface
                        InputStream inputStream = httpResponse.getEntity().getContent();
                        HTTPResult.setText("result3");
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        HTTPResult.setText("result4");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        HTTPResult.setText("result5");
                        StringBuilder stringBuilder = new StringBuilder();
                        HTTPResult.setText("result6");
                        String bufferedStrChunk = null;

                        while((bufferedStrChunk = bufferedReader.readLine()) != null){
                            stringBuilder.append(bufferedStrChunk);
                        }


                        result = stringBuilder.toString();
                        HTTPResult.setText(result);



                    } catch (IOException ioe) {
                        //HTTPResult.setText("IOException");
                        System.out.println("Second Exception caz of HttpResponse :" + ioe);
                        ioe.printStackTrace();
                    }

                } catch (UnsupportedEncodingException uee) {
                    HTTPResult.setText("UnsupportedEncodingExceptionException");
                    System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
                    uee.printStackTrace();
                }*/
