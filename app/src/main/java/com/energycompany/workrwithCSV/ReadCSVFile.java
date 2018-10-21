package com.energycompany.workrwithCSV;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCSVFile {
    public static List<Employee> empList = new ArrayList<>();
    public static List<String> citylist = new ArrayList<>();
    public static List<String> streetlist = new ArrayList<>();
    Context mContext;

    public ReadCSVFile(String URI) throws IOException {
        BufferedReader reader = null;

        String line = "";
        String splitBy = ";";
        String fileInAndroidDerectory = URI;
        try {

            FileInputStream fileInputStream = new FileInputStream(fileInAndroidDerectory);
            reader = new BufferedReader(
                    new InputStreamReader(fileInputStream,"windows-1251"));

            while ((line = reader.readLine()) != null) {
                // split on comma(';')
                String[] listforeverycoloum =line.split(splitBy);
                // create  object to store values
                Employee mainObjects = new Employee();
                // add values from csv to object
                mainObjects.setPersonalAccount(listforeverycoloum[0]);
                mainObjects.setNameFirstNameSecondName(listforeverycoloum[1]);
                mainObjects.setStreet(listforeverycoloum[2]);
                mainObjects.setNumberhouse(listforeverycoloum[3]);
                mainObjects.setNumberpartment(listforeverycoloum[4]);
                mainObjects.setPhonenumber(listforeverycoloum[5]);
                mainObjects.setBalance(listforeverycoloum[6]);
                mainObjects.setSealnumber(listforeverycoloum[7]);
                mainObjects.setNumberMeter(listforeverycoloum[8]);
                mainObjects.setTypeOfSeal(listforeverycoloum[9]);
                mainObjects.setDisposition(listforeverycoloum[10]);
                mainObjects.setTypeOfMeter(listforeverycoloum[11]);
                mainObjects.setYearOfVerification(listforeverycoloum[12]);
                mainObjects.setValidUntil(listforeverycoloum[13]);
                mainObjects.setPreviousIndicators(listforeverycoloum[14]);
                mainObjects.setActualIndicators(listforeverycoloum[15]);
                mainObjects.setCity(listforeverycoloum[16]);
                // adding objects to a list
                empList.add(mainObjects);
                citylist.add(listforeverycoloum[16]);
                streetlist.add(listforeverycoloum[2]);
                streetlist.remove("Адрес");
                citylist.remove("Город");
            }
        } catch (IOException e) {
            //e.printStackTrace();
//            Toast toast = Toast.makeText(mContext, "Перезапустите программу произошла ошибка", Toast.LENGTH_SHORT);
//            toast.show();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Toast toast = Toast.makeText(mContext, "Перезапустите программу произошла ошибка", Toast.LENGTH_SHORT);
                    toast.show();
                   // e.printStackTrace();
                }
            }

        }
    }


}





