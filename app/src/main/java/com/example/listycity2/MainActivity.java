package com.example.listycity2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.OnFragmentInteractionListener {
    private ListView city_liist;
    private EditText input_city;
    private Button add_city_button;
    private Button delete_city_butto;
    private Button confirm_button;
    private ArrayList<City> dataList;
    private CustomList cityAdapter;
    private long lastClickTime = 0;

    private int curr_selected_index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        city_liist = findViewById(R.id.city_list);
        input_city = findViewById(R.id.input_city);
        add_city_button = findViewById(R.id.button_add_city);
        delete_city_butto = findViewById(R.id.button_del_city);
        confirm_button = findViewById(R.id.button_confirm);

        dataList = new ArrayList<>();
        dataList.add(new City("Islamabad", "ICT"));
        dataList.add(new City("New York", "NY"));
        dataList.add(new City("Helsinki", "Uusimaa"));
        dataList.add(new City("Taxila", "Punjab"));


        cityAdapter = new CustomList(this, dataList);

        city_liist.setAdapter(cityAdapter);

        city_liist.setOnItemClickListener((parent, view, position, id) -> {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < 300 && curr_selected_index == position) {
                City selectedCity = dataList.get(position);
                AddCityFragment.newInstance(selectedCity).show(getSupportFragmentManager(), "EDIT_CITY");
            } else {
                curr_selected_index = position;
                cityAdapter.setSelectedPosition(position);
                Toast.makeText(this, "Double click to edit", Toast.LENGTH_SHORT).show();
            }
            lastClickTime = clickTime;
        });

        add_city_button.setOnClickListener(v -> {
            curr_selected_index = -1;
            new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
        });

        delete_city_butto.setOnClickListener(v -> deleteSelectedCity());

        findViewById(R.id.main).setOnClickListener(v -> {
            curr_selected_index = -1;
            cityAdapter.setSelectedPosition(-1);
        });


    }


    @Override
    public void onOkPressed(City newCity) {
        if (curr_selected_index == -1) {
            dataList.add(newCity);
        } else {
            dataList.set(curr_selected_index, newCity);
            curr_selected_index = -1;
        }
        cityAdapter.notifyDataSetChanged();
    }

    private void deleteSelectedCity() {
        if (curr_selected_index < 0 || curr_selected_index >= dataList.size()) {
            Toast.makeText(this, "!!!Tapp on a city to select it first", Toast.LENGTH_SHORT).show();
            return;
        }

        dataList.remove(curr_selected_index);
        cityAdapter.setSelectedPosition(-1);
        cityAdapter.notifyDataSetChanged();

        curr_selected_index = -1;
    }

}


