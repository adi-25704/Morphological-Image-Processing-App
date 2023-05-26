package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;


public class Dilation extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button b,b7,b8;
    private EditText e;
    private ImageView imageView;




    public static Bitmap Dilate(Bitmap b)
    {
        int arrR[][]= Functions.channel(b,'r');

        for(int i=0;i<arrR.length;i++)
        {
            for(int j=0;j<arrR[0].length;j++)
            {
                if(i>=1&&i<arrR.length-1&&j>=1&&j<arrR[0].length-1)
                {
                    if(arrR[i][j]==0&&(arrR[i+1][j]==0||arrR[i-1][j]==0||arrR[i][j+1]==0||arrR[i][j-1]==0))
                    {
                        arrR[i][j]=10;
                    }
                }
            }
        }

        for(int i=0;i<arrR.length;i++)
        {
            for(int j=0;j<arrR[0].length;j++)
            {
                if(i>=1&&i<arrR.length-1&&j>=1&&j<arrR[0].length-1)
                {
                    if(arrR[i][j]==10)
                    {
                        arrR[i][j]=0;
                        arrR[i+1][j]=0;
                        arrR[i-1][j]=0;
                        arrR[i][j+1]=0;
                        arrR[i][j-1]=0;
                    }
                }
            }
        }
        int arrB[][]= Functions.channel(b,'b');
        for(int i=0;i<arrB.length;i++)
        {
            for(int j=0;j<arrR[0].length;j++)
            {
                if(i>=1&&i<arrB.length-1&&j>=1&&j<arrB[0].length-1)
                {
                    if(arrB[i][j]==0&&(arrB[i+1][j]==0||arrB[i-1][j]==0||arrB[i][j+1]==0||arrB[i][j-1]==0))
                    {
                        arrB[i][j]=10;
                    }
                }
            }
        }
        for(int i=0;i<arrB.length;i++)
        {
            for(int j=0;j<arrR[0].length;j++)
            {
                if(i>=1&&i<arrB.length-1&&j>=1&&j<arrB[0].length-1)
                {
                    if(arrB[i][j]==10)
                    {
                        arrB[i][j]=0;
                        arrB[i+1][j]=0;
                        arrB[i-1][j]=0;
                        arrB[i][j+1]=0;
                        arrB[i][j-1]=0;
                    }
                }
            }
        }
        int arrG[][]= Functions.channel(b,'g');
        for(int i=0;i<arrG.length;i++)
        {
            for(int j=0;j<arrG[0].length;j++)
            {
                if(i>=1&&i<arrG.length-1&&j>=1&&j<arrG[0].length-1)
                {
                    if(arrG[i][j]==0&&(arrG[i+1][j]==0||arrG[i-1][j]==0||arrG[i][j+1]==0||arrG[i][j-1]==0))
                    {
                        arrG[i][j]=10;
                    }
                }
            }
        }
        for(int i=0;i<arrG.length;i++)
        {
            for(int j=0;j<arrG[0].length;j++)
            {
                if(i>=1&&i<arrG.length-1&&j>=1&&j<arrG[0].length-1)
                {
                    if(arrG[i][j]==10)
                    {
                        arrG[i][j]=0;
                        arrG[i+1][j]=0;
                        arrG[i-1][j]=0;
                        arrG[i][j+1]=0;
                        arrG[i][j-1]=0;
                    }
                }
            }
        }
        return Functions.combine(arrR,arrB,arrG);
    }
    public Bitmap uriToBitmap(Uri uri) throws IOException, IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageView = findViewById(R.id.IV_Dilation);

//            try {
//                imageView.setImageBitmap(Process.greyScale(uriToBitmap(selectedImageUri)));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            try {
                imageView.setImageBitmap(uriToBitmap(selectedImageUri));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            b8 = findViewById(R.id.MC_Dilation);
                b8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        e = findViewById((R.id.ET_Dilation));
                        String s = e.getText().toString();
                        int a;
                        if(s.isEmpty()){
                            a = 0;
                        }else{
                            a= Integer.parseInt(s);
                        }
                        try {

                            Bitmap b1 = Binarise.binarizeImage(MainActivity.greyScale(uriToBitmap(selectedImageUri)),128);
                            Bitmap b = Dilate(b1);
                            for(int i = 1 ; i < a ; i++){
                                b = Dilate(b);
                            }
                            imageView.setImageBitmap(b);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });



        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilation);
        setTitle("Dilation");
        b = findViewById(R.id.AI_Dilation);
        b7 = findViewById(R.id.SI_Dilation);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);


            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable d = imageView.getDrawable();
                Binarise.saveImage(d,Dilation.this, "Dilation");
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