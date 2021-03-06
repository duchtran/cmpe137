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
import android.util.Log;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;

public class MorgateCalculatorActivity extends AppCompatActivity {
    private EditText mHomeValueEditText;
    private EditText mDowPaymentEditText;
    private EditText mInterestRateEditText;
    private Spinner mTermEditText;
    private EditText mPropertyTaxRate;
    private Button mResetButton;
    private Button mCalculatorButton;
    private EditText mMoneyPaymentEditText;
    private EditText mInterestPaid;
    private EditText mTaxPaid;
    private EditText mPayOffDate;
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
        mMoneyPaymentEditText = (EditText) findViewById(R.id.money_payment_amount_edit_text);
        mInterestPaid = (EditText) findViewById(R.id.interest_paid_edit_text);
        mTaxPaid = (EditText) findViewById(R.id.tax_paid_edit_text);
        mPayOffDate = (EditText) findViewById(R.id.pay_off_date_edit_text);

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
                if("".equals(mHomeValueEditText.getText().toString())
                        |"".equals( mDowPaymentEditText.getText().toString())
                        |"".equals(mInterestRateEditText.getText().toString())
                        |"".equals(mPropertyTaxRate.getText().toString())) {
                    mMoneyPaymentEditText.setText("Missing Entries");
                } else {
                    String homeValueString = mHomeValueEditText.getText().toString();
                    double homeValueDouble = Double.parseDouble(homeValueString);
                    String downPaymentString = mDowPaymentEditText.getText().toString();
                    double downPaymentDouble = Double.parseDouble(downPaymentString);
                    String interestRateString = mInterestRateEditText.getText().toString();
                    double interestRateDouble = Double.parseDouble(interestRateString);
                    String propertyTaxRateString = mPropertyTaxRate.getText().toString();
                    double propertyTaxRateDouble = Double.parseDouble(propertyTaxRateString);
                    double term;

                    switch (mTermEditText.getSelectedItem().toString()) {
                        case "15":
                            term = 15;
                            break;
                        case "20":
                            term = 20;
                            break;
                        case "30":
                            term = 30;
                            break;
                        default:
                            term = 0;
                            break;
                    }
                    Calendar c = Calendar.getInstance();
                    int payYear = c.get(Calendar.YEAR) + (int) term;
                    String payMonth = getMonthShortName(c.get(Calendar.MONTH));
                    //Log.v("debug",homeValueDouble + "\n" + downPaymentDouble + "\n" + interestRateDouble + "\n" + term + "\n" + propertyTaxRateDouble);

                    mCalcuator.init(homeValueDouble, downPaymentDouble, interestRateDouble / 100 / 12, term, propertyTaxRateDouble / 100 / 12);
                    if (errorHandler(mCalcuator.calculateTotalInterestPaid(), mCalcuator.calculateMonthlyPayment())) {
                        mMoneyPaymentEditText.setText(String.format("%.2f", mCalcuator.calculateMonthlyPayment()));
                        mInterestPaid.setText(String.format("%.2f", mCalcuator.calculateTotalInterestPaid()));
                        mTaxPaid.setText(String.format("%.2f", mCalcuator.calculateTotalTaxPaid()));
                        mPayOffDate.setText(String.valueOf(payMonth + " " + payYear));
                    } else {
                        mMoneyPaymentEditText.setText("Invalid Entries");
                    }
                }
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
        mMoneyPaymentEditText.setText("");
        mInterestPaid.setText("");
        mTaxPaid.setText("");
        mPayOffDate.setText("");
      }


    public static String getMonthShortName(int monthNumber)
    {
        String monthName="";

        if(monthNumber>=0 && monthNumber<12)
            try
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, monthNumber);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM");
                simpleDateFormat.setCalendar(calendar);
                monthName = simpleDateFormat.format(calendar.getTime());
            }
            catch (Exception e)
            {
                if(e!=null)
                    e.printStackTrace();
            }
        return monthName;
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

    public boolean errorHandler(double tempTotalInterestPaid, double tempcalculateMonthlyPayment) {
        if ((String.valueOf(tempTotalInterestPaid).equals("NaN")) | (String.valueOf(tempcalculateMonthlyPayment).equals("NaN"))) {
            return false;
        } else {
            return true;
        }
    }

}
