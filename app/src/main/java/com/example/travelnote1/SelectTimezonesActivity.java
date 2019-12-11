package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

public class SelectTimezonesActivity extends AppCompatActivity {
    ArrayList<String> selectedTimezones = new ArrayList<>();
    TimeZoneAdapter adapter;
    ArrayList<String> timezones;
    ListView listView;
    boolean showAll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_timezones);

        //setTitle("시간 선택하기");

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("selectedTimezonesBundle");
        selectedTimezones = bundle.getStringArrayList("selectedTimezones");
        timezones = new ArrayList<>(Arrays.asList(TimeZone.getAvailableIDs()));

        listView = findViewById(R.id.listView);
        adapter = new TimeZoneAdapter(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, timezones);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listView.isItemChecked(i)) {
                    selectedTimezones.add(adapter.getItem(i));
                } else {
                    selectedTimezones.remove(adapter.getItem(i));
                }
            }
        });

        checkSelectedTimezones();
    }

    public void done(View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("selectedTimezones", selectedTimezones);
        Intent result = new Intent(this, MainActivity.class);
        result.putExtra("selectedTimezonesBundle", bundle);
        setResult(RESULT_OK, result);
        finish();
    }

    public void showChecked(View view) {
        BootstrapButton button = (BootstrapButton) view;
        adapter.clear();
        if (showAll) {
            for (String timezone : selectedTimezones) {
                adapter.add(timezone);
            }
            adapter.notifyDataSetChanged();

            button.setText("시간 List 보기");
            showAll = false;
        } else {
            for (String timezone : TimeZone.getAvailableIDs()) {
                adapter.add(timezone);
            }
            adapter.notifyDataSetChanged();

            button.setText("선택한 시간 보기");
            showAll = true;
        }

        checkSelectedTimezones();
    }

    public void uncheckAll(View view) {
        selectedTimezones.clear();
        checkSelectedTimezones();
    }

    private void checkSelectedTimezones() {
        for(int j = 0; j < adapter.getCount(); j++) {
            if (selectedTimezones.contains(adapter.getItem(j))) {
                listView.setItemChecked(j, true);
            } else {
                listView.setItemChecked(j, false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s, new Filter.FilterListener() {
                    @Override
                    public void onFilterComplete(int i) {
                        checkSelectedTimezones();
                    }
                });

                return true;
            }
        });

        return true;
    }
}
