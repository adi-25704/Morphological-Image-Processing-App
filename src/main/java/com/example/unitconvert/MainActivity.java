package com.example.unitconvert;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    public  static Bitmap greyScale(Bitmap b){

        int width = b.getWidth();
        int height = b.getHeight();

        //Making a mutable Bitmap
        Bitmap b1 = b.copy(Bitmap.Config.ARGB_8888, true);
        int R = 0;
        int G = 0;
        int B = 0;
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                     R = Color.red(b.getPixel(j,i));
                     G = Color.green(b.getPixel(j,i));
                     B = Color.blue(b.getPixel(j,i));
                    // Always use (j,i) instead of (i,j)
                    b1.setPixel(j, i, Color.rgb((R+G+B)/3,(R+G+B)/3,(R+G+B)/3));
                }

            }
        }


        return b1;


    }
    public Bitmap uriToBitmap(Uri uri) throws IOException, IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        inputStream.close();
        return bitmap;
    }
    // In your method or event handler for selecting an image:

    private Button b,b6;


    // Handle the result in onActivityResult:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            ImageView imageView = findViewById(R.id.IV_greyScale);


            String filePath;
            if ("content".equalsIgnoreCase(selectedImageUri.getScheme())) {
                // The Uri is a content Uri (e.g., returned by an Intent.ACTION_GET_CONTENT)
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                } else {
                    // Failed to resolve the content Uri to a file path
                    // Handle the error case appropriately
                    return;
                }
            } else if ("file".equalsIgnoreCase(selectedImageUri.getScheme())) {
                // The Uri is a file Uri
                filePath = selectedImageUri.getPath();
            } else {
                // Unsupported Uri scheme
                // Handle the error case appropriately
                return;
            }

            File file = new File(filePath);

            try {
                imageView.setImageBitmap(greyScale(uriToBitmap(selectedImageUri)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("GreyScale-Convertor");
        setContentView(R.layout.activity_main);
        b = findViewById(R.id.AI_greyScale);
        ImageView imageView = findViewById(R.id.IV_greyScale);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);


            }
        });
        b6 = findViewById(R.id.SI_greyScale);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable d = imageView.getDrawable();
                Binarise.saveImage(d,MainActivity.this,"Grayscale");
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
//    private File createImageFile() {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        try {
//            File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
//            return imageFile;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}