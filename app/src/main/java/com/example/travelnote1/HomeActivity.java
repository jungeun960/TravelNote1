package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        String travel_title = intent.getStringExtra("travel_title");
        String travel_img = intent.getStringExtra("travel_img");
        Log.d("값 확인","제목 : " +travel_title + " 이미지 uri : " + travel_img);

        TextView title = (TextView) findViewById(R.id.title);
        ImageView img = (ImageView)findViewById(R.id.imageView2);

        title.setText(travel_title);
        //Glide.with(this).load(travel_img).into(img);
        Glide.with(this).load(R.drawable.me).into(img);
        //Glide를 이용하여 이미지뷰에 url에 있는 이미지를 세팅해줌


//        try {
//
//            Log.e("uri로 변경","ff");
//            InputStream in = getContentResolver().openInputStream(uri);
//            Bitmap imga = BitmapFactory.decodeStream(in);
//            in.close();
//            img.setImageBitmap(imga);
//        } catch (Exception e) {

       //img.setImageResource(travel_img);
    }
}
