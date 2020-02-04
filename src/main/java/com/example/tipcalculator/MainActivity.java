package com.example.tipcalculator;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private TextView percent;
    private TextView total;
    private TextView perPerson;

    private EditText billAmount;
    private SeekBar percentSeekbar;
    private EditText tip;
    private RadioGroup rg;

    private RadioButton none;
    private RadioButton radioTip;
    private RadioButton radioTotal;

    private Spinner spinner;
    double amt, billAmt, tipAmt, finalAmt = 0.0;
    NumberFormat nf = NumberFormat.getNumberInstance();
    private static final String TAG = "MainActivity";
    private boolean isTip = false;
    private boolean isTotal = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        percent = findViewById(R.id.textView3);
        total = findViewById(R.id.textViewFinal);
        perPerson = findViewById(R.id.textView15);
        billAmount = findViewById(R.id.editText2);
        percentSeekbar = findViewById(R.id.seekBar);
        tip = findViewById(R.id.editTextTip);
        rg = findViewById(R.id.radioGroup);
        none = findViewById(R.id.radioButtonNone);
        radioTip = findViewById(R.id.radioButtonTip);
        radioTotal = findViewById(R.id.radioButtonTotal);
        spinner = findViewById(R.id.spinner);
        ArrayList<String> spinnerList = new ArrayList<>();

        spinnerList.add("None");
        spinnerList.add("2 Person");
        spinnerList.add("3 Person");
        spinnerList.add("4 Person");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerList);


        percentSeekbar.setOnSeekBarChangeListener(MainActivity.this);
        rg.setOnCheckedChangeListener(this);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButtonTip:
                isTip = true;
                calculateAndDisplay();
                isTotal = false;
                break;

            case R.id.radioButtonTotal:
                isTotal = true;
                calculateAndDisplay();
                isTip = false;
                break;

            default:
                isTotal = false;
                isTip = false;
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getItemAtPosition(position).toString()) {
            case "2 Person":
                perPerson.setText(amt / 2 + "");
                break;
            case "3 Person":
                perPerson.setText(amt / 3 + "");
                break;
            case "4 Person":
                perPerson.setText(amt / 4 + "");
                break;
            default:
                if(total.getText().toString().length()!=0){
                    perPerson.setText(amt+"");
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        calculateAndDisplay();
        percent.setText(progress + "%");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void calculateAndDisplay() {
        if (billAmount.getText().toString().length() != 0) {
            billAmt = Double.parseDouble(billAmount.getText().toString());
        }
        tipAmt = Double.valueOf(percentSeekbar.getProgress());
        tipAmt = billAmt * (tipAmt / 100);
        nf.setMaximumFractionDigits(3);
        amt = billAmt + tipAmt;

        if (isTip) {
            tipAmt = Math.round(tipAmt);
        }
        if (isTotal) {
            amt = Math.round(amt);
        }

        tip.setText(nf.format(tipAmt) + "");
        total.setText(nf.format(amt) + "");
    }
}
