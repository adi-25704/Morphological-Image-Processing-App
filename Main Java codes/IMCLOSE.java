package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
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


public class IMCLOSE extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button b,b7,b8;
    private EditText e;
    private ImageView imageView;



    public static Bitmap combine(int arrRed[][], int arrGreen[][], int[][] arrBlue){
        int height = arrBlue.length;
        int width = arrBlue[0].length;

        Bitmap combine = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                combine.setPixel(j,i,Color.rgb(arrRed[i][j],arrGreen[i][j],arrBlue[i][j]));
            }
        }


        return combine;
        // // Save the image to a file
        // File outputFile = new File("GI - Copy(dilate).png");
        // try {
        //     ImageIO.write(image2, "png", outputFile);
        //     System.out.println("Image created successfully!");
        // } catch (IOException e) {
        //     System.out.println("Error: " + e.getMessage());
        // }
    }
    public static Bitmap invert(Bitmap b)
    {
        for(int i = 0 ; i < b.getWidth() ; i++){
            for(int j = 0 ; j < b.getHeight() ; j++){
                if(Color.red(b.getPixel(i,j))==0){
                    b.setPixel(i,j,Color.WHITE);
                }else{
                    b.setPixel(i,j,Color.BLACK);
                }

            }
        }
        return b;
//        return combine(arrR,arrB,arrG);
    }
    public static Bitmap imclose(Bitmap image)
    {
        int arr[][]= Functions.channel(image,'r'),countW=0,countB=0;
        for(int i=0;i<arr.length;i++)
        {
            for(int j=0;j<arr[0].length;j++)
            {
                if(arr[i][j]==0)
                {
                    countB++;
                }
                else
                {
                    countW++;
                }
            }
        }
        if(countB>countW)
        {
            image=Functions.invert(image);
            for(int i=0;i<2;i++)
            {
                image= Functions.Dilate(image);
            }
            for(int i=0;i<2;i++)
            {
                image= Functions.Erode(image);
            }
            image=Functions.invert(image);
            return image;
        }
        else
        {

            for(int i=0;i<2;i++)
            {
                image=Functions.Dilate(image);
            }
            for(int i=0;i<2;i++)
            {
                image= Functions.Erode(image);
            }

            return image;
        }

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
            imageView = findViewById(R.id.IV_Imclose);

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
            b8 = findViewById(R.id.MC_Imclose);
            b8.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View v) {
                    e = findViewById((R.id.ET_Imclose));

                    String s = e.getText().toString();
                    int a;
                    if(s.isEmpty()){
                        a = 0;
                    }else{
                       a= Integer.parseInt(s);
                    }
                    try {
                        Bitmap b = Binarise.binarizeImage(MainActivity.greyScale(uriToBitmap(selectedImageUri)),128);
                        for(int i = 1 ; i < a ; i++) b = imclose(b);
                        imageView.setImageBitmap(imclose(b));
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
        setContentView(R.layout.activity_imclose);
        setTitle("Closing");
        b = findViewById(R.id.AI_Imclose);
        b7 = findViewById(R.id.SI_Imclose);

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
                Binarise.saveImage(d,IMCLOSE.this, "Closing");
            }
        });




    }
    @Override
    public void onBackPressed() {
        // Add your back button handling logic here
        super.onBackPressed();
        Intent intent = new Intent(this, Morphological.class);
        startActivity(intent);
        finish();
    }
}