package com.example.unitconvert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Binarise extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button b,b5;
    ImageView imageView;


//    public static void saveFile(Bitmap b){
//
//    }
    @SuppressLint("MissingInflatedId")
    public Bitmap uriToBitmap(Uri uri) throws IOException, IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }
    public static Bitmap binarizeImage(Bitmap grayScale, int threshold) {
        
        //Making a mutable Bitmap
        Bitmap b1 = grayScale.copy(Bitmap.Config.ARGB_8888, true);

        for(int i = 0 ; i < grayScale.getHeight(); i++){
            for(int j = 0 ; j < grayScale.getWidth(); j++){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                   int x = Color.red(grayScale.getPixel(j,i));
                    if(x < threshold){
                        b1.setPixel(j,i,Color.BLACK);
                    }else{
                        b1.setPixel(j,i,Color.WHITE);
                    }
                }
//                b1.setPixel(j,i,Color.RED);

            }
        }

        return b1;
    }
    public static void saveImage(Drawable d, Context c, String s){
        Bitmap b = ((BitmapDrawable) d).getBitmap();
        Random r = new Random();
        int a = r.nextInt(Integer.MAX_VALUE);
        String fileName =  s + a + ".jpg"; // The name of the file to be saved

        try {
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
            FileOutputStream outputStream = new FileOutputStream(f);
            b.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

            // Insert the image into the MediaStore database
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATA, f.getAbsolutePath());
            c.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // Notify the MediaScanner to scan the file and update the gallery
            MediaScannerConnection.scanFile(c, new String[]{f.getAbsolutePath()}, null, null);

            Toast.makeText(c, "Image saved to gallery", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(c, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageView = findViewById(R.id.IV_Binarise);

//            try {
//                imageView.setImageBitmap(Process.greyScale(uriToBitmap(selectedImageUri)));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            try {
                Bitmap b = binarizeImage(MainActivity.greyScale(uriToBitmap(selectedImageUri)), 128);
                imageView.setImageBitmap(b);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Binary Image Convertor");

        setContentView(R.layout.activity_binarise);
        b = findViewById(R.id.AI_Binarise);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        b5 = findViewById(R.id.SF_Binarise);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                        saveFile(b);
                Drawable d = imageView.getDrawable();
                saveImage(d,Binarise.this, "Binarise");
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