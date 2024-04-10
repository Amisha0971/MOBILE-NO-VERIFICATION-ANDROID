package com.example.mobileno_verification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method to handle button click and redirect to contactDetailsActivity
    public void redirectToContactDetails(View view) {
        Intent intent = new Intent(MainActivity.this, contactDetailsActivity.class);
        startActivity(intent);
    }
}