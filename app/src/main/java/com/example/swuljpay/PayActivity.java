package com.example.swuljpay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PayActivity extends AppCompatActivity {
    String BUS_NUMBER = "EXTRA_BUS_NUMBER";
    String Id = "ID";
    String Source = "SOURCE";
    String Destination = "DESTINATION";
    String Fare = "FARE";
    String url = "https://swulj.000webhostapp.com/bus_payment.php";
    Context mContext = this;
    String filename = "tickets";
    static final int REQUEST = 112;
    String TICKETS_DIRECTORY = "tickets";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        Intent i = getIntent();
        PayData datum = new PayData(url,
                i.getStringExtra(Fare),
                i.getStringExtra(BUS_NUMBER),
                i.getStringExtra(Source),
                i.getStringExtra(Destination),
                i.getStringExtra(Id));
        HTTPConnection1 conn = new HTTPConnection1();
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!hasPermissions(mContext, PERMISSIONS)) {
                ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST);
                conn.execute(datum);
            } else {
                //do here
                conn.execute(datum);

            }
        } else {
            //do here
            conn.execute(datum);

        }

    }

    class HTTPConnection1 extends AsyncTask<PayData, Void, Void> {
        String result;
        String url;
        String busNumber;
        String ID;
        String Source;
        String Destination;
        String fare;

        @Override
        protected Void doInBackground(PayData... params) {
            PayData data = params[0];
            url = data.url;
            busNumber = data.busNumber;
            ID = data.Id;
            Source = data.Source;
            Destination = data.Destination;
            fare = data.fare;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
                nameValuePairs.add(new BasicNameValuePair("busNumber", busNumber));
                nameValuePairs.add(new BasicNameValuePair("ID", ID));
                nameValuePairs.add(new BasicNameValuePair("Source", Source));
                nameValuePairs.add(new BasicNameValuePair("Destination", Destination));
                nameValuePairs.add(new BasicNameValuePair("FARE", fare));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try {
                        InputStream inputStream = entity.getContent();
                        //set the path where we want to save the file
                        //in this case, going to save it on the root directory of the
                        //sd card.
                        File wallpaperDirectory = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), TICKETS_DIRECTORY);
                        // have the object build the directory structure, if needed.

                        if (!wallpaperDirectory.exists()) {
                            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
                            wallpaperDirectory.mkdirs();
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());

                            File f = new File(wallpaperDirectory,currentDateandTime + ".pdf");




                         /*   File SDCardRoot = Environment.getExternalStorageDirectory();
                        //create a new file, specifying the path, and the filename
                        //which we want to save the file as.
                        File file = new File(SDCardRoot, filename);*/

                        //this will be used to write the downloaded data into the file we created
                        FileOutputStream fileOutput = new FileOutputStream(f);

                        //create a buffer...
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0; //used to store a temporary size of the buffer

                        //now, read through the input buffer and write the contents to the file
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            //add the data in the buffer to the file in the file output stream (the file on the sd card
                            fileOutput.write(buffer, 0, bufferLength);
                        }
                        //close the output stream when done
                        fileOutput.close();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    return null;
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                    Toast.makeText(mContext, "The app was  allowed to read your store.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}