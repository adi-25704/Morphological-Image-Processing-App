package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Credits extends AppCompatActivity {
    private ImageView i1 , i2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        setTitle("Credits");
        i1 = findViewById(R.id.Credit1);
        i2 = findViewById(R.id.Credit2);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = "https://pngtree.com/freebackground/gradient-geometric-vertical-background-for-instagram-stories-device-landing-page-web-app-digital-projects-in-abstract-style_1596760.html?sol=downref&id=bef";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                startActivity(intent);

            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "https://www.ucraft.com/free-logo-maker";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                startActivity(intent);
            }
        });
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