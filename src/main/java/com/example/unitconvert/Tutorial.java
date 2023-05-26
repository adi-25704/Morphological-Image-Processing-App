package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

public class Tutorial extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setTitle("Tutorial");




    }
    @Override
    public void onBackPressed() {
        // Add your back button handling logic here
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }
}