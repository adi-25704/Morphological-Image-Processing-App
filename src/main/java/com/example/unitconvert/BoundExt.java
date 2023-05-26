package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;


public class BoundExt extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button b,b7,b8;
    private EditText e;
    private ImageView imageView;





    public static Bitmap extract(Bitmap image, int factor)
    {
        int countW=0,countB=0;
        int arr[][]= Functions.channel(image,'r');
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
            image=invert(image);

            Bitmap image1=Functions.Erode(image);;
            for(int i=1;i<factor;i++)
            {
                image1= Functions.Erode(image1);
            }
            image=invert(image);
            image1=invert(image1);
            Bitmap image_F1= intersect(image, image1,countB,countW);

            //image_F1=InvertBW.invert(image_F1);
            return image_F1;
            //Main.output("imopen.jpg",image);
        }
        else
        {
            Bitmap image1=Functions.Erode(image);
            for(int i=1;i<factor;i++)
            {
                image1= Functions.Erode(image1);
            }
            Bitmap image_F1= intersect(image, image1,countB,countW);
            //image_F1=InvertBW.invert(image_F1);
            //Main.output("imopen.jpg",image);
            return image_F1;
        }
    }


    public static Bitmap intersect(Bitmap image, Bitmap image1,int countB,int countW)
    {
        image1= invert(image1);
        int arrR2[][]=Functions.channel(image1, '0');
        int arrR1[][]=Functions.channel(image1, 'r');
        int arrR0[][]=Functions.channel(image, 'r');

        for(int i=0;i<arrR1.length;i++)
        {
            for(int j=0;j<arrR1[0].length;j++)
            {
                if(arrR1[i][j] == arrR0[i][j])
                {
                    arrR2[i][j]=arrR0[i][j];
                }

                else
                {
                    if(countB>countW)
                        arrR2[i][j]=0;
                    else
                        arrR2[i][j]=255;
                }
            }
        }
        int arrB2[][]=Functions.channel(image1, '0');
        int arrB1[][]=Functions.channel(image1, 'b');
        int arrB0[][]=Functions.channel(image, 'b');

        for(int i=0;i<arrB1.length;i++)
        {
            for(int j=0;j<arrB1[0].length;j++)
            {
                if(arrB1[i][j] == arrB0[i][j])
                {
                    arrB2[i][j]=arrB0[i][j];
                }

                else
                {
                    if(countB>countW)
                        arrB2[i][j]=0;
                    else
                        arrB2[i][j]=255;
                }
            }

        }

        int arrG2[][]=Functions.channel(image1, '0');
        int arrG1[][]=Functions.channel(image1, 'g');
        int arrG0[][]=Functions.channel(image, 'g');

        for(int i=0;i<arrG1.length;i++)
        {
            for(int j=0;j<arrG1[0].length;j++)
            {
                if(arrG1[i][j] == arrG0[i][j])
                {
                    arrG2[i][j]=arrG0[i][j];
                }

                else
                {
                    if(countB>countW)
                        arrG2[i][j]=0;
                    else
                        arrG2[i][j]=255;
                }
            }
        }
        return Functions.combine(arrR2, arrG2, arrB2);
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
            imageView = findViewById(R.id.IV_Boundary);

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
            b8 = findViewById(R.id.MC_Boundary);
            b8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e = findViewById((R.id.ET_Boundary));
                    String s = e.getText().toString();
                    int a;
                    if(s.isEmpty()){
                        a = 0;
                    }else{
                        a= Integer.parseInt(s);
                    }

                    try {
                        Bitmap b = Binarise.binarizeImage(MainActivity.greyScale(uriToBitmap(selectedImageUri)),128);
                        imageView.setImageBitmap(extract(b,a));
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
        setContentView(R.layout.activity_bound_ext);
        setTitle("Boundary Extraction");
        b = findViewById(R.id.AI_Boundary);
        b7 = findViewById(R.id.SI_Boundary);

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
                Binarise.saveImage(d,BoundExt.this,"Boundary_Extract");
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