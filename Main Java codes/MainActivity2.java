package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
        private Button b;
    private Button b2;
    private Button b9;
    private Button b13;
    private Button b14;
    private Button b15;


    public void navigateToActivity(View view, Class c) {
        Intent intent = new Intent(MainActivity2.this, c);
        startActivity(intent);
    }
    @SuppressLint("MissingInflatedId")
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main2);
            setTitle("Image Processing");

            b = findViewById(R.id.greyScale);
            b2 = findViewById(R.id.Binarise);
        Button b3 = findViewById(R.id.Dilation);
            b9 = findViewById(R.id.Erosion);
            b13 = findViewById(R.id.Morph);
            b14 = findViewById(R.id.Tutorial);
            b15 = findViewById(R.id.Credit);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToActivity(v,MainActivity.class);
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToActivity(v,Binarise.class);
                }
            });
            b3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToActivity(v,Dilation.class);
                }
            });
            b9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToActivity(v, Erosion.class);
                }
            });
            b13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToActivity(v, Morphological.class);
                }
            });
            b14.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToActivity(v, Tutorial.class);
                }
            });
            b15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToActivity(v, Credits.class);
                }
            });

        }
    public void onBackPressed() {
        // Add your back button handling logic here
        super.onBackPressed();
//        Intent intent = new Intent(this, MainActivity2.class);
//       startActivity(intent);
        finishAffinity();
        finish();
    }
}