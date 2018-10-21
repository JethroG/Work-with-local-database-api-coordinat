package com.energycompany.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.energycompany.sharedpreferences.Prefererenses;
import com.energycompany.R;
import com.energycompany.app.MyApplicationCustom;
import com.energycompany.gpslocation.GPSLocation;
import com.energycompany.network.Network;
import com.energycompany.workrwithCSV.ReadCSVFile;

import java.io.IOException;

import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooserDialog;

public class SingIn extends AppCompatActivity {
    Button singInButton;
    ProgressDialog loadingDialog;
    EditText loginn, passwordd;
   static Context context;
    static boolean enabled;
    private static final String DEFAULT_URI_DB = "/WaterTreatment/BD/database.csv";
    private static String URI_DB = DEFAULT_URI_DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sing_in);
        context = this;
        singInButton = (Button) findViewById(R.id.singinbutton);
        loginn = (EditText) findViewById(R.id.login1);
        passwordd = (EditText) findViewById(R.id.password1);

        Pair<String, String> pair = Prefererenses.getAuth(context);
        loginn.setText(pair.first);
        passwordd.setText(pair.second);

        singInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startLoginTask();
            }
        });
    }

    private void startLoginTask() {


        String login = loginn.getText().toString().trim();
        String pass = passwordd.getText().toString().trim();

        new LoginTask(context, login, pass).execute();
    }

    class LoginTask extends AsyncTask<Void, Void, String> {
        Context context;
        String login;
        String pass;

        public LoginTask(Context context, String login, String pass) {
            this.context = context;
            this.login = login;
            this.pass = pass;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(context, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            String resp = new Network().sendData(login, pass);

            if (resp.equals("200")) {
                try {
                    new ReadCSVFile(URI_DB);
                } catch (IOException ignored) {

                }
                if (!ReadCSVFile.empList.isEmpty()) {
                    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    resp = "201";
                }
                if (enabled) {
                    resp = "203";
                }
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String resp) {
            loadingDialog.dismiss();
            super.onPostExecute(resp);
            if (!resp.equals("203")) {
                String notify = "";
                switch (resp) {
                    case "200":
                        try {
                            showFileChooser();
                            Prefererenses.setAuth(context, new Pair<String, String>(login, pass));
                        } catch (ExternalStorageNotAvailableException e) {
                            showDialog("Проверьте наличие файла с клиентской базой");
                        }
                        break;
                    case "201":
                        showDialog("Включите на телефоне функцию определение местоположения");
                        break;
                    default:
                        showDialog("Логин, пароль введены неверно.Проверьте соединение с интернетом");
                        break;
                }

            } else {
                MyApplicationCustom.login = login;
                Intent intent = new Intent(context, GPSLocation.class);
                startService(intent);
                Intent nextactivity = new Intent(SingIn.this, ThisIsMainActivity.class);
                startActivity(nextactivity);
                finish();
            }
        }
        private void showDialog(String notify) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SingIn.this);
            builder.setTitle("Ошибка!")
                    .setMessage(notify)
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void showFileChooser() throws ExternalStorageNotAvailableException {
        FileChooserDialog dialog = new FileChooserDialog.Builder(FileChooserDialog.ChooserType.FILE_CHOOSER, new FileChooserDialog.ChooserListener() {
            @Override
            public void onSelect(String path) {
                URI_DB = path;

                startLoginTask();
            }
        }).build();
        dialog.show(getSupportFragmentManager(), null);
    }
}
