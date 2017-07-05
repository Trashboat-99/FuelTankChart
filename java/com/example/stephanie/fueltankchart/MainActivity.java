package com.example.stephanie.fueltankchart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private TextView amountToFill;
    private TextView tankDepthTxt;
    private SeekBar seekBar;
    private double value = 40;
    //private TextView total;
    private Button searchBtn;
    private RadioButton rb1;
    private RadioButton rb2;
    private Spinner spinnerTankCap;
    private String selectedTankDiameter;
    private DataAdapter data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to show the icon in the Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        data = new DataAdapter(this);
        data.createDatabase(); //build the database
        //set up Spinner to string array values
        TextView tankCapTxt = (TextView) findViewById(R.id.tankSizeText);
        spinnerTankCap = (Spinner) findViewById(R.id.tankSize);


        //coding the radio group and radio buttons
        final TextView diameterText = (TextView) findViewById(R.id.widthTxt);
        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
        rb1 = (RadioButton) findViewById(R.id.width1);
        rb2 = (RadioButton) findViewById(R.id.width2);
        //this on should be selected by default
        if (rb1.isSelected()) {
            //if the first rb is selected then set the spinner adapter to tank size 1
            //create and ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tankCapValues1, android.R.layout.simple_spinner_item);
            //Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //apply adapter to the spinner
            spinnerTankCap.setAdapter(adapter);
            selectedTankDiameter = rb1.getText().toString();
        } else if (rb2.isSelected()) {
            //if the second rb is selected then set the spinner adapter to tank size 2
            //create and ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tankCapValues2, android.R.layout.simple_spinner_item);
            //Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //apply adapter to the spinner
            spinnerTankCap.setAdapter(adapter);
            //edit this so that when the app starts this happens first
        } else {
            //output a toast pop-up message. context, text, duration of message
            Toast.makeText(MainActivity.this, "Please select a tank width before proceeding", Toast.LENGTH_LONG).show();
            //by default set to rb1's array addapter values
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tankCapValues1, android.R.layout.simple_spinner_item);
            //Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //apply adapter to the spinner
            spinnerTankCap.setAdapter(adapter);
        }
        SeekBarListener();
        //code the calculate button
        searchBtn = (Button)findViewById(R.id.calculatebtn);
        searchBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                amountToFill = (TextView)findViewById(R.id.total);
                data.open(); //open the database
                String result = search();
                amountToFill.setText(result);
                data.close(); //close the database when done

            }
        });


    }//end of on Create method

    public void SeekBarListener(){
        //cast private variables
        //code the textview for the seekbar
        tankDepthTxt = (TextView) findViewById(R.id.tankDepthText);
        seekBar = (SeekBar) findViewById(R.id.tankDepth);
        //set the text of the text view
        tankDepthTxt.setText(seekBar.getProgress()+" / "+seekBar.getMax());

        //now add listener
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                //asign value to textview also
                tankDepthTxt.setText(progress+" / "+seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tankDepthTxt.setText(progress_value+" / "+seekBar.getMax());
            }
        });
    }
    //query the database
    public String search (){
        //pass the tank diameter, tank capacity, inches of fuel from the view to the adapter
        String value = data.findValue(rb1.getText().toString(),spinnerTankCap.getSelectedItem().toString(),seekBar.getProgress());

        return value;
    }
}
