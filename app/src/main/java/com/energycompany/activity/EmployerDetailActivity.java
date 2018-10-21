package com.energycompany.activity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.energycompany.R;
import com.energycompany.calendar.DatePickerFragment;
import com.energycompany.workrwithCSV.Employee;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.energycompany.photodecod.ImageUtil.convert;

public class EmployerDetailActivity extends AppCompatActivity {
    private static int TAKE_PICTURE = 1;
    ImageView cameraOnClick;
    Bitmap thumbnailBitmap;
    Boolean resultphoto = false;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_detail);
        getSupportActionBar().setTitle("Информация");
        final Employee e = (Employee) getIntent().getSerializableExtra("e");
        TextView employer_detail_score = (TextView) findViewById(R.id.faceinformationcsore);
        TextView employer_detail_fio = (TextView) findViewById(R.id.fioinformation);
        TextView employer_detail_street = (TextView) findViewById(R.id.adressinformation);
        Button sendfile = (Button) findViewById(R.id.sendfilebutton);
        final TextView serionnumber = (TextView) findViewById(R.id.serionnumber);
        final TextView serionnumber1 = (TextView) findViewById(R.id.serionnumber1);
        TextView oldnumber = (TextView) findViewById(R.id.oldnumber);
        final TextView newpokaznyk = (TextView) findViewById(R.id.newpokaznyk);
        TextView dataproverki = (TextView) findViewById(R.id.dataproverki);
        TextView oldnumber1 = (TextView) findViewById(R.id.oldnumber1);
        TextView typeplomba = (TextView) findViewById(R.id.typeOfSeal);
        TextView locationinhouse = (TextView) findViewById(R.id.locationinhouse);
        TextView modelindicator = (TextView) findViewById(R.id.modelindicator);
        TextView datapokazanii = (TextView) findViewById(R.id.datapokazanii);
        TextView numberplamba = (TextView) findViewById(R.id.sealnumber);
        TextView saldo = (TextView) findViewById(R.id.balance);
        cameraOnClick = (ImageView) findViewById(R.id.cameraOnclick);
        employer_detail_score.setText("Лицевой счет- " + e.getPersonalAccount());
        employer_detail_fio.setText("Ф.И.О- " + e.getNameFirstNameSecondName());
        employer_detail_street.setText("Улица- " + e.getStreet() + " " + e.getNumberhouse() + "  кв." + e.getNumberpartment());
        serionnumber.setText(e.getNumberMeter());
        serionnumber1.setText(e.getNumberMeter());
        saldo.setText("Сальдо: " + e.getBalance());
        datapokazanii.setText(e.getYearOfVerification());
        dataproverki.setText(e.getValidUntil());
        locationinhouse.setText(e.getDisposition());
        typeplomba.setText(e.getTypeOfSeal());
        oldnumber.setText(e.getPreviousIndicators());
        oldnumber1.setText(e.getPreviousIndicators());
        modelindicator.setText(e.getTypeOfMeter());
        numberplamba.setText(e.getSealnumber());
        final FragmentManager fm = this.getFragmentManager();
        Fragment informationpersonabout = fm.findFragmentById(R.id.informationpersonabout);
        Fragment otherinfotmation = fm.findFragmentById(R.id.elseinformationperson);
        final Fragment entersendfile = fm.findFragmentById(R.id.entersendfile);
        Button sendfileontext = (Button) findViewById(R.id.sendfileontext);
        fm.beginTransaction()
                .hide(informationpersonabout)
                .commit();
        fm.beginTransaction()
                .hide(otherinfotmation)
                .commit();
        fm.beginTransaction()
                .hide(entersendfile)
                .commit();
        addShowHideListener(R.id.informationperson, R.id.imageView3, informationpersonabout);
        addShowHideListener(R.id.otherinfotmation, R.id.imageView4, otherinfotmation);
        addShowHideListener(R.id.senddeteilfor, R.id.imageView5, entersendfile);
        //Start camera onClick
        cameraOnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(captureIntent, TAKE_PICTURE);
                } catch (ActivityNotFoundException e) {
                    // Error
                    String errorMessage = "Ваше устройство не поддерживает съемку";
                    Toast toast = Toast.makeText(getApplication(), errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        sendfileontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serionnumber = e.getNumberMeter();
                String oldnumber1 = e.getPreviousIndicators();
                String newpokaz = newpokaznyk.getText().toString();
                final String FILE_HEADER = "-------;-------;--------;Дата проверки;Фото;";
                String h = DateFormat.format("dd-MM-yyyyy", System.currentTimeMillis()).toString();
                File f = new File(Environment.getExternalStorageDirectory(), "/WaterTreatment/indicators/" + h + ".csv");
                FileWriter writer = null;
                try {
                    writer = new FileWriter(f, true);
                    if ( newpokaznyk.length() == 0&& resultphoto) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Сделайте фото счетчика или внесите новые данные", Toast.LENGTH_SHORT);
                        toast.show();
                    } else if (f.length() == 0) {
                        writer.write(FILE_HEADER);
                    }
                    String personaldata = "\n" + serionnumber + ";" + oldnumber1 + ";" + newpokaz + ";"
                            + h + ";" + convert(thumbnailBitmap);
                    writer.write(personaldata);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                } finally {
                    try {
                        resultphoto=false;
                        writer.close();
                    } catch (IOException e1) {
                  //      e1.printStackTrace();
                        Toast toast = Toast.makeText(getApplicationContext(), "Произошла ошибка при отправке файла, проверьте наличие всех данных для отправки", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Данные занесены в таблицу", Toast.LENGTH_SHORT);
                toast.show();
                cameraOnClick.setImageResource(R.drawable.ic_add);
                fm.beginTransaction()
                        .hide(entersendfile)
                        .commit();
                newpokaznyk.setText("");
            }
        });
        //sendfile
        sendfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String h = DateFormat.format("dd-MM-yyyyy", System.currentTimeMillis()).toString();
                String filename = "/WaterTreatment/indicators/" + h + ".csv";
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                Uri path = Uri.fromFile(filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                // set the type to 'email'
                emailIntent.setType("check/email");
                String to[] = {"email-your@gmail.com"};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                // the attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                // the mail subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Data per day");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE) {
            // Проверяем, содержит ли результат маленькую картинку
            if (data != null) {
                if (data.hasExtra("data")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Фото добавлено", Toast.LENGTH_SHORT);
                    toast.show();
                    resultphoto = true;
                    thumbnailBitmap = data.getParcelableExtra("data");
                    cameraOnClick.setImageBitmap(thumbnailBitmap);
                }
            }
        }
    }
    // fragment animation
    void addShowHideListener(int buttonId, int imageViewId, final Fragment fragment) {
        View layout = findViewById(buttonId);
        final ImageView imageView = (ImageView) findViewById(imageViewId);
        layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);
                if (fragment.isHidden()) {
                    ft.show(fragment);
                    imageView.setImageResource(R.drawable.ic_arrow_drop_up);
                } else {
                    ft.hide(fragment);
                    imageView.setImageResource(R.drawable.ic_arrow_drop_down);
                }
                ft.commit();
            }
        });
    }
    //for calendar
    public void onEditClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "Date Picker");
    }

}
