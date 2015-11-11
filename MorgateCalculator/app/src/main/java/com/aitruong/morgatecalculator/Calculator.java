package com.aitruong.morgatecalculator;

import android.util.Log;

/**
 * Created by mina on 11/3/15.
 */
public class Calculator {
    private double mHomeValue;
    private double mDowPayment;
    private double mInterestRate;
    private double mTerm;
    private double mPropertyTaxRate;

    public Calculator (double aHomeValue,double aDowpayment,double anInterestRate,double aTerm,double aPropertyTaxRate){
        mHomeValue = aHomeValue;
        mDowPayment = aDowpayment;
        mInterestRate = anInterestRate;
        mTerm = aTerm;
        mPropertyTaxRate = aPropertyTaxRate;
    }
    public Calculator(){

    }

    public void init(double aHomeValue,double aDowpayment,double anInterestRate,double aTerm,double aPropertyTaxRate){
        mHomeValue = aHomeValue;
        mDowPayment = aDowpayment;
        mInterestRate = anInterestRate;
        mTerm = aTerm;
        mPropertyTaxRate = aPropertyTaxRate;
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

    public double calculateNumPayment(){
        return  mTerm * 12;
    }

    public double calculateMonthlyPayment(){
        double mMoneyPayment;
        mMoneyPayment = (mHomeValue - mDowPayment) *((mInterestRate*(Math.pow((1 + mInterestRate), calculateNumPayment())))/
                (Math.pow((1 + mInterestRate), calculateNumPayment())-1));
        Log.v("debug","debug:" + (Math.pow((1 + mInterestRate), calculateNumPayment())));
        mMoneyPayment += calculateTotalTaxPaid() / calculateNumPayment();
        return mMoneyPayment;
    }

    public double calculateTotalPaid(){
        return calculateMonthlyPayment() * calculateNumPayment();
    }

    public double calculateTotalInterestPaid(){
        return calculateTotalPaid() - calculateTotalTaxPaid() - (mHomeValue - mDowPayment);
    }

    public double calculateTotalTaxPaid(){
        return mHomeValue * mPropertyTaxRate * calculateNumPayment();
    }

}
