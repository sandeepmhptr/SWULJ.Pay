package com.example.swuljpay;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SelectStopsActivity extends AppCompatActivity {
    String BUS_NUMBER= "EXTRA_BUS_NUMBER";
    String Param = "PARAM";
    String Stop = "STOP";
    String Id = "ID";
    String Source = "SOURCE";
    int count_stops = 0;
    String busNumber;
    String Stops[];
    Button next_page;
    TextView id_txt;
    EditText id_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_stops);
        next_page = (Button) findViewById(R.id.btn1);
        id_input = (EditText) findViewById(R.id.ID_input) ;
        id_txt = (TextView) findViewById(R.id.id_txt) ;

        Intent i = getIntent();

        busNumber = i.getStringExtra(BUS_NUMBER);
        String temp = i.getStringExtra(Param);
        count_stops = Integer.valueOf(temp);

        Stops = new String[count_stops];

        for(int x = 1; x <= count_stops; x++){
            String temp2 = Stop + String.valueOf(x);
            Stops[x-1] = i.getStringExtra(temp2);
        }


        // get reference to radio group in layout
        RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        // layout params to use when adding each radio button
        LinearLayout.LayoutParams layoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT);
        // add 20 radio buttons to the group
        for (int u = 0; u < count_stops; u++){
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

                String ID = (String) id_input.getText().toString();
                id_input.setText(String.valueOf(a));
                if(a >= 0 && ID.length()>2)
                {
                    Intent inte = new Intent(SelectStopsActivity.this,
                            DestinationStopActivity.class);
                    inte.putExtra(BUS_NUMBER, busNumber);
                    inte.putExtra(Param,String.valueOf(count_stops));
                    for(int u = 1; u <= count_stops; u++){
                        String temp = Stop + String.valueOf(u);
                        inte.putExtra(temp, Stops[u-1]);
                    }
                    inte.putExtra(Id,ID);
                    inte.putExtra(Source, Stops[a]);
                    startActivity(inte);
                    //invoke the SecondActivity.

                    finish();
                }
            }
        });
    }
}