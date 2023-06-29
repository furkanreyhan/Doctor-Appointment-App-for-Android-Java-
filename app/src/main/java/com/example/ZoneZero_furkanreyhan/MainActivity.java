package com.example.ZoneZero_furkanreyhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    Doctor_RecyclerViewAdapter adapter;
    ArrayList<Doctor> doctors = new ArrayList<>();
    ArrayList<Doctor> filteredDoctors;

    ArrayList<String> genderFilter = new ArrayList<>();
    String nameFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        SearchView searchView = findViewById(R.id.searchView);
        CheckBox checkboxFemale = findViewById(R.id.checkBoxFemale);
        CheckBox checkboxMale = findViewById(R.id.checkBoxMale);
        ConstraintLayout notFoundLayout = findViewById(R.id.notFoundLayout);
        notFoundLayout.setVisibility(View.GONE);

        setupDoctors();

        adapter = new Doctor_RecyclerViewAdapter(this, doctors, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set search bar event listeners
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                nameFilter = query;
                filterDoctors(recyclerView, notFoundLayout, adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                nameFilter = newText;
                filterDoctors(recyclerView, notFoundLayout, adapter);
                return true;
            }
        });

        // Set gender checkbox event listeners
        checkboxFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    genderFilter.add("female");
                    if (checkboxMale.isChecked()) checkboxMale.setChecked(false);
                }
                else
                    genderFilter.remove("female");

                filterDoctors(recyclerView, notFoundLayout, adapter);
            }
        });

        checkboxMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    genderFilter.add("male");
                    if (checkboxFemale.isChecked()) checkboxFemale.setChecked(false);
                }
                else
                    genderFilter.remove("male");

                filterDoctors(recyclerView, notFoundLayout, adapter);
            }
        });


    }

    private void filterDoctors(RecyclerView recyclerView, ConstraintLayout notFoundLayout, Doctor_RecyclerViewAdapter adapter) {
        ArrayList<Doctor> filteredByName = new ArrayList<>(doctors);

        // Filter by name
        if (!Objects.equals(nameFilter, "")) {
            for (Doctor doctor : doctors) {
                if (!doctor.getFullName().toLowerCase().contains(nameFilter.toLowerCase())) {
                    filteredByName.remove(doctor);
                }
            }
        }

        ArrayList<Doctor> filteredByNameAndGender = new ArrayList<>(filteredByName);
        // Filter by gender
        if (!genderFilter.isEmpty()) {
            for (Doctor doctor: filteredByName) {
                if (!genderFilter.contains(doctor.getGender())) {
                    filteredByNameAndGender.remove(doctor);
                }
            }
        }

        filteredDoctors = filteredByNameAndGender;

        if (filteredDoctors.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            notFoundLayout.setVisibility(View.VISIBLE);
        } else {
            notFoundLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setDoctors(filteredDoctors);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("fullName", filteredDoctors.get(position).getFullName());
        intent.putExtra("imageUrl", filteredDoctors.get(position).getImageUrl());
        String status = filteredDoctors.get(position).getStatus();
        System.out.println("########### status : " + status);
        intent.putExtra("status", status);
        startActivity(intent);
    }

    private void setupDoctors() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://www.mobillium.com/android/doctors.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("doctors");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    doctors.add(new Doctor(
                            object.getString("full_name"),
                            object.getString("user_status"),
                            object.getString("gender"),
                            object.getJSONObject("image").getString("url")
                    ));
                }
                filteredDoctors = new ArrayList<>(doctors);
                adapter.setDoctors(doctors);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> System.out.println("Error: Can't fetch the doctors data."));
        requestQueue.add(jsonObjectRequest);

        System.out.println("All doctors fetched.");


    }


}