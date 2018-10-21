package com.energycompany.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.energycompany.R;
import com.energycompany.workrwithCSV.Employee;
import com.energycompany.workrwithCSV.ReadCSVFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.energycompany.workrwithCSV.ReadCSVFile.citylist;
import static com.energycompany.workrwithCSV.ReadCSVFile.streetlist;
public class ThisIsMainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText enterfacescore,  enterhouse, enterpartment;
    Button searchButton,exitButton;
    Context context;
    public static List<Employee> empList = new ArrayList<>();
    Spinner spinnercitylist, spinnerstreetlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Поиск лицевого счёта");
        context = this;
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_this_is_main);
        searchButton = (Button) findViewById(R.id.buttonsearch);
        exitButton = (Button) findViewById(R.id.exitbutton);
        enterfacescore = (EditText) findViewById(R.id.enterfacescore);
        enterhouse = (EditText) findViewById(R.id.enterhouse);
        enterpartment = (EditText) findViewById(R.id.enterpartment);
        spinnercitylist = (Spinner) findViewById(R.id.spinnercity);
        spinnerstreetlist = (Spinner) findViewById(R.id.spinnerstreet);
        final FragmentManager fm = this.getFragmentManager();
        final Fragment adressstreethousepartnemt = fm.findFragmentById(R.id.adressstreethousepartnemt);
        final Fragment facescore = fm.findFragmentById(R.id.facescore);
        fm.beginTransaction()
                .hide(facescore)
                .commit();
        fm.beginTransaction()
                .hide(adressstreethousepartnemt)
                .commit();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selState = (String) spinnercitylist.getSelectedItem();
                String selState1 = (String) spinnerstreetlist.getSelectedItem();
                if (!adressstreethousepartnemt.isHidden() && facescore.isHidden()  ) {
                    if (enterhouse.length()==0){
                        Toast toast = Toast.makeText(getApplication(), "Введите дом", Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        new SearchTask(selState, selState1, enterhouse.getText().toString(),
                                enterpartment.getText().toString()).execute();
                        fm.beginTransaction()
                                .hide(adressstreethousepartnemt)
                                .commit();
                         enterhouse.setText("");
                         enterpartment.setText("");
                    }
                }
                if (!facescore.isHidden() && adressstreethousepartnemt.isHidden()){
                    if (enterfacescore.length()==0){
                        Toast toast = Toast.makeText(getApplication(), "Введите лицевой счет", Toast.LENGTH_SHORT);
                        toast.show();
                    }else {
                        new SearchTask(enterfacescore.getText().toString()).execute();
                        fm.beginTransaction()
                                .hide(facescore)
                                .commit();
                        enterfacescore.setText("");
                    }
                }
                if ((facescore.isHidden() && adressstreethousepartnemt.isHidden()) ) {
                    Toast toast = Toast.makeText(getApplication(), "Откройте меню поиска по любому параметру", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    if (!facescore.isHidden() && !adressstreethousepartnemt.isHidden()){
                        Toast toast = Toast.makeText(getApplication(), "Поиск можно проводить только по адресу или номеру л/с- одновременно нельзя! Спасибо ", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //fragment
        addShowHideListener(R.id.search_by_address_panel, R.id.imageView, adressstreethousepartnemt);
        addShowHideListener(R.id.search_by_facescore_panel, R.id.imageView1, facescore);
        //
        String[] myArraycitylist;
        myArraycitylist = citylist.toArray(new String[citylist.size()]);
        Set<String> setcity = new HashSet<>(Arrays.asList(myArraycitylist));
        String[] resultcity = setcity.toArray(new String[setcity.size()]);
        //
        String[] myArraystreetlist;
        myArraystreetlist = streetlist.toArray(new String[streetlist.size()]);
        Set<String> setstreet = new HashSet<>(Arrays.asList(myArraystreetlist));
        String[] resultstreet = setstreet.toArray(new String[setstreet.size()]);
        //first spinner city
        ArrayAdapter<String> adapter_state_city = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item) {

            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount()-1; // you don't display last item. It is used as hint.
            }
        };
        adapter_state_city.addAll(resultcity);
        adapter_state_city.add("Выберите город");
        adapter_state_city
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercitylist.setAdapter(adapter_state_city);
        spinnercitylist.setOnItemSelectedListener(this);
        spinnercitylist.setSelection(adapter_state_city.getCount());
        // second spinner street
        ArrayAdapter<String> adapter_state_street = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }
                return v;
            }
            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };
        adapter_state_street.addAll(resultstreet);
        adapter_state_street.add("Выберите улицу");
        adapter_state_street
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerstreetlist.setAdapter(adapter_state_street);
        spinnerstreetlist.setOnItemSelectedListener(this);
        spinnerstreetlist.setSelection(adapter_state_street.getCount());
    }
    // background for fragment animation
    void addShowHideListener(int buttonId, int imageViewId, final Fragment fragment) {
        View layout = findViewById(buttonId);
        final  ImageView imageView = (ImageView) findViewById(imageViewId);
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
    //Search in  List
    class SearchTask extends AsyncTask<Void, Void, List<Employee>> {
        //
        String city = "";
        String street = "";
        String numberhouse = "";
        String numberpartment = "";
        //
       String numberscore = "";
        //
        private SearchTask(String city, String street, String numberhouse, String numberpartment) {
            this.city = city;
            this.street = street;
            this.numberhouse = numberhouse;
            this.numberpartment = numberpartment;
        }
        private SearchTask(String s) {
            this.numberscore = s;
        }
        @Override
        protected List<Employee> doInBackground(Void... params) {
            List<Employee> employees = new ArrayList<>();
            if (!numberscore.isEmpty() && city.isEmpty() && street.isEmpty()) {
                for (Employee e : ReadCSVFile.empList) {
                    if (e.getPersonalAccount().contains(numberscore)) {
                        employees.add(e);
                    }
                }
            } else {
                if (numberscore.isEmpty() && !city.isEmpty() && !street.isEmpty()) {
                    if(!numberhouse.isEmpty() && numberpartment.isEmpty()) {
                        for (Employee e : ReadCSVFile.empList) {
                            if (e.getCity().trim().toLowerCase().contains(city.trim().toLowerCase())
                                    && e.getStreet().trim().toLowerCase().contains(street.trim().toLowerCase())
                                    && e.getNumberhouse().trim().toLowerCase().contains(numberhouse.trim().toLowerCase())
                                    )
                                employees.add(e);
                        }
                    }else{
                            if(!numberhouse.isEmpty() && !numberpartment.isEmpty()) {
                                for (Employee e : ReadCSVFile.empList) {
                                    if (e.getCity().trim().toLowerCase().contains(city.trim().toLowerCase())
                                            && e.getStreet().trim().toLowerCase().contains(street.trim().toLowerCase())
                                            && e.getNumberhouse().trim().toLowerCase().contains(numberhouse.trim().toLowerCase())
                                            && e.getNumberpartment().trim().toLowerCase().contains(numberpartment.trim().toLowerCase())
                                            )
                                        employees.add(e);
                                }
                            }
                        }
                     }
                }

            return employees;
        }
            @Override
            protected void onPostExecute (List < Employee > result) {
                super.onPostExecute(result);
                Intent intent = new Intent(context, ResultOfSearch.class);
                intent.putExtra("E", (Serializable) result);
                startActivity(intent);
            }
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                              long id) {
    }
   @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}

