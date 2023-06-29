package com.example.ZoneZero_furkanreyhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String fullName = getIntent().getStringExtra("fullName");
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String status = getIntent().getStringExtra("status");

        TextView tvPremium = findViewById(R.id.textViewPremium);
        TextView tvAction = findViewById(R.id.textViewAction);

        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                if (Objects.equals(status, "premium")) {
                    intent = new Intent(DetailActivity.this, PaymentActivity.class);
                } else {
                    intent = new Intent(DetailActivity.this, AppointmentActivity.class);
                }

                startActivity(intent);
            }
        });


        if (Objects.equals(status, "premium")) {
            tvPremium.setVisibility(View.VISIBLE);
            tvAction.setText("Premium Paket Al");
        } else {
            tvPremium.setVisibility(View.INVISIBLE);
            tvAction.setText("Randevu Al");
        }

        ImageView imageViewAvatar = findViewById(R.id.imageViewAvatar);


        Glide.with(this).load(imageUrl).into(imageViewAvatar);

        TextView textViewFullName = findViewById(R.id.textViewFullname);

        textViewFullName.setText(fullName);
    }
}