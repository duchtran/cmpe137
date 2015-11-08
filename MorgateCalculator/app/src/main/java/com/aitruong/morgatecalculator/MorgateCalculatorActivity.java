package com.aitruong.morgatecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MorgateCalculatorActivity extends AppCompatActivity {
    private EditText mHomeValueEditText;
    private EditText mDowPaymentEditText;
    private EditText mInterestRateEditText;
    private Spinner mTermEditText;
    private EditText mPropertyTaxRate;
    private Button mResetButton;
    private Button mCalculatorButton;
    private EditText mMoneyPaymentEditText;
    private Calculator mCalculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morgate_calculator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Calculator  mCalcuator = new Calculator();



        mHomeValueEditText = (EditText) findViewById(R.id.home_value_edit_text);
        mDowPaymentEditText = (EditText) findViewById(R.id.down_payment_edit_text);
        mInterestRateEditText = (EditText) findViewById(R.id.interest_rate_edit_text);
        mPropertyTaxRate = (EditText) findViewById(R.id.property_tax_rate_edit_text);
        mTermEditText = (Spinner) findViewById(R.id.term_edit_text);
        String [] items = new String []{"Choose Term in Years","15", "20", "30"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        mTermEditText.setAdapter(adapter);
//        mTermEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String term = mTermEditText.getItemAtPosition(1).toString();
//            }
//        });
        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clear();
            }
        });

        mMoneyPaymentEditText =(EditText)findViewById(R.id.money_payment_amount_edit_text);
        mCalculatorButton = (Button)findViewById(R.id.calculator_button);
        mCalculatorButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               String homeValueString= mHomeValueEditText.getText().toString();
               double homeValueDouble = Double.parseDouble(homeValueString) ;
               String downPaymentString = mDowPaymentEditText.getText().toString();
               double downPaymentDouble = Double.parseDouble(downPaymentString);
               String interestRateString = mInterestRateEditText.getText().toString();
               double interestRateDouble = Double.parseDouble(interestRateString);




            }
        });

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        double itemDouble = Double.parseDouble(item);
    }
    //Reset text

    public void clear(){

        mHomeValueEditText.setText("");
        mDowPaymentEditText.setText("");
        mPropertyTaxRate.setText("");
        mInterestRateEditText.setText("");

      }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_morgate_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
