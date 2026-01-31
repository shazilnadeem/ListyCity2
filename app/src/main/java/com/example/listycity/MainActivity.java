package com.example.listycity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView city_liist;
    private EditText input_city;
    private Button add_city_button;
    private Button delete_city_butto;
    private Button confirm_button;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> cityAdapter;

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
        dataList.add("Islamabad");
        dataList.add("Newyork");
        dataList.add("Helsinki");
        dataList.add("taxila");


        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, dataList);

        city_liist.setAdapter(cityAdapter);

        city_liist.setOnItemClickListener((parent, view, position, id) -> {
            curr_selected_index = position;
            city_liist.setItemChecked(position, true);
        });


        add_city_button.setOnClickListener(v -> {
            input_city.setVisibility(View.VISIBLE);
            confirm_button.setVisibility(View.VISIBLE);
            input_city.setText("");
            input_city.requestFocus();
        });

        confirm_button.setOnClickListener(v -> addCity());
        delete_city_butto.setOnClickListener(v -> deleteSelectedCity());


    }


    private void addCity() {
        String city = input_city.getText().toString().trim();

        if (city.isEmpty()) {
            Toast.makeText(this, "Pls Enter a city name", Toast.LENGTH_SHORT).show();
            return;
        }

        dataList.add(city);
        cityAdapter.notifyDataSetChanged();

        input_city.setText("");
        input_city.setVisibility(View.GONE);
        confirm_button.setVisibility(View.GONE);

        curr_selected_index = -1;
        city_liist.clearChoices();
    }

    private void deleteSelectedCity() {
        if (curr_selected_index < 0 || curr_selected_index >= dataList.size()) {
            Toast.makeText(this, "!!!Tapp on a city to select it first", Toast.LENGTH_SHORT).show();
            return;
        }

        dataList.remove(curr_selected_index);
        cityAdapter.notifyDataSetChanged();

        curr_selected_index = -1;
        city_liist.clearChoices();
    }

}


