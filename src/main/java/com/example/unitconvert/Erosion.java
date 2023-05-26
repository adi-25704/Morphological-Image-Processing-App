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


public class Erosion extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button b,b7,b8;
    private EditText e;
    private ImageView imageView;


    public static int[][] channel(Bitmap image, char c1){
        int arr[][] = new int[image.getHeight()][image.getWidth()];
        for(int i = 0 ; i < image.getHeight() ; i++){
            for(int j = 0 ; j < image.getWidth() ; j++){

                if(c1=='r') arr[i][j] = Color.red(image.getPixel(j,i));
                if(c1=='g'){
                    arr[i][j] =Color.green(image.getPixel(j,i));

                }
                if(c1=='b') arr[i][j] =Color.blue(image.getPixel(j,i));

            }
        }
        return arr;
    }

    public static Bitmap Erosion(Bitmap b)
    {

        int arrR[][]= channel(b,'r');

        for(int i=0;i<arrR.length;i++)
        {
            for(int j=0;j<arrR[0].length;j++)
            {
                if(i>=1&&i<arrR.length-1&&j>=1&&j<arrR[0].length-1)
                {
                    if(arrR[i][j]==0&&(arrR[i+1][j]==255||arrR[i-1][j]==255||arrR[i][j+1]==255||arrR[i][j-1]==255))
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
                        arrR[i][j]=255;
                        // arrR[i+1][j]=0;
                        // arrR[i-1][j]=0;
                        // arrR[i][j+1]=0;
                        // arrR[i][j-1]=0;
                    }
                }
            }
        }
        int arrB[][]= channel(b,'b');
        for(int i=0;i<arrB.length;i++)
        {
            for(int j=0;j<arrB[0].length;j++)
            {
                if(i>=1&&i<arrB.length-1&&j>=1&&j<arrB[0].length-1)
                {
                    if(arrB[i][j]==0&&(arrB[i+1][j]==255||arrB[i-1][j]==255||arrB[i][j+1]==255||arrB[i][j-1]==255))
                    {
                        arrB[i][j]=10;
                    }
                }
            }
        }
        for(int i=0;i<arrB.length;i++)
        {
            for(int j=0;j<arrB[0].length;j++)
            {
                if(i>=1&&i<arrB.length-1&&j>=1&&j<arrB[0].length-1)
                {
                    if(arrB[i][j]==10)
                    {
                        arrB[i][j]=255;
                        // arrB[i+1][j]=0;
                        // arrB[i-1][j]=0;
                        // arrB[i][j+1]=0;
                        // arrB[i][j-1]=0;
                    }
                }
            }
        }
        int arrG[][]= channel(b,'g');
        for(int i=0;i<arrG.length;i++)
        {
            for(int j=0;j<arrG[0].length;j++)
            {
                if(i>=1&&i<arrG.length-1&&j>=1&&j<arrG[0].length-1)
                {
                    if(arrG[i][j]==0&&(arrG[i+1][j]==255||arrG[i-1][j]==255||arrG[i][j+1]==255||arrG[i][j-1]==255))
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
                        arrG[i][j]=255;
                        // arrG[i+1][j]=0;
                        // arrG[i-1][j]=0;
                        // arrG[i][j+1]=0;
                        // arrG[i][j-1]=0;
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
            imageView = findViewById(R.id.IV_Erosion);

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
            b8 = findViewById(R.id.MC_Erosion);
            b8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    e = findViewById((R.id.ET_Erosion));
                    String s = e.getText().toString();
                    int a;
                    if(s.isEmpty()){
                        a = 0;
                    }else{
                        a= Integer.parseInt(s);
                    }
                    try {
                        Bitmap b = Erosion(Binarise.binarizeImage(MainActivity.greyScale(uriToBitmap(selectedImageUri)),128));
                        for(int i = 1 ; i < a ; i++) b = Erosion(b);
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
        setContentView(R.layout.activity_erosion);
        setTitle("Erosion");
        b = findViewById(R.id.AI_Erosion);
        b7 = findViewById(R.id.SI_Erosion);

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
                Binarise.saveImage(d,Erosion.this, "Erosion");
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