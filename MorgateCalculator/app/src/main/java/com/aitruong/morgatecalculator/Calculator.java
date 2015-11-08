package com.aitruong.morgatecalculator;

/**
 * Created by mina on 11/3/15.
 */
public class Calculator {
    private double mHomeValue;
    private double mDowPayment;
    private double mInterestRate;
    private double mTerm;
    private double mPropertyTaxRate;
    private double mMoneyPayment;

    public Calculator (double aHomeValue,double aDowpayment,double anInterestRate,double aTerm,double aPropertyTaxRate){
        mHomeValue = aHomeValue;
        mDowPayment = aDowpayment;
        mInterestRate = anInterestRate;
        mTerm = aTerm;
        mPropertyTaxRate = aPropertyTaxRate;



    }
    public Calculator(){

    }

    public double getHomeValue() {
        return mHomeValue;
    }

    public void setHomeValue(double homeValue) {
        mHomeValue = homeValue;
    }

    public double getDowPayment() {
        return mDowPayment;
    }

    public void setDowPayment(double dowPayment) {
        mDowPayment = dowPayment;
    }

    public double getInterestRate() {
        return mInterestRate;
    }

    public void setInterestRate(double interestRate) {
        mInterestRate = interestRate;
    }

    public double getTerm() {
        return mTerm;
    }

    public void setTerm(int term) {
        mTerm = term;
    }

    public double getPropertyTaxRate() {
        return mPropertyTaxRate;
    }

    public void setPropertyTaxRate(double propertyTaxRate) {
        mPropertyTaxRate = propertyTaxRate;
    }

    public double calculateMonthlyPayment(){
    double numPayment = mTerm * 12;
       mMoneyPayment =mHomeValue *((mInterestRate*(Math.pow((1 + mInterestRate), numPayment)))/(Math.pow((1 + mInterestRate), numPayment))-1);
     return mMoneyPayment;
    }


}
