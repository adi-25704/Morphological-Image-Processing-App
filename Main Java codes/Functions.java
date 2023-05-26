package com.example.unitconvert;
import android.content.Context;
import android.media.ExifInterface;
import android.net.Uri;
import android.widget.ImageView;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.bumptech.glide.Glide;

import java.io.IOException;

public class Functions {



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
    }
    public static int[][] channel(Bitmap image, char c1){
        int arr[][] = new int[image.getHeight()][image.getWidth()];
        for(int i = 0 ; i < image.getHeight() ; i++){
            for(int j = 0 ; j < image.getWidth() ; j++){

                if(c1=='r') arr[i][j] = Color.red(image.getPixel(j,i));
                if(c1=='g'){
                    arr[i][j] =Color.green(image.getPixel(j,i));

                }
                if(c1=='b') arr[i][j] =Color.blue(image.getPixel(j,i));
                if(c1=='0') arr[i][j] =Color.BLACK;

            }
        }
        return arr;
    }
    public static Bitmap Erode(Bitmap b)
    {

        int arrR[][]= channel(b,'r');
        for(int i=0;i<arrR.length;i++)
        {
            for(int j=0;j<arrR[0].length;j++)
            {
                if(i>=1&&i<arrR.length-1&&j>=1&&j<arrR[0].length-1)
                {
                    if(arrR[i][j]==0&&(arrR[i+1][j]==255||arrR[i-1][j]==255||arrR[i][j+1]==255||arrR[i][j-1]==255||arrR[i+1][j+1]==255||arrR[i-1][j+1]==255||arrR[i+1][j-1]==255||arrR[i-1][j-1]==255))
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
        int arrB[][]= channel(b,'b');
        for(int i=0;i<arrB.length;i++)
        {
            for(int j=0;j<arrB[0].length;j++)
            {
                if(i>=1&&i<arrB.length-1&&j>=1&&j<arrB[0].length-1)
                {
                    if(arrB[i][j]==0&&(arrB[i+1][j]==255||arrB[i-1][j]==255||arrB[i][j+1]==255||arrB[i][j-1]==255||arrB[i+1][j+1]==255||arrB[i+1][j-1]==255||arrB[i-1][j+1]==255||arrB[i-1][j-1]==255))
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
        int arrG[][]= channel(b,'g');
        for(int i=0;i<arrG.length;i++)
        {
            for(int j=0;j<arrG[0].length;j++)
            {
                if(i>=1&&i<arrG.length-1&&j>=1&&j<arrG[0].length-1)
                {
                    if(arrG[i][j]==0&&(arrG[i+1][j]==255||arrG[i-1][j]==255||arrG[i][j+1]==255||arrG[i][j-1]==255||arrG[i+1][j+1]==255||arrG[i+1][j-1]==255||arrG[i-1][j+1]==255||arrG[i-1][j-1]==255))
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
        //Main.output("erode.jpg",combine(arrR,arrB,arrG));
        return combine(arrR,arrG,arrB);

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

    public static Bitmap Dilate(Bitmap b)
    {
        int arrR[][]= channel(b,'r');
        for(int i=0;i<arrR.length;i++)
        {
            for(int j=0;j<arrR[0].length;j++)
            {
                if(i>=1&&i<arrR.length-1&&j>=1&&j<arrR[0].length-1)
                {
                    if(arrR[i][j]==0&&(arrR[i+1][j]==0||arrR[i-1][j]==0||arrR[i][j+1]==0||arrR[i][j-1]==0||arrR[i+1][j+1]==0||arrR[i-1][j+1]==0||arrR[i+1][j-1]==0||arrR[i-1][j-1]==0))
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
                if(arrR[i][j]==10)
                {
                    arrR[i][j]=0;
                    arrR[i+1][j]=0;
                    arrR[i-1][j]=0;
                    arrR[i][j+1]=0;
                    arrR[i][j-1]=0;
                    arrR[i+1][j+1]=0;
                    arrR[i+1][j-1]=0;
                    arrR[i-1][j+1]=0;
                    arrR[i-1][j-1]=0;
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
                    if(arrB[i][j]==0&&(arrB[i+1][j]==0||arrB[i-1][j]==0||arrB[i][j+1]==0||arrB[i][j-1]==0||arrB[i+1][j+1]==0||arrB[i+1][j-1]==0||arrB[i-1][j+1]==0||arrB[i-1][j-1]==0))
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
                if(arrB[i][j]==10)
                {
                    arrB[i][j]=0;
                    arrB[i+1][j]=0;
                    arrB[i-1][j]=0;
                    arrB[i][j+1]=0;
                    arrB[i][j-1]=0;
                    arrB[i+1][j+1]=0;
                    arrB[i+1][j-1]=0;
                    arrB[i-1][j+1]=0;
                    arrB[i-1][j-1]=0;
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
                    if(arrG[i][j]==0&&(arrG[i+1][j]==0||arrG[i-1][j]==0||arrG[i][j+1]==0||arrG[i][j-1]==0||arrG[i+1][j+1]==0||arrG[i+1][j-1]==0||arrG[i-1][j+1]==0||arrG[i-1][j-1]==0))
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
                if(arrG[i][j]==10)
                {
                    arrG[i][j]=0;
                    arrG[i+1][j]=0;
                    arrG[i-1][j]=0;
                    arrG[i][j+1]=0;
                    arrG[i][j-1]=0;
                    arrG[i+1][j+1]=0;
                    arrG[i+1][j-1]=0;
                    arrG[i-1][j+1]=0;
                    arrG[i-1][j-1]=0;
                }
            }
        }
        //Main.output("erode.jpg",combine(arrR,arrB,arrG));
        return combine(arrR,arrG,arrB);

    }
}
