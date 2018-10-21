package com.energycompany.workrwithCSV;


import java.io.Serializable;

public class Employee implements Serializable{
    private String personalAccount;
    private String nameFirstNameSecondName;
    private String street;
    private String numberhouse;
    private String numberpartment;
    private String phonenumber;
    private String balance;
    private String sealnumber;
    private String numberMeter;
    private String typeOfSeal;
    private String disposition;
    private String typeOfMeter;
    private String yearOfVerification;
    private String validUntil;
    private String previousIndicators;
    private String actualIndicators;
    private String city;
    @Override
    public String toString() {
        return "Employee{" +
                "personalAccount='" + personalAccount + '\'' +
                ", nameFirstNameSecondName='" + nameFirstNameSecondName + '\'' +
                ", street='" + street + '\'' +
                ", numberhouse='" + numberhouse + '\'' +
                ", numberpartment='" + numberpartment + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", balance='" + balance + '\'' +
                ", sealnumber='" + sealnumber + '\'' +
                ", numberMeter='" + numberMeter + '\'' +
                ", typeOfSeal='" + typeOfSeal + '\'' +
                ", disposition='" + disposition + '\'' +
                ", typeOfMeter='" + typeOfMeter + '\'' +
                ", yearOfVerification='" + yearOfVerification + '\'' +
                ", validUntil='" + validUntil + '\'' +
                ", previousIndicators='" + previousIndicators + '\'' +
                ", actualIndicators='" + actualIndicators + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }

    public String getNameFirstNameSecondName() {
        return nameFirstNameSecondName;
    }

    public void setNameFirstNameSecondName(String nameFirstNameSecondName) {
        this.nameFirstNameSecondName = nameFirstNameSecondName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getNumberMeter() {
        return numberMeter;
    }

    public void setNumberMeter(String numberMeter) {
        this.numberMeter = numberMeter;
    }

    public String getTypeOfMeter() {
        return typeOfMeter;
    }

    public void setTypeOfMeter(String typeOfMeter) {
        this.typeOfMeter = typeOfMeter;
    }

    public String getYearOfVerification() {
        return yearOfVerification;
    }

    public void setYearOfVerification(String yearOfVerification) {
        this.yearOfVerification = yearOfVerification;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }

    public String getPreviousIndicators() {
        return previousIndicators;
    }

    public void setPreviousIndicators(String previousIndicators) {
        this.previousIndicators = previousIndicators;
    }

    public String getActualIndicators() {
        return actualIndicators;
    }

    public void setActualIndicators(String actualIndicators) {
        this.actualIndicators = actualIndicators;
    }

    public String getSealnumber() {
        return sealnumber;
    }

    public void setSealnumber(String sealnumber) {
        this.sealnumber = sealnumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumberhouse() {
        return numberhouse;
    }

    public void setNumberhouse(String numberhouse) {
        this.numberhouse = numberhouse;
    }

    public String getNumberpartment() {
        return numberpartment;
    }
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTypeOfSeal() {
        return typeOfSeal;
    }

    public void setTypeOfSeal(String typeOfSeal) {
        this.typeOfSeal = typeOfSeal;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }


    public void setNumberpartment(String numberpartment) {
        this.numberpartment = numberpartment;
    }

}
