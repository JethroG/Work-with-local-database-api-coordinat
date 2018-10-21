package com.energycompany.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.energycompany.R;
import com.energycompany.adapter.SearchResultAdapter;
import com.energycompany.workrwithCSV.Employee;

import java.util.List;

public class ResultOfSearch extends AppCompatActivity {
    ListView resultlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_of_search);
        getSupportActionBar().setTitle("Результаты поиска");
        resultlist= (ListView) findViewById(R.id.resultlist);
        Intent intent = getIntent();
        List<Employee> e = (List<Employee>) intent.getSerializableExtra("E");
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(ResultOfSearch.this, e);
        resultlist.setAdapter(searchResultAdapter);
        resultlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Employee e = (Employee) resultlist.getItemAtPosition(i);
                Intent intent = new Intent(ResultOfSearch.this, EmployerDetailActivity.class);
                intent.putExtra("e", e);
                startActivity(intent);
                           }
        });
    }

}
