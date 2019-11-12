package com.example.travelnote1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String travel_title = "";
        String travel_img = "";

        Bundle extras = getIntent().getExtras();

        travel_title = extras.getString("travel_title");
        travel_img = extras.getString("travel_img");
        Log.d("값 확인",travel_title + travel_img);

        TextView title = (TextView) findViewById(R.id.title);
        ImageView img = (ImageView)findViewById(R.id.re_profile);

        title.setText(travel_title);
       //img.setImageResource(travel_img);
    }
}
