package com.example.xiaojiakai.opencvtest;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import java.io.FileNotFoundException;
import java.io.InputStream;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_open_cv);

        Button GiveFaceButton = (Button)findViewById(R.id.GiveFaceButton);
        GiveFaceButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent();

                //开启picture画面Type设置为image*
                myIntent.setType("image/*");

                //使用Intent.ACTION_GET_CONTENT这个Action
                myIntent.setAction(Intent.ACTION_GET_CONTENT);

                /*取得相片后返回本画面*/
                startActivityForResult(myIntent,1);
            }
        });

        Button GetFaceButton = (Button)findViewById(R.id.GetFaceButton);
        GetFaceButton.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent myIntent = new Intent();

                //开启picture画面Type设置为image*
                myIntent.setType("image/*");

                //使用Intent.ACTION_GET_CONTENT这个Action
                myIntent.setAction(Intent.ACTION_GET_CONTENT);

                /*取得相片后返回本画面*/
                startActivityForResult(myIntent,2);
            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        switch (requestCode)
        {
            case 1 :
                Uri uri = data.getData();
                Log.e("uri",uri.toString());
                ContentResolver cr = this.getContentResolver();

                try
                {
                    InputStream input = cr.openInputStream(uri);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(input, null, options);
                    if (options.outWidth > 1024 || options.outHeight > 1024){
                        options.inSampleSize = Math.max(options.outWidth / 1024, options.outHeight/1024);
                    }
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri), null, options);

                    /*以下进行灰度化处理*/
                    int w = bitmap.getWidth();
                    int h = bitmap.getHeight();
                    int[] pix = new int[w * h];
                    bitmap.getPixels(pix, 0, w, 0, 0, w, h);
                    int[] resultPixels = OpenCVHelper.gray(pix, w, h);
                    Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                    result.setPixels(resultPixels,0,w,0,0,w,h);

                    ImageView imageView = (ImageView)findViewById(R.id.IV01);
                    /*将处理好的灰度图设定到ImageView*/
                    imageView.setImageBitmap(result);
                }
                catch(FileNotFoundException e)
                {
                    Log.e("Exception",e.getMessage(),e);
                }
                break;
            case 2:
                Uri uri2 = data.getData();
                Log.e("uri2",uri2.toString());
                ContentResolver cr2 = this.getContentResolver();

                try
                {
                    InputStream input = cr2.openInputStream(uri2);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(input, null, options);
                    if (options.outWidth > 1024 || options.outHeight > 1024){
                        options.inSampleSize = Math.max(options.outWidth / 1024, options.outHeight/1024);
                    }
                    options.inJustDecodeBounds = false;
                    Bitmap bitmap = BitmapFactory.decodeStream(cr2.openInputStream(uri2), null, options);
                    ImageView imageView = (ImageView)findViewById(R.id.IV02);

                    /*将Bitmap设定到ImageView*/
                    imageView.setImageBitmap(bitmap);
                }
                catch(FileNotFoundException e)
                {
                    Log.e("Exception",e.getMessage(),e);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode,data);
    }
}