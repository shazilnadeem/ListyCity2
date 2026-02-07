package com.example.listycity2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    private EditText cityNameInput;
    private EditText provinceNameInput;
    private OnFragmentInteractionListener listener;

    // Interface to send data back to MainActivity
    public interface OnFragmentInteractionListener {
        void onOkPressed(City newCity);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    // Method to create a new instance with a City object (for editing)
    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_city_fragment_layout, null);
        cityNameInput = view.findViewById(R.id.city_name_edit);
        provinceNameInput = view.findViewById(R.id.province_name_edit);

        // Check if we are editing (populate fields)
        if (getArguments() != null) {
            City city = (City) getArguments().getSerializable("city");
            if (city != null) {
                cityNameInput.setText(city.getCityName());
                provinceNameInput.setText(city.getProvinceName());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String city = cityNameInput.getText().toString();
                    String province = provinceNameInput.getText().toString();

                    // Send the new/updated object back to MainActivity
                    listener.onOkPressed(new City(city, province));
                }).create();
    }
}