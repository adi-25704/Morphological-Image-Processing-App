package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Morphological extends AppCompatActivity {

    private Button b,b1,b2;
    public void navigateToActivity(View view, Class c) {
        Intent intent = new Intent(Morphological.this, c);
        startActivity(intent);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morphological);
        setTitle("Morphological Operations");
        b = findViewById(R.id.Imopen);
        b1 = findViewById(R.id.Boundary);
        b2 = findViewById(R.id.Imclose);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(v, IMOPEN.class);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToActivity(v,BoundExt.class);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 navigateToActivity(v,IMCLOSE.class);
            }
        });


    }
    public void onBackPressed() {
        // Add your back button handling logic here
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }
}