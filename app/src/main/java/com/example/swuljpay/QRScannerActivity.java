package com.example.swuljpay;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_q_r_scanner);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();

    }
    @Override
    public void handleResult(Result result) {
        Intent intent = new Intent();
        //intent.putExtra(AppConstants.KEY_QR_CODE, result.getText());
        setResult(RESULT_OK, intent);
        finish();
        //String res = result.getText();
    }
}